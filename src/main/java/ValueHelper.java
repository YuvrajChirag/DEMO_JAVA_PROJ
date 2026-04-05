/**
 * Utility methods for value conversion, validation, and display formatting.
 */
public final class ValueHelper {
    private ValueHelper() {
    }

    public static double asNumber(Object value, String context) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new IllegalArgumentException(context + " must be numeric but got: " + value);
    }

    public static boolean asBoolean(Object value, String context) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new IllegalArgumentException(context + " must evaluate to Boolean but got: " + value);
    }

    /**
     * Generic helper that safely casts runtime values with descriptive errors.
     */
    public static <T> T requireType(Object value, Class<T> type, String context) {
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        throw new IllegalArgumentException(context + " must be of type " + type.getSimpleName() + " but got: " + value);
    }

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
