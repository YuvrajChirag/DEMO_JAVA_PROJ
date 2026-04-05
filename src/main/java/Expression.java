/**
 * Represents a typed expression node in the AST.
 *
 * @param <T> the evaluated type of this expression
 */
public interface Expression<T> {
    T evaluate(Environment<Object> env);
}
