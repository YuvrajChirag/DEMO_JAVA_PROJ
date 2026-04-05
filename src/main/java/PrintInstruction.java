/**
 * Output instruction that prints expression results to standard output.
 */
public class PrintInstruction implements Instruction {
    private final Expression expressionToPrint;

    public PrintInstruction(Expression expressionToPrint) {
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public void execute(Environment env) {
        Object value = expressionToPrint.evaluate(env);
        System.out.println(ValueHelper.formatForDisplay(value));
    }
}
