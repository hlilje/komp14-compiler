package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

// TODO Should this class have scopes?
public class TypeDepthFirstVisitor implements TypeVisitor {
    private ErrorMsg error;
    private Table table;
    private Object currClass;
    private Object currMethod;

    // Add constructor to inject error message
    public TypeDepthFirstVisitor(ErrorMsg error, Table table) {
        this.error = error;
        this.table = table;
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
        currClass = n;

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
        currClass = n;

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
        currClass = n;

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
        currMethod = n;

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
        Object o = table.get(s);
        //if(!(n.i.accept(this).equals(n.e.accept(this))))
        if(!(n.i.accept(this).equals(n.e.accept(this))))
            error.complain("Expression is not of type " + ((VarDecl)o).t);

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
        Symbol s = Symbol.symbol(n.i.toString());
        Type t;
        ClassDecl cl = (ClassDecl)table.get(s);
        if(cl instanceof ClassDeclSimple) {
            t = new IdentifierType(((ClassDeclSimple)cl).i.toString());
        } else {
            t = new IdentifierType(((ClassDeclExtends)cl).i.toString());
        }

        n.e.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
        return t; // TODO Should this return the type of the called method?
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
        Object o = table.get(s);
        Type t;
        if(o instanceof VarDecl) {
            System.out.println("    instanceof VarDecl"); // DEBUG
            t = ((VarDecl)o).t;
        } else if(o instanceof MethodDecl) {
            System.out.println("    instanceof MethodDecl"); // DEBUG
            t = ((MethodDecl)o).t;
        //} else if(o instanceof Formal) {
        } else {
            System.out.println("    instanceof Formal"); // DEBUG
            t = ((Formal)o).t;
        } /*else {
            if(o == null)
                System.out.println("    was NULL"); // DEBUG
            else
                System.out.println("    instance of class: " + o.getClass()); // DEBUG
            return null; // TODO
        }*/

        return t;
    }

    public Type visit(This n) {
        // TODO Is this correct?
        ClassDecl cl = (ClassDecl)currClass;
        if(cl instanceof ClassDeclSimple) {
            ClassDeclSimple cds = (ClassDeclSimple)cl;
            return cds.i.accept(this); // Extract the type from the identifier
        } else {
            ClassDeclExtends cde = (ClassDeclExtends)cl;
            return cde.i.accept(this);
        }
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
