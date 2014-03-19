package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class TypeDepthFirstVisitor implements TypeVisitor {
    private ErrorMsg error;
    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;

    // Added constructor to inject error message and symtable
    public TypeDepthFirstVisitor(ErrorMsg error, SymbolTable symTable) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
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
        currMethod = null; // TODO Is this correct?

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
        currMethod = null; // TODO Is this correct?

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
        currMethod = null; // TODO Is this correct?

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
        System.out.println("ID_TYPE: " + n.s); // DEBUG
        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        if(!(n.e.accept(this) instanceof BooleanType))
            error.complain("If is only applicable to boolean type");
        n.s1.accept(this);
        n.s2.accept(this);

        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        if(!(n.e.accept(this) instanceof BooleanType))
            error.complain("While is missing boolean expression");
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
        Binding b;
        if(currClass == null)
            b = currMethod.getVar(s);
        else
            b = currClass.getVar(s);

        if(!(n.i.accept(this).equals(n.e.accept(this))))
            error.complain("Expression is not of type " + b.getType());

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
        System.out.println("AND E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of And must be of type boolean");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of And must be of type boolean");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        System.out.println("LESS_THAN E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of LessThan must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of LessThan must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    // TODO
    public Type visit(Plus n) {
        System.out.println("PLUS E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of Plus must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of Plus must be of type integer");

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of Minus must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of Minus must be of type integer");

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of Times must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of Times must be of type integer");

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
        n.e.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
        return null; // TODO Should this return the type of the called method?
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
        // TODO Must return the actual type from symbol table
        System.out.println("ID_EXP: " + n.s); // DEBUG
        Symbol s = Symbol.symbol(n.s);

        Binding b;
        if(currMethod == null)
            b = currClass.getVar(s);
        else
            b = currMethod.getVar(s);

        return b.getType();
    }

    public Type visit(This n) {
        // TODO Is this correct?
        //ClassDecl cl = (ClassDecl)currClass;
        //if(cl instanceof ClassDeclSimple) {
        //    ClassDeclSimple cds = (ClassDeclSimple)cl;
        //    return cds.i.accept(this); // Extract the type from the identifier
        //} else {
        //    ClassDeclExtends cde = (ClassDeclExtends)cl;
        //    return cde.i.accept(this);
        //}

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
        if(!(n.e.accept(this) instanceof BooleanType))
            error.complain("Not is only applicable to type boolean");

        return new BooleanType();
    }

    // String s;
    public Type visit(Identifier n) {
        System.out.println("ID: " + n.s); // DEBUG

        return new IdentifierType(n.s);
    }

    // Exp e1,e2;
    public Type visit(LessThanEquals n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of LessThanEquals must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of LessThanEquals must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThan n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of GreaterThan must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of GreaterThan must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThanEquals n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of GreateThanEquals must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of GreateThanEquals must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Equals n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of Equals must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of Equals must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(EqualsNot n) {
        if(!(n.e1.accept(this) instanceof IntegerType))
            error.complain("Left side of EqualsNot must be of type integer");
        if(!(n.e2.accept(this) instanceof IntegerType))
            error.complain("Right side of EqualsNot must be of type integer");

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Or n) {
        if(!(n.e1.accept(this) instanceof BooleanType))
            error.complain("Left side of Or must be of type boolean");
        if(!(n.e2.accept(this) instanceof BooleanType))
            error.complain("Right side of Or must be of type boolean");

        return new BooleanType();
    }
}
