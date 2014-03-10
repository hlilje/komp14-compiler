import parser.*;
import syntaxtree.*;
import visitor.*;

public class Main {
    public static void main(String [] args) {
        MiniJavaParser parser;
        ASTPrintVisitor visitor;
        Program p;

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
            p = parser.Program();

            visitor = new ASTPrintVisitor();
            visitor.visit(p);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
