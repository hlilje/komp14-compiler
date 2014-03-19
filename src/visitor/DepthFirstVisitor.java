package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class DepthFirstVisitor implements Visitor {
    private ErrorMsg error;
    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;

    // Added constructor to inject error message and symtable
    public DepthFirstVisitor(ErrorMsg error, SymbolTable symTable) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        System.out.println(">>> VISIT PROGRAM"); // DEBUG
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        System.out.println("====== BEGIN SCOPE ====== "); // DEBUG
        Symbol s = Symbol.symbol(n.i1.toString());
        System.out.println(">>> VISIT MAIN_CLASS: " + s); // DEBUG
        ClassTable ct = new ClassTable(s);

        if(!symTable.addClass(s, ct))
            error.complain("Class " + s + " is already defined (main class)");
        else {
            currClass = ct;
            currMethod = null; // TODO
        }

        n.i1.accept(this);
        n.i2.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        System.out.println("======= END SCOPE ======="); // DEBUG
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        System.out.println("====== BEGIN SCOPE ====== "); // DEBUG
        Symbol s = Symbol.symbol(n.i.toString());
        System.out.println(">>> VISIT CLASS_DECL_SIMP: " + s); // DEBUG
        ClassTable ct = new ClassTable(s);

        if(!symTable.addClass(s, ct))
            error.complain("Class " + s + " is already defined");
        else {
            error.complain("    add ClassDecl " + s); // DEBUG
            currClass = ct;
            currMethod = null; // TODO
        }

        n.i.accept(this);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        System.out.println("======= END SCOPE ======="); // DEBUG
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        System.out.println("====== BEGIN SCOPE ====== "); // DEBUG
        Symbol s = Symbol.symbol(n.i.toString());
        System.out.println(">>> VISIT CLASS_DECLEXT: " + s); // DEBUG
        ClassTable ct = new ClassTable(s);

        if(!symTable.addClass(s, ct))
            error.complain("Class " + s + " is already defined");
        else {
            error.complain("    add ClassDecl " + s); // DEBUG
            currClass = ct;
            currMethod = null; // TODO
        }

        n.i.accept(this);
        n.j.accept(this);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        System.out.println("======= END SCOPE ======="); // DEBUG
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        Symbol s = Symbol.symbol(n.i.toString());
        System.out.println(">>> VISIT VAR_DECL: " + s); // DEBUG


        // TODO May local variables override formals?
        if(currMethod == null) {
            System.out.println("    currMethod for VarDecl " + s + " was NULL"); // DEBUG
            if(!currClass.addVar(s, n.t))
                error.complain("VarDecl " + s + " is already defined in " + currClass.getId());
        } else {
            System.out.println("    currMethod for VarDecl " + s + " was NOT NULL"); // DEBUG
            if(!currMethod.addVar(s, n.t)); // TODO Must there be a check in class scope here?
                error.complain("VarDecl " + s + " is already defined in " + currMethod.getId());
        }

        n.t.accept(this);
        n.i.accept(this);
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        System.out.println("====== BEGIN SCOPE ====== "); // DEBUG
        Symbol s = Symbol.symbol(n.i.toString());
        System.out.println(">>> VISIT METHOD_DECL: " + s); // DEBUG
        MethodTable mt = new MethodTable(s, n.t);

        if(!currClass.addMethod(s, mt))
            error.complain("Method " + s + " is already defined in " + currClass.getId());
        else
            currMethod = mt;

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

        n.t.accept(this);
        n.e.accept(this);
        System.out.println("======= END SCOPE ======="); // DEBUG
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        Symbol s = Symbol.symbol(n.i.toString());
        System.out.println(">>> VISIT FORMAL: " + s); // DEBUG

        if(!currMethod.addFormal(s, n.t))
            error.complain("Formal " + s + " is already defined in " + currMethod.getId());

        n.t.accept(this);
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {
    }

    // String s;
    public void visit(IdentifierType n) {
    }

    // StatementList sl;
    // TODO Names in inner blocks should not be allowed to override the scope above
    public void visit(Block n) {
        System.out.println(">>> VISIT BLOCK"); // DEBUG
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        Symbol s = Symbol.symbol(n.i.toString());
        if(currMethod == null) {
            if(!currClass.hasVar(s))
                error.complain(s + " is not defined");
        } else {
            if(!currMethod.inScope(s)) {
                if(!currClass.hasVar(s))
                    error.complain(s + " is not defined");
            }
        }
        n.i.accept(this);
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        System.out.println(">>> VISIT CALL: " + n.i.toString()); // DEBUG
        n.e.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {
    }

    public void visit(True n) {
    }

    public void visit(False n) {
    }

    // String s;
    public void visit(IdentifierExp n) {
    }

    public void visit(This n) {
    }

    // Exp e;
    public void visit(NewArray n) {
        n.e.accept(this);
    }

    // Identifier i;
    public void visit(NewObject n) {
    }

    // Exp e;
    public void visit(Not n) {
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
    }

    // Exp e1,e2;
    public void visit(LessThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(GreaterThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(GreaterThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Equals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(EqualsNot n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }
}
