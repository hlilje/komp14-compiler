import parser.*;
import syntaxtree.*;
import visitor.*;
import error.*;
import symbol.*;

public class Main {
    public static void main(String[] args) {
        MiniJavaParser parser;
        ASTPrintVisitor printVisitor;
        DepthFirstVisitor depthVisitor;
        TypeDepthFirstVisitor typeVisitor;
        Program program;

        ErrorMsg error = new ErrorMsg(System.out);
        Table table = new Table(null); // Set previous table to null

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

            System.out.println("<<<<<<<<<<<<<<< PRINT VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            printVisitor = new ASTPrintVisitor();
            printVisitor.visit(program);

            System.out.println("<<<<<<<<<<<<<<< DEPTH VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            depthVisitor = new DepthFirstVisitor(error, table);
            depthVisitor.visit(program);

            System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>"); // DEBUG
            typeVisitor = new TypeDepthFirstVisitor(error, table);
            typeVisitor.visit(program);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
