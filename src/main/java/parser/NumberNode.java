package parser;

import environment.Environment;

public class NumberNode implements Expression<Object> {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment<Object> env) {
        return value;
    }
}
