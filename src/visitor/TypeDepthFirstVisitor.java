package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class TypeDepthFirstVisitor implements TypeVisitor {
    public static final boolean DEBUG = true;

    private ErrorHandler error;
    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;
    private BlockTable currBlock;

    // Added constructor to inject error message and symtable
    public TypeDepthFirstVisitor(ErrorHandler error, SymbolTable symTable) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
        currBlock = null;
    }

    // Helper method to extract type of given var symbol, returns null
    // if not found
    public Type getVarType(Symbol s) {
        Type t; Binding b;
        if(currMethod == null)
            b = currClass.getVar(s);
        else if(currBlock == null) {
            if(currMethod.getVar(s) == null)
                b = (Binding)currClass.getVar(s);
            else
                b = (Binding)currMethod.getVar(s);
        } else {
            if(currBlock.getVar(s) == null) { // Also checks method
                    b = (Binding)currClass.getVar(s);
            } else
                    b = (Binding)currBlock.getVar(s);
        }

        return b != null ? b.getType() : null;
    }

    // Helper method to use the name of a variable to create a symbol
    // of its IdentifierType (class).
    public Symbol getClassNameFromVar(Symbol s) {
        return Symbol.symbol(((IdentifierType)getVarType(s)).s);
    }

    // MainClass m;
    // ClassDeclList cl;
    public Type visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }

        return null;
    }

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n) {
        currClass = symTable.getClass(Symbol.symbol(n.i1.toString()));
        currMethod = null;

        n.i1.accept(this);
        n.i2.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        currClass = symTable.getClass(Symbol.symbol(n.i.toString()));
        currMethod = null;

        n.i.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        return new IdentifierType(n.i.toString());
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        currClass = symTable.getClass(Symbol.symbol(n.i.toString()));
        currMethod = null;

        n.i.accept(this);
        n.j.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        return new IdentifierType(n.i.toString());
    }

    // Type t;
    // Identifier i;
    public Type visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
        return n.t;
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        currMethod = currClass.getMethod(Symbol.symbol(n.i.toString()));

        n.t.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        n.e.accept(this);

        return n.t;
    }

    // Type t;
    // Identifier i;
    public Type visit(Formal n) {
        n.t.accept(this);
        n.i.accept(this);
        return n.t;
    }

    public Type visit(IntArrayType n) {
        return n;
    }

    public Type visit(BooleanType n) {
        return n;
    }

    public Type visit(IntegerType n) {
        return n;
    }

    // String s;
    public Type visit(IdentifierType n) {
        if(DEBUG)
            System.out.println("ID_TYPE: " + n.s); // DEBUG

        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        AbstractTable at;
        if(currBlock == null)
            at = new BlockTable(currMethod);
        else
            at = new BlockTable(currBlock);

        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        currBlock = null;
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("If is only applicable to boolean type",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        n.s1.accept(this);
        n.s2.accept(this);

        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("While is only applicable to boolean type",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        n.s.accept(this);

        return null;
    }

    // Exp e;
    public Type visit(Print n) {
        n.e.accept(this);
        return null;
    }

    // Identifier i;
    // Exp e;
    public Type visit(Assign n) {
        Symbol s = Symbol.symbol(n.i.toString());
        Type t = getVarType(s);

        if(DEBUG)
            System.out.println("ASSIGN: " + n.i.accept(this) + " TO: " + n.e.accept(this)); // DEBUG

        if(!(n.i.accept(this).equals(n.e.accept(this)))) {
            error.complain("Expression is not of type " + t,
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return null;
    }

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n) {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e1,e2;
    public Type visit(And n) {
        if(DEBUG)
            System.out.println("AND E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof BooleanType)) {
            error.complain("Left side of And must be of type boolean",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof BooleanType)) {
            error.complain("Right side of And must be of type boolean",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        if(DEBUG)
            System.out.println("LESS_THAN E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of LessThan must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of LessThan must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        if(DEBUG)
            System.out.println("PLUS E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of Plus must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of Plus must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of Minus must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of Minus must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of Times must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of Times must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return new IntegerType();
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        n.e.accept(this);
        return new IntegerType();
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {
        Symbol s1 = Symbol.symbol(n.i.toString()); // Method name
        Type t; Exp e = n.e; Symbol s2;
        if(e instanceof NewObject) {
            if(DEBUG)
                System.out.println("instanceof NewObject"); // DEBUG
            s2 = Symbol.symbol(((NewObject)e).i.toString());
            t = symTable.getClass(s2).getMethod(s1).getType();
        } else if(e instanceof IdentifierExp) {
            if(DEBUG)
                System.out.println("instanceof IdentifierExp"); // DEBUG
            IdentifierExp ie = (IdentifierExp)e;
            // Use class name to find class
            s2 = getClassNameFromVar(Symbol.symbol(ie.s));
            t = symTable.getClass(s2).getMethod(s1).getType();
        } else { // instanceof This
            if(DEBUG)
                System.out.println("instanceof (else)"); // DEBUG
            t = ((MethodTable)currClass.getMethod(s1)).getType();
        }

        n.e.accept(this);
        n.i.accept(this);

        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
        return t;
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return new IntegerType();
    }

    public Type visit(True n) {
        return new BooleanType();
    }

    public Type visit(False n) {
        return new BooleanType();
    }

    // String s;
    public Type visit(IdentifierExp n) {
        if(DEBUG)
            System.out.println("ID_EXP: " + n.s); // DEBUG
        Symbol s = Symbol.symbol(n.s);
        return getVarType(s);
    }

    public Type visit(This n) {
        return null;
    }

    // Exp e;
    public Type visit(NewArray n) {
        n.e.accept(this);
        return new IntArrayType();
    }

    // Identifier i;
    public Type visit(NewObject n) {
        return new IdentifierType(n.i.s);
    }

    // Exp e;
    public Type visit(Not n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("Not is only applicable to type boolean",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // String s;
    public Type visit(Identifier n) {
        if(DEBUG)
            System.out.println("ID: " + n.s); // DEBUG
        Symbol s = Symbol.symbol(n.s);

        return getVarType(s);
    }

    // Exp e1,e2;
    public Type visit(LessThanEquals n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of LessThanEquals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of LessThanEquals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThan n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of GreaterThan must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of GreaterThan must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThanEquals n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of GreateThanEquals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of GreateThanEquals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Equals n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of Equals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of Equals must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(EqualsNot n) {
        if(!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of EqualsNot must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of EqualsNot must be of type integer",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Or n) {
        if(!(n.e1.accept(this) instanceof BooleanType)) {
            error.complain("Left side of Or must be of type boolean",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }
        if(!(n.e2.accept(this) instanceof BooleanType)) {
            error.complain("Right side of Or must be of type boolean",
                    ErrorHandler.ErrorCode.TYPE_ERROR);
        }

        return new BooleanType();
    }
}
