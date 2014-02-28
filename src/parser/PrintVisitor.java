package parser;

public class PrintVisitor implements MiniJavaParserVisitor {
    public Object defaultVisit(SimpleNode node, Object data){
        node.childrenAccept(this, data);
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return data;
    }

    public Object visit(SimpleNode node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTMainClass node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTProgram node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTClassDecl node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTVarDecl node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTMethodDecl node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTType node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTStmt node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTExp node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTExpPrim node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTExpList node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }
    public Object visit(ASTExpRest node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }

    public Object visit(ASTOp node, Object data){
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }
    public Object visit(ASTFormalList node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }
    public Object visit(ASTFormalRest node, Object data) {
        System.out.println("Node: " + node + ", Data: " + data + ", Value: " + node.value);
        return defaultVisit(node, data);
    }
}
