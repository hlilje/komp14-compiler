import parser.*;
import syntaxtree.*;
import visitor.*;
import error.*;

public class Main {
    public static void main(String [] args) {
        MiniJavaParser parser;
        ASTPrintVisitor printVisitor;
        DepthFirstVisitor depthVisitor;
        //TypeDepthFirstVisitor typeVisitor;
        Program program;

        ErrorMsg error;
        error = new ErrorMsg(System.out);

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

            printVisitor = new ASTPrintVisitor();
            printVisitor.visit(program);

            depthVisitor = new DepthFirstVisitor(error);
            depthVisitor.visit(program);
            //typeVisitor = new TypeDepthFirstVisitor(error);
            //typeVisitor.visit(program);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
