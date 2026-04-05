/**
 * Output instruction that prints expression results to standard output.
 */
public class PrintInstruction implements Instruction<Object> {
    private final Expression<Object> expressionToPrint;

    public PrintInstruction(Expression<Object> expressionToPrint) {
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public void execute(Environment<Object> env) {
        Object value = expressionToPrint.evaluate(env);
        System.out.println(ValueHelper.formatForDisplay(value));
    }
}
