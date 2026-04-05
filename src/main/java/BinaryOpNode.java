/**
 * Expression node that evaluates arithmetic, comparison, and equality operations.
 */
public class BinaryOpNode implements Expression<Object> {
    private final Expression<Object> leftOperand;
    private final String operator;
    private final Expression<Object> rightOperand;

    public BinaryOpNode(Expression<Object> leftOperand, String operator, Expression<Object> rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    @Override
    public Object evaluate(Environment<Object> env) {
        Object leftValue = leftOperand.evaluate(env);
        Object rightValue = rightOperand.evaluate(env);

        switch (operator) {
            case "+":
                return evaluateAddition(leftValue, rightValue);
            case "-":
                return ValueHelper.asNumber(leftValue, "Left operand for '-'")
                        - ValueHelper.asNumber(rightValue, "Right operand for '-'");
            case "*":
                return ValueHelper.asNumber(leftValue, "Left operand for '*'")
                        * ValueHelper.asNumber(rightValue, "Right operand for '*'");
            case "/":
                double divisor = ValueHelper.asNumber(rightValue, "Right operand for '/'");
                if (divisor == 0.0d) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return ValueHelper.asNumber(leftValue, "Left operand for '/'") / divisor;
            case ">":
                return ValueHelper.asNumber(leftValue, "Left operand for '>'")
                        > ValueHelper.asNumber(rightValue, "Right operand for '>'");
            case "<":
                return ValueHelper.asNumber(leftValue, "Left operand for '<'")
                        < ValueHelper.asNumber(rightValue, "Right operand for '<'");
            case "==":
                return evaluateEquality(leftValue, rightValue);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private Object evaluateAddition(Object leftValue, Object rightValue) {
        if (leftValue instanceof String || rightValue instanceof String) {
            return String.valueOf(leftValue) + rightValue;
        }
        return ValueHelper.asNumber(leftValue, "Left operand for '+'")
                + ValueHelper.asNumber(rightValue, "Right operand for '+'");
    }

    private boolean evaluateEquality(Object leftValue, Object rightValue) {
        if (leftValue instanceof Number && rightValue instanceof Number) {
            return Double.compare(
                    ValueHelper.asNumber(leftValue, "Left operand for '=='"),
                    ValueHelper.asNumber(rightValue, "Right operand for '=='")) == 0;
        }
        return leftValue == null ? rightValue == null : leftValue.equals(rightValue);
    }
}
