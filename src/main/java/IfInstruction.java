import java.util.List;

/**
 * Conditional instruction that executes a nested block when the condition
 * evaluates to {@code true}.
 */
public class IfInstruction implements Instruction {
    private final Expression conditionExpression;
    private final List<Instruction> bodyInstructions;

    public IfInstruction(Expression conditionExpression, List<Instruction> bodyInstructions) {
        this.conditionExpression = conditionExpression;
        this.bodyInstructions = bodyInstructions;
    }

    @Override
    public void execute(Environment env) {
        Object conditionValue = conditionExpression.evaluate(env);
        boolean shouldExecute = ValueHelper.asBoolean(conditionValue, "If condition");
        if (shouldExecute) {
            InstructionExecutor.executeAll(bodyInstructions, env);
        }
    }
}
