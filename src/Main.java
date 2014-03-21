import parser.*;
import syntaxtree.*;
import visitor.*;
import error.*;
import symbol.*;

public class Main {
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
                return;
            }
        } else {
            System.out.println("Give no argument to read from stdin, otherwise specify a sinle input file.");
            return;
        }

        try {
            program = parser.Program();

            if(DEBUG)
                System.out.println("<<<<<<<<<<<<<<< PRINT VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            //printVisitor = new ASTPrintVisitor();
            //printVisitor.visit(program);

            if(DEBUG)
                System.out.println("<<<<<<<<<<<<<<< DEPTH VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            depthVisitor = new DepthFirstVisitor(error, symTable);
            depthVisitor.visit(program);

            if(DEBUG)
                System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            typeVisitor = new TypeDepthFirstVisitor(error, symTable);
            typeVisitor.visit(program);

            if(error.anyErrors()) {
                System.err.println("___THERE WERE COMPILATION ERRORS___");
                error.printErrors();
                System.exit(-1);
            }
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
