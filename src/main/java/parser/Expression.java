package parser;

import environment.Environment;

public interface Expression<T> {
    T evaluate(Environment<Object> env);
}
