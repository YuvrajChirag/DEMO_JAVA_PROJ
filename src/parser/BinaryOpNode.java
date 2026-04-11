package parser;

import environment.Environment;

/**
 * AST node for binary operators like +, -, *, /, >, <, and ==.
 */
public class BinaryOpNode implements Expression<Object> {
    private final Expression<Object> leftOperand;
    private final String operator;
    private final Expression<Object> rightOperand;

    /**
     * @param leftOperand left-hand side expression
     * @param operator operator symbol
     * @param rightOperand right-hand side expression
     */
    public BinaryOpNode(Expression<Object> leftOperand, String operator, Expression<Object> rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    /**
     * Evaluates both sides and applies the binary operator semantics.
     */
    @Override
    public Object evaluate(Environment<Object> env) {
        Object leftValue = leftOperand.evaluate(env);
        Object rightValue = rightOperand.evaluate(env);

        switch (operator) {
            case "+":
                if (leftValue instanceof String || rightValue instanceof String) {
                    return String.valueOf(leftValue) + rightValue;
                }
                return asNumber(leftValue) + asNumber(rightValue);
            case "-":
                return asNumber(leftValue) - asNumber(rightValue);
            case "*":
                return asNumber(leftValue) * asNumber(rightValue);
            case "/":
                double divisor = asNumber(rightValue);
                if (divisor == 0.0d) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return asNumber(leftValue) / divisor;
            case ">":
                return asNumber(leftValue) > asNumber(rightValue);
            case "<":
                return asNumber(leftValue) < asNumber(rightValue);
            case "==":
                if (leftValue instanceof Number && rightValue instanceof Number) {
                    return Double.compare(asNumber(leftValue), asNumber(rightValue)) == 0;
                }
                return leftValue == null ? rightValue == null : leftValue.equals(rightValue);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    /**
     * Converts an evaluated value into a double for numeric operators.
     */
    private double asNumber(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        throw new IllegalArgumentException("Expected numeric value but got: " + value);
    }
}
