package mjc;

import parser.*;
import syntaxtree.*;
import visitor.*;
import error.*;
import symbol.*;

public class JVMMain {
    public static final boolean DEBUG = true;
    public static boolean ASSEM = false;

    public static void main(String[] args) {
        MiniJavaParser parser;
        ASTPrintVisitor printVisitor;
        DepthFirstVisitor depthVisitor;
        TypeDepthFirstVisitor typeVisitor;
        Program program;

        ErrorHandler error = new ErrorHandler();
        SymbolTable symTable = new SymbolTable();

        switch(args.length) {
            case 0:
                parser = new MiniJavaParser(System.in);
                break;
            case 1:
                try {
                    parser = new MiniJavaParser(new java.io.FileInputStream(args[0]));
                } catch (java.io.FileNotFoundException e) {
                    System.err.println("File not found");
                    return;
                }
                break;
            case 2:
                if(args[2].equals("-S")) { // Tigris command line invokation
                    ASSEM = true;
                    try {
                        parser = new MiniJavaParser(new java.io.FileInputStream(args[0]));
                    } catch (java.io.FileNotFoundException e) {
                        System.err.println("File not found");
                        return;
                    }
                } else {
                    System.err.println("Invalid copiler argument");
                    return;
                }
                break;
            default:
                System.err.println("Give no argument to read from stdin, otherwise specify a single input file");
                return;
        }

        try {
            program = parser.Program();
        } catch (ParseException e) {
            System.err.println(e.toString());
            return;
        }

        if(DEBUG) System.out.println("<<<<<<<<<<<<<<< PRINT VISITOR >>>>>>>>>>>>>>>");
        //printVisitor = new ASTPrintVisitor();
        //printVisitor.visit(program);

        if(DEBUG) System.out.println("<<<<<<<<<<<<<<< DEPTH VISITOR >>>>>>>>>>>>>>>");
        depthVisitor = new DepthFirstVisitor(error, symTable);
        depthVisitor.visit(program);

        if(DEBUG) System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>");
        typeVisitor = new TypeDepthFirstVisitor(error, symTable);
        typeVisitor.visit(program);

        // TODO Generates fake assembler files
        if(ASSEM) {
        }

        if(error.anyErrors()) {
            System.err.println("___THERE WERE COMPILATION ERRORS___");
            error.printErrors();
            System.exit(1); // According to Tigris spec
        } else
            System.exit(0);
    }
}
