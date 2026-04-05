import java.util.List;

/**
 * Loop instruction that repeats a nested block a computed number of times.
 */
public class RepeatInstruction implements Instruction {
    private final Expression repeatCountExpression;
    private final List<Instruction> bodyInstructions;

    public RepeatInstruction(Expression repeatCountExpression, List<Instruction> bodyInstructions) {
        this.repeatCountExpression = repeatCountExpression;
        this.bodyInstructions = bodyInstructions;
    }

    @Override
    public void execute(Environment env) {
        Object countValue = repeatCountExpression.evaluate(env);
        int repeatCount = normalizeRepeatCount(countValue);

        for (int iteration = 0; iteration < repeatCount; iteration++) {
            InstructionExecutor.executeAll(bodyInstructions, env);
        }
    }

    /**
     * Preserves previous behavior by flooring decimal counts.
     */
    private int normalizeRepeatCount(Object countValue) {
        double numericCount = ValueHelper.asNumber(countValue, "Repeat count");
        return (int) Math.floor(numericCount);
    }
}
