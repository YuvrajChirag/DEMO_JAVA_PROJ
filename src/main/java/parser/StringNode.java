package parser;

import environment.Environment;

public class StringNode implements Expression<Object> {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment<Object> env) {
        return value;
    }
}
