/**
 * Expression node for reading variable values from the environment.
 */
public class VariableNode implements Expression<Object> {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(Environment<Object> env) {
        return env.get(name);
    }
}
