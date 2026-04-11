package environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic runtime environment used by the evaluator layer.
 *
 * @param <T> value type stored in variables
 */
public class Environment<T> {
    private final Map<String, T> values = new HashMap<>();

    /**
     * Stores/updates a variable value in the environment.
     *
     * @param name variable name
     * @param value value to store
     */
    public void set(String name, T value) {
        values.put(name, value);
    }

    /**
     * Reads a variable value from the environment.
     *
     * @param name variable name
     * @return stored value
     */
    public T get(String name) {
        if (!values.containsKey(name)) {
            throw new IllegalStateException("Variable not defined: " + name);
        }
        return values.get(name);
    }
}
