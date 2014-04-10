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
        JasminVisitor jasminVisitor;
        Program program;

        ErrorHandler error = new ErrorHandler();
        SymbolTable symTable = new SymbolTable();
        String filePath = ""; // Where to generate Jasmin files

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
                if(args[1].equals("-S")) { // Tigris command line invokation
                    ASSEM = true;
                    filePath = ".";
                    
                    try {
                        parser = new MiniJavaParser(new java.io.FileInputStream(args[0]));
                    } catch (java.io.FileNotFoundException e) {
                        System.err.println("File not found");
                        return;
                    }
                } else {
                    System.err.println("Invalid compiler argument");
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

        if(ASSEM) {
            if(DEBUG) System.out.println("<<<<<<<<<<<<<<< ASSEM VISITOR >>>>>>>>>>>>>>>");
            jasminVisitor = new JasminVisitor(error, symTable, filePath);
            jasminVisitor.visit(program);
        }

        if(DEBUG) System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>");
        typeVisitor = new TypeDepthFirstVisitor(error, symTable);
        typeVisitor.visit(program);

        if(error.anyErrors()) {
            System.err.println("___THERE WERE COMPILATION ERRORS___");
            error.printErrors();
            System.exit(1); // According to Tigris spec
        } else
            System.exit(0);
    }
}
