/**
 * Utility methods for value conversion, validation, and display formatting.
 * <p>
 * Centralizing these operations keeps evaluation logic focused and avoids
 * duplicating type-checking code across expression and instruction classes.
 */
public final class ValueHelper {
    private ValueHelper() {
        // Utility class
    }

    /**
     * Converts a runtime value to a double or throws a descriptive error.
     */
    public static double asNumber(Object value, String context) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new RuntimeException(context + " must be numeric but got: " + value);
    }

    /**
     * Requires the runtime value to be a boolean.
     */
    public static boolean asBoolean(Object value, String context) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new RuntimeException(context + " must evaluate to Boolean but got: " + value);
    }

    /**
     * Formats values for user-facing output.
     * Whole numeric values are printed as integers for cleaner output.
     */
    public static String formatForDisplay(Object value) {
        if (value instanceof Number) {
            double numericValue = ((Number) value).doubleValue();
            if (numericValue == Math.rint(numericValue)) {
                return Long.toString((long) numericValue);
            }
        }
        return String.valueOf(value);
    }
}
