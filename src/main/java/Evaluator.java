/**
 * Generic evaluator contract.
 *
 * @param <T> parsed artifact type that can be evaluated
 */
public interface Evaluator<T> {
    void evaluate(T parsedProgram);
}
