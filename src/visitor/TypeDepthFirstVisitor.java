package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class TypeDepthFirstVisitor implements TypeVisitor {
    private ErrorMsg error;
    private Table table;
    private Symbol currClass;
    private Symbol currMethod;

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
        table.beginScope();

        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }

        table.endScope();
        return null;
    }

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n) {
        table.beginScope();

        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);

        table.endScope();
        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        table.beginScope();

        n.i.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        table.endScope();
        return null;
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        table.beginScope();

        n.i.accept(this);
        n.j.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        table.endScope();
        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        table.beginScope();

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

        table.endScope();
        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(Formal n) {
        n.t.accept(this);
        n.i.accept(this);
        return null;
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
        table.beginScope();
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        table.endScope();
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
        // TODO Hopefully this works for IdentifierType equals method
        //if(!(n.i.accept(this).getClass().equals(n.e.accept(this).getClass())))
        if(!(n.i.accept(this).equals(n.e.accept(this))))
            error.complain("Incompatible types in assignment");

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
        System.out.println("LESSTHAN E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this)); // DEBUG

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
        return new IntegerType(); // TODO Is this correct?
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

        return new IdentifierType(n.s);
    }

    public Type visit(This n) {
        return new IdentifierType("this"); // TODO Is this correct?
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
