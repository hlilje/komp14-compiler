package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class DepthFirstVisitor implements Visitor {
    private ErrorMsg error;
    private Table table;
    private Symbol currClass;
    private Symbol currMethod;

    // Add constructor to inject error message and table
    public DepthFirstVisitor(ErrorMsg error, Table table) {
        this.error = error;
        this.table = table;
        currClass = null;
        currMethod = null;
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        table.beginScope();

        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }

        table.endScope();
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        // TODO Should MainClass have a special scope?
        Symbol s = Symbol.symbol(n.i1.toString());
        currClass = s;
        table.put(s, n);
        table.beginScope();

        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);

        table.endScope();
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        Symbol s = Symbol.symbol(n.i.toString());
        if(!table.inScope(s)) {
            currClass = s;
            table.put(s, n);
        } else {
            error.complain(s + " is already defined");
        }

        n.i.accept(this);

        table.beginScope();
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        table.endScope();
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    // TODO Not implemented
    public void visit(ClassDeclExtends n) {
        Symbol s = Symbol.symbol(n.i.toString());
        if(!table.inScope(s)) {
            currClass = s;
            table.put(s, n);
        } else {
            error.complain(s + " is already defined");
        }

        n.i.accept(this);
        n.j.accept(this);

        table.beginScope();
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        table.endScope();
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        Symbol s = Symbol.symbol(n.i.toString());
        if(!table.inScope(s)) {
            table.put(s, n);
        } else {
            if(currMethod == null)
                error.complain(s + " is already defined in " + currClass);
            else
                error.complain(s + " is already defined in " + currMethod);
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
        Symbol s = Symbol.symbol(n.i.toString());
        if(!table.inScope(s)) {
            currMethod = s;
            table.put(s, n);
        } else {
            error.complain(s + " is already defined in " + currClass);
        }

        n.t.accept(this);
        n.i.accept(this);

        table.beginScope();
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        table.endScope();

        n.e.accept(this);
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        table.put(Symbol.symbol(n.i.toString()), n);

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
    // TODO Is it sufficient to just ignore declaring a new scope for this issue?
    public void visit(Block n) {
        table.beginScope();
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        table.endScope();
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
