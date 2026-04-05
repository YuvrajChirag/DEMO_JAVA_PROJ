/**
 * Represents an executable program instruction.
 *
 * @param <T> value type stored in the runtime environment
 */
public interface Instruction<T> {
    void execute(Environment<T> env);
}
