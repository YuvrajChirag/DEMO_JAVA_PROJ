package evaluator;

import environment.Environment;
import parser.Expression;

/**
 * Assignment instruction: evaluates expression and stores it in a variable.
 */
public class AssignInstruction implements Instruction {
    private final String variableName;
    private final Expression<Object> expression;

    public AssignInstruction(String variableName, Expression<Object> expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    /**
     * Evaluates the right-hand side and updates environment variable.
     */
    @Override
    public void execute(Environment<Object> env) {
        env.set(variableName, expression.evaluate(env));
    }
}
