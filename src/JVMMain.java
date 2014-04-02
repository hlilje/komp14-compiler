package mjc;

import parser.*;
import syntaxtree.*;
import visitor.*;
import error.*;
import symbol.*;

public class JVMMain {
    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        MiniJavaParser parser;
        ASTPrintVisitor printVisitor;
        DepthFirstVisitor depthVisitor;
        TypeDepthFirstVisitor typeVisitor;
        Program program;

        ErrorHandler error = new ErrorHandler();
        SymbolTable symTable = new SymbolTable();

        if(args.length == 0) {
            parser = new MiniJavaParser(System.in);
        } else if(args.length == 1) {
            try {
                parser = new MiniJavaParser(new java.io.FileInputStream(args[0]));
            } catch (java.io.FileNotFoundException e) {
                System.err.println("File not found");
                return;
            }
        } else {
            System.err.println("Give no argument to read from stdin, otherwise specify a single input file");
            return;
        }

        try {
            program = parser.Program();

            if(DEBUG) System.out.println("<<<<<<<<<<<<<<< PRINT VISITOR >>>>>>>>>>>>>>>");
            //printVisitor = new ASTPrintVisitor();
            //printVisitor.visit(program);

            if(DEBUG) System.out.println("<<<<<<<<<<<<<<< DEPTH VISITOR >>>>>>>>>>>>>>>");
            depthVisitor = new DepthFirstVisitor(error, symTable);
            depthVisitor.visit(program);

            if(DEBUG) System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>");
            typeVisitor = new TypeDepthFirstVisitor(error, symTable);
            typeVisitor.visit(program);

            if(error.anyErrors()) {
                System.err.println("___THERE WERE COMPILATION ERRORS___");
                error.printErrors();
                System.exit(-1);
            }
        } catch (ParseException e) {
            System.err.println(e.toString());
        }
    }
}
