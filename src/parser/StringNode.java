package parser;

import environment.Environment;

/**
 * AST node for string literals.
 */
public class StringNode implements Expression<Object> {
    private final String value;

    /**
     * @param value parsed string content
     */
    public StringNode(String value) {
        this.value = value;
    }

    /**
     * Returns the literal string value.
     */
    @Override
    public Object evaluate(Environment<Object> env) {
        return value;
    }
}
