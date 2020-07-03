package com.yaozou.pattern.behavior;

/**
 * @author yaozou
 */
public class InterpreterMethod {
    public static void main(String[] args) {
        Expression robert = new TerminalExpression("Robert");
        Expression john = new TerminalExpression("John");
        System.out.println(new OrExpression(robert, john).interpret("John"));

        Expression julie = new TerminalExpression("Julie");
        Expression married = new TerminalExpression("Married");
        System.out.println(new AndExpression(julie, married).interpret("Married Julie"));

    }
}
interface Expression{
    boolean interpret(String context);
}

class TerminalExpression implements Expression{
    private String data;

    public TerminalExpression(String data){
        this.data = data;
    }

    @Override
    public boolean interpret(String context) {
        if(context.contains(data)){
            return true;
        }
        return false;
    }
}

class OrExpression implements Expression{
    private Expression expr1 = null;
    private Expression expr2 = null;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean interpret(String context) {
        return expr1.interpret(context) || expr2.interpret(context);
    }
}

class AndExpression implements Expression{
    private Expression expr1 = null;
    private Expression expr2 = null;

    public AndExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean interpret(String context) {
        return expr1.interpret(context) && expr2.interpret(context);
    }
}