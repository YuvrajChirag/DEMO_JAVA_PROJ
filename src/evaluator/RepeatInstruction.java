package evaluator;

import environment.Environment;
import parser.Expression;

import java.util.List;

/**
 * Loop instruction that executes a body a numeric number of times.
 */
public class RepeatInstruction implements Instruction {
    private final Expression<Object> repeatCountExpression;
    private final List<Instruction> bodyInstructions;

    public RepeatInstruction(Expression<Object> repeatCountExpression, List<Instruction> bodyInstructions) {
        this.repeatCountExpression = repeatCountExpression;
        this.bodyInstructions = bodyInstructions;
    }

    /**
     * Evaluates the repeat count, validates it, then executes body in a loop.
     */
    @Override
    public void execute(Environment<Object> env) {
        Object value = repeatCountExpression.evaluate(env);
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Repeat count must be numeric but got: " + value);
        }

        int repeatCount = (int) Math.floor(((Number) value).doubleValue());
        if (repeatCount < 0) {
            throw new IllegalArgumentException("Repeat count cannot be negative: " + repeatCount);
        }

        for (int i = 0; i < repeatCount; i++) {
            for (Instruction instruction : bodyInstructions) {
                instruction.execute(env);
            }
        }
    }
}
