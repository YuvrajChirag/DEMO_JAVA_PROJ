/**
 * Generic literal expression to avoid duplicated node types.
 *
 * @param <T> literal type
 */
public class LiteralNode<T> implements Expression<T> {
    private final T value;

    public LiteralNode(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(Environment<Object> env) {
        return value;
    }
}
