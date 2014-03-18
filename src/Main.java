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
        Table table = new Table();

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

            System.out.println("<<<<<<<<<<<<<<< PRINT VISITOR >>>>>>>>>>>>>>>");
            printVisitor = new ASTPrintVisitor();
            printVisitor.visit(program);

            System.out.println("<<<<<<<<<<<<<<< DEPTH VISITOR >>>>>>>>>>>>>>>");
            depthVisitor = new DepthFirstVisitor(error, table);
            depthVisitor.visit(program);

            System.out.println("<<<<<<<<<<<<<<< TYPE VISITOR >>>>>>>>>>>>>>>");
            // TODO Should the same table be passed?
            typeVisitor = new TypeDepthFirstVisitor(error, table);
            typeVisitor.visit(program);

            // DEBUG
            //java.util.Enumeration keys = table.keys();
            //String key; Object type;
            //System.out.println("<<<<<<<< PRINT KEYS >>>>>>>>");
            //while(keys.hasMoreElements()) {
            //    key = (String)keys.nextElement();
            //    type = table.get(Symbol.symbol(key));
            //    System.out.println("KEY FROM TABLE: " + key);
            //    System.out.println("    TYPE FROM TABLE: " + (type != null ? table.get(Symbol.symbol(key)).getClass().getName() : "NULL"));
            //}
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
