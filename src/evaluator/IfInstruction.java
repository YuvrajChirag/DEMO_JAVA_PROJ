package evaluator;

import environment.Environment;
import parser.Expression;

import java.util.List;

/**
 * Conditional instruction that executes its body when condition is true.
 */
public class IfInstruction implements Instruction {
    private final Expression<Boolean> conditionExpression;
    private final List<Instruction> bodyInstructions;

    public IfInstruction(Expression<Boolean> conditionExpression, List<Instruction> bodyInstructions) {
        this.conditionExpression = conditionExpression;
        this.bodyInstructions = bodyInstructions;
    }

    /**
     * Runs all nested instructions only when condition evaluates to true.
     */
    @Override
    public void execute(Environment<Object> env) {
        if (conditionExpression.evaluate(env)) {
            for (Instruction instruction : bodyInstructions) {
                instruction.execute(env);
            }
        }
    }
}
