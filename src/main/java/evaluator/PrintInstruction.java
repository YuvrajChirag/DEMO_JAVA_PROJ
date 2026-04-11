package evaluator;

import environment.Environment;
import parser.Expression;

public class PrintInstruction implements Instruction {
    private final Expression<Object> expressionToPrint;

    public PrintInstruction(Expression<Object> expressionToPrint) {
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public void execute(Environment<Object> env) {
        Object value = expressionToPrint.evaluate(env);
        if (value instanceof Number) {
            double numericValue = ((Number) value).doubleValue();
            if (numericValue == Math.rint(numericValue)) {
                System.out.println((long) numericValue);
                return;
            }
        }
        System.out.println(value);
    }
}
