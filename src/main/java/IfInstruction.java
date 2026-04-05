import java.util.List;

/**
 * Conditional instruction that executes body instructions when condition is true.
 */
public class IfInstruction implements Instruction<Object> {
    private final Expression<Boolean> conditionExpression;
    private final List<Instruction<Object>> bodyInstructions;

    public IfInstruction(Expression<Boolean> conditionExpression, List<Instruction<Object>> bodyInstructions) {
        this.conditionExpression = conditionExpression;
        this.bodyInstructions = bodyInstructions;
    }

    @Override
    public void execute(Environment<Object> env) {
        if (conditionExpression.evaluate(env)) {
            InstructionExecutor.executeAll(bodyInstructions, env);
        }
    }
}
