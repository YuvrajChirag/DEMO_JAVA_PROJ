/**
 * Assigns evaluated expression value to a named variable.
 */
public class AssignInstruction implements Instruction<Object> {
    private final String variableName;
    private final Expression<Object> expression;

    public AssignInstruction(String variableName, Expression<Object> expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void execute(Environment<Object> env) {
        env.set(variableName, expression.evaluate(env));
    }
}
