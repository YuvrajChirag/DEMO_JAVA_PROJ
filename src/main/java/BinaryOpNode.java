public class BinaryOpNode implements Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public BinaryOpNode(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftValue = left.evaluate(env);
        Object rightValue = right.evaluate(env);

        switch (operator) {
            case "+":
                if (leftValue instanceof String || rightValue instanceof String) {
                    return String.valueOf(leftValue) + rightValue;
                }
                return toDouble(leftValue) + toDouble(rightValue);
            case "-":
                return toDouble(leftValue) - toDouble(rightValue);
            case "*":
                return toDouble(leftValue) * toDouble(rightValue);
            case "/":
                return toDouble(leftValue) / toDouble(rightValue);
            case ">":
                return toDouble(leftValue) > toDouble(rightValue);
            case "<":
                return toDouble(leftValue) < toDouble(rightValue);
            case "==":
                if (leftValue instanceof Number && rightValue instanceof Number) {
                    return Double.compare(toDouble(leftValue), toDouble(rightValue)) == 0;
                }
                return leftValue == null ? rightValue == null : leftValue.equals(rightValue);
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }

    private double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new RuntimeException("Expected numeric value but got: " + value);
    }
}
