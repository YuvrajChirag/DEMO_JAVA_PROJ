package parser;

import environment.Environment;

/**
 * AST node for variable references.
 */
public class VariableNode implements Expression<Object> {
    private final String name;

    /**
     * @param name variable identifier
     */
    public VariableNode(String name) {
        this.name = name;
    }

    /**
     * Resolves and returns the variable value from the environment.
     */
    @Override
    public Object evaluate(Environment<Object> env) {
        return env.get(name);
    }
}
