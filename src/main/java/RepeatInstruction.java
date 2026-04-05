import java.util.List;

/**
 * Loop instruction that repeats body instructions N times.
 */
public class RepeatInstruction implements Instruction<Object> {
    private final Expression<Object> repeatCountExpression;
    private final List<Instruction<Object>> bodyInstructions;

    public RepeatInstruction(Expression<Object> repeatCountExpression, List<Instruction<Object>> bodyInstructions) {
        this.repeatCountExpression = repeatCountExpression;
        this.bodyInstructions = bodyInstructions;
    }

    @Override
    public void execute(Environment<Object> env) {
        int repeatCount = normalizeRepeatCount(repeatCountExpression.evaluate(env));
        for (int iteration = 0; iteration < repeatCount; iteration++) {
            InstructionExecutor.executeAll(bodyInstructions, env);
        }
    }

    private int normalizeRepeatCount(Object countValue) {
        double numericCount = ValueHelper.asNumber(countValue, "Repeat count");
        if (numericCount < 0) {
            throw new IllegalArgumentException("Repeat count cannot be negative: " + numericCount);
        }
        return (int) Math.floor(numericCount);
    }
}
