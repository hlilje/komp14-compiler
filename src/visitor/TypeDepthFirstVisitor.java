/**
 * Depth first visitor which typechecks an AST.
 */

package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;

public class TypeDepthFirstVisitor implements TypeVisitor {
    public static final boolean DEBUG = false;

    private ErrorHandler error;
    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;
    private BlockTable currBlock;

    private int blockId; // Unique id for outmost blocks

    // Added constructor to inject error message and symtable
    public TypeDepthFirstVisitor(ErrorHandler error, SymbolTable symTable) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
        currBlock = null;
        blockId = -1; // To give block #1 id 0
    }

    // Helper method to extract type of given var symbol, returns null
    // if not found
    public Type getVarType(Symbol s) {
        Type t; Binding b;
        if(currMethod == null)
            b = currClass.getVar(s);
        else if(currBlock == null) {
            b = (Binding)currMethod.getVar(s);
            if(b == null) b = (Binding)currClass.getVar(s);
        } else { // Check block
            b = currBlock.getVar(s);
            if(b == null) {
                b = currMethod.getVar(s);
                if(b == null) b = currClass.getVar(s);
            } else b = currBlock.getVar(s);
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
        // Hard coded method name, actual name is ignored
        currMethod = currClass.getMethod(Symbol.symbol("main"));

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
        currClass = symTable.getClass(Symbol.symbol(n.i.s));
        currMethod = null;

        n.i.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        return new IdentifierType(n.i.s);
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        currClass = symTable.getClass(Symbol.symbol(n.i.s));
        currMethod = null;

        if(symTable.getClass(Symbol.symbol(n.j.s)) == null) {
            error.complain("Class " + n.i + " extends nonexistent class " +
                    n.j, ErrorHandler.ErrorCode.NOT_FOUND);
        }

        n.i.accept(this);
        n.j.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        return new IdentifierType(n.i.s);
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
        Symbol s = Symbol.symbol(n.i.s);
        currMethod = currClass.getMethod(s);
        currBlock = null; // Reset block scope

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

        Type t = n.e.accept(this); // Avoid nullpointer exception
        if((t != null) && (!(t.equals(n.t)))) {
            error.complain("Returned type " + t + " is not same as declared type " +
                    n.t, ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        // Check for method overloading from inheritance here
        if( currClass.getSuperId() != null
            && symTable.getClass(currClass.getSuperId()) != null
            && symTable.getClass(currClass.getSuperId()).getMethod(s) != null ) {
                error.complain("Method " + s + " in class " + currClass.getId()
                + " was already defined in super class " + currClass.getSuperId(),
                ErrorHandler.ErrorCode.ALREADY_DEFINED);
        }

        blockId = -1; // Reset the block counter for this method
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

    public Type visit(LongArrayType n) {
        return n;
    }

    public Type visit(BooleanType n) {
        return n;
    }

    public Type visit(IntegerType n) {
        return n;
    }

    public Type visit(LongType n) {
        return n;
    }

    // String s;
    public Type visit(IdentifierType n) {
        if(DEBUG) System.out.println("ID_TYPE: " + n.s);
        // Also check for super Object type
        if((symTable.getClass(Symbol.symbol(n.s)) == null) &&
                (!n.s.equals("Object"))) {
            error.complain("Undeclared type " + n.s + " in method " +
                    currMethod.getId() + " in class " + currClass.getId(),
                    ErrorHandler.ErrorCode.UNDECLARED_TYPE);
        }

        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        BlockTable prevBlock = currBlock;

        blockId++;
        currBlock = currMethod.getBlock(Symbol.symbol(blockId + ""));

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        currBlock = prevBlock; // End scope
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("If is only applicable to Boolean type",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        n.s1.accept(this);

        if(n.s2 != null)
            n.s2.accept(this);

        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("While is only applicable to Boolean type",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        n.s.accept(this);

        return null;
    }

    // Exp e;
    public Type visit(Print n) {
        Type e = n.e.accept(this);
        if(e instanceof IdentifierType) {
            error.complain("Invalid print of object in method " + currMethod.getId() +
                    " in class " + currClass.getId(), ErrorHandler.ErrorCode.OBJECT_PRINT);
        }
        return null;
    }

    // Identifier i;
    // Exp e;
    public Type visit(Assign n) {
        // To avoid nullpointer exception
        Type it = n.i.accept(this);
        Type et = n.e.accept(this);

        if(DEBUG) System.out.println("ASSIGN: " + et + " TO: " + it);

        if((it != null) && (et != null) && (!it.equals(et))) {
            error.complain("Assigned type " + et + " should be of type " + it,
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return null;
    }

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n) {
        Symbol s = Symbol.symbol(n.i.s);
        Type t = getVarType(s);
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);

        if(!(t instanceof IntArrayType) && !(t instanceof LongArrayType)) {
            error.complain("Array lookup on non int or long array type in array assign in method " +
                    currMethod.getId() + " in class " + currClass.getId(),
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        n.i.accept(this);

        if(!(t1 instanceof IntegerType)) {
            error.complain("Non integer type in array index for array " + s,
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        if(t instanceof IntArrayType) {
            if(!(n.e2.accept(this) instanceof IntegerType)) {
                error.complain("Non integer type in array assign for array " + s,
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        }
        if(t instanceof LongArrayType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Non long type in array assign for array " + s,
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        }

        if((t instanceof IntArrayType) || (t instanceof LongArrayType))
            return t;
        else
            return null; // Don't return invalid types
    }

    // Exp e1,e2;
    public Type visit(And n) {
        if(DEBUG) System.out.println("AND E1: " + n.e1.accept(this) + ", E2: " + n.e2.accept(this));

        if(!(n.e1.accept(this) instanceof BooleanType)) {
            error.complain("Left side of And must be of type Boolean",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        if(!(n.e2.accept(this) instanceof BooleanType)) {
            error.complain("Right side of And must be of type Boolean",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("LESS_THAN E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of LessThan must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of LessThan must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for LessThan",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("PLUS E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of Plus must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of Plus must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for Plus",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("MINUS E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of Minus must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of Minus must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for Minus",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("TIMES E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of Times must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of Times must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for Times",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);

        if(!(t1 instanceof IntArrayType) && !(t1 instanceof LongArrayType)) {
            error.complain("Outer expression of ArrayLookup must be of type IntArrayType " +
                    " or LongArrayType",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        if(!(t2 instanceof IntegerType)) {
            error.complain("Non integer type in array index",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        if((t1 instanceof IntArrayType) || (t1 instanceof LongArrayType))
            return t1;
        else
            return null; // Don't return invalid types
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        Type t = n.e.accept(this);

        if(!(t instanceof IntArrayType) && !(t instanceof LongArrayType)) {
            error.complain("Array length of non-array type in method " + currMethod.getId() +
                    " in class " + currClass.getId(),
                    ErrorHandler.ErrorCode.NON_ARRAY_LENGTH);
        }
        return new IntegerType();
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {
        /* This method has to be massive since it's impossible to check for
         * method/class existance in the first visit (it may not have been visited yet). */
        Symbol s1 = Symbol.symbol(n.i.s); // Method name
        Type t = null; Type t2; Exp e = n.e; Symbol s2;
        java.util.ArrayList<Binding> fl = null; // To be able to check for formal type in call
        ClassTable ct = null; MethodTable mt = null; // For null checks

        t2 = n.e.accept(this); // Save for call on call

        if(e instanceof NewObject) {
            if(DEBUG) System.out.println("  instanceof NewObject");
            s2 = Symbol.symbol(((NewObject)e).i.toString());
            ct = symTable.getClass(s2); mt = ct.getMethod(s1);

            if(mt == null) {
                error.complain("Call to nonexistent method " + s1 + " in method " +
                        currMethod.getId() + " in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.NOT_FOUND);
            } else {
                t = mt.getType();
                fl = mt.getOrderedFormals();
            }
        } else if(e instanceof IdentifierExp) {
            if(DEBUG) System.out.println("  instanceof IdentifierExp");
            IdentifierExp ie = (IdentifierExp)e;
            s2 = getClassNameFromVar(Symbol.symbol(ie.s)); // Use class name to find class
            ct = symTable.getClass(s2); mt = ct.getMethod(s1);

            if(mt == null) {
                error.complain("Call to nonexistent method " + s1 + " in method " +
                        currMethod.getId() + " in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.NOT_FOUND);
            } else {
                t = mt.getType();
                fl = mt.getOrderedFormals();
            }
        } else if(e instanceof Call) {
            if(DEBUG) System.out.println("  instanceof Call");
            s2 = Symbol.symbol(((IdentifierType)t2).s);
            ct = symTable.getClass(s2);

            if(ct == null) {
                error.complain("Misformed call on call for method " + s1 + " in method " +
                        currMethod.getId() + " in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.NOT_FOUND);
            } else {
                mt = ct.getMethod(s1);
                if(mt == null) {
                    error.complain("Call to nonexistent method on call " + s1 + " in method " +
                            currMethod.getId() + " in class " + currClass.getId(),
                            ErrorHandler.ErrorCode.NOT_FOUND);
                } else {
                    t = mt.getType();
                    fl = mt.getOrderedFormals();
                }
            }
        } else if(e instanceof This) {
            // TODO There needs to be more checks here if the method doesn't exist
            if(DEBUG) System.out.println("  instanceof This");
            mt = currClass.getMethod(s1);

            if(mt == null) {
                error.complain("Call to nonexistent local method " + s1 + " in method " +
                        currMethod.getId() + " in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.NOT_FOUND);
            } else {
                t = mt.getType();
                fl = mt.getOrderedFormals();
            }
        } else {
            error.complain("Call on invalid object with method call " + s1 + " in class " +
                    currClass.getId().toString(), ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }

        n.i.accept(this);

        if(fl != null && n.el.size() != fl.size()) {
                error.complain("Wrong number of arguments in method call to " + n.i +
                        " in method " + currMethod.getId() + " in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.WRONG_NUM_ARGS);
        }

        // Type check formals
        for ( int i = 0; i < n.el.size(); i++ ) {
            Type formalType = n.el.elementAt(i).accept(this);
            Type callType = null;
            if(fl != null) callType = fl.get(i).getType(); // To handle null errors from before
            if(!formalType.equals(callType)) {
                error.complain("Parameter type " + formalType + " in call not of type " + callType + " for method " +
                        currMethod.getId(), ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        }

        return t;
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return new IntegerType();
    }

    // long i;
    public Type visit(LongLiteral n) {
        return new LongType();
    }

    public Type visit(True n) {
        return new BooleanType();
    }

    public Type visit(False n) {
        return new BooleanType();
    }

    // String s;
    public Type visit(IdentifierExp n) {
        if(DEBUG) System.out.println("ID_EXP: " + n.s);
        Symbol s = Symbol.symbol(n.s);
        return getVarType(s);
    }

    public Type visit(This n) {
        return new IdentifierType(currClass.getId().toString());
    }

    // Exp e;
    public Type visit(NewArray n) {
        if(!(n.e.accept(this) instanceof IntegerType)) {
            error.complain("Array size is not of type Integer",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        return new IntArrayType();
    }

    // Exp e;
    public Type visit(NewLongArray n) {
        if(!(n.e.accept(this) instanceof IntegerType)) {
            error.complain("Array size is not of type Integer",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        return new LongArrayType();
    }

    // Identifier i;
    public Type visit(NewObject n) {
        Symbol s = Symbol.symbol(n.i.s);
        if(symTable.getClass(s) == null) {
            error.complain("Creation of object of nonexistent class " + s + " in class " +
                    currClass.getId() + " in method " + currMethod.getId(),
                    ErrorHandler.ErrorCode.NOT_FOUND);
        }
        return new IdentifierType(n.i.s);
    }

    // Exp e;
    public Type visit(Not n) {
        if(!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("Not is only applicable to type Boolean",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // String s;
    public Type visit(Identifier n) {
        if(DEBUG) System.out.println("ID: " + n.s);
        Symbol s = Symbol.symbol(n.s);

        return getVarType(s);
    }

    // Exp e1,e2;
    public Type visit(LessThanEquals n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("LESS_THAN_EQUALS E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of LessThanEquals must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of LessThanEquals must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for LessThanEquals",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThan n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("GREATER_THAN E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of GreaterThan must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of GreaterThan must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for GreaterThan",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThanEquals n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);
        if(DEBUG) System.out.println("GREATER_THAN_EQUALS E1: " + t1 + ", E2: " + t2);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of GreaterThanEquals must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of GreaterThanEquals must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else {
            error.complain("Non Integer or Long type for GreaterThanEquals",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Equals n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of Equals must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of Equals must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if((t1 instanceof IdentifierType) && !t1.equals(t2)) {
            error.complain("Different identifier types in equality comparison",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        } else {
            error.complain("Invalid type in equality comparison",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(EqualsNot n) {
        Type t1 = n.e1.accept(this); Type t2 = n.e2.accept(this);

        if(t1 instanceof IntegerType) {
            if(!(t2 instanceof IntegerType)) {
                error.complain("Right side of EqualsNot must be of type Integer",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if(t1 instanceof LongType) {
            if(!(t2 instanceof LongType)) {
                error.complain("Right side of EqualsNot must be of type Long",
                        ErrorHandler.ErrorCode.TYPE_MISMATCH);
            }
        } else if((t1 instanceof IdentifierType) && !t1.equals(t2)) {
            error.complain("Different identifier types in non-equality comparison",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        } else {
            error.complain("Invalid type in non-equality comparison",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Or n) {
        if(!(n.e1.accept(this) instanceof BooleanType)) {
            error.complain("Left side of Or must be of type Boolean",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }
        if(!(n.e2.accept(this) instanceof BooleanType)) {
            error.complain("Right side of Or must be of type Boolean",
                    ErrorHandler.ErrorCode.TYPE_MISMATCH);
        }

        return new BooleanType();
    }
}
