package evaluator;

import environment.Environment;
import parser.Expression;

/**
 * Output instruction that prints evaluated expression value.
 */
public class PrintInstruction implements Instruction {
    private final Expression<Object> expressionToPrint;

    public PrintInstruction(Expression<Object> expressionToPrint) {
        this.expressionToPrint = expressionToPrint;
    }

    /**
     * Prints numbers without trailing ".0" when value is mathematically integral.
     */
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
