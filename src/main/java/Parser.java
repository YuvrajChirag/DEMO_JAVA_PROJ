/**
 * Generic parser contract.
 *
 * @param <T> parsed output type
 */
public interface Parser<T> {
    T parse();
}
