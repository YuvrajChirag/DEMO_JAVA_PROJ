package parser;

import environment.Environment;

/**
 * AST node for numeric literals.
 */
public class NumberNode implements Expression<Object> {
    private final double value;

    /**
     * @param value parsed numeric value
     */
    public NumberNode(double value) {
        this.value = value;
    }

    /**
     * Returns the numeric literal value.
     */
    @Override
    public Object evaluate(Environment<Object> env) {
        return value;
    }
}
