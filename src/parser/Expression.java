package parser;

import environment.Environment;

/**
 * Generic expression node that can be evaluated against runtime variables.
 *
 * @param <T> resulting value type
 */
public interface Expression<T> {
    /**
     * Evaluates the expression.
     *
     * @param env variable environment
     * @return expression result
     */
    T evaluate(Environment<Object> env);
}
