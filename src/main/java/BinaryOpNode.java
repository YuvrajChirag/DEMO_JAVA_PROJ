/**
 * Expression node that evaluates binary operations such as arithmetic,
 * comparisons, and equality checks.
 */
public class BinaryOpNode implements Expression {
    private final Expression leftOperand;
    private final String operator;
    private final Expression rightOperand;

    public BinaryOpNode(Expression leftOperand, String operator, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    @Override
    public Object evaluate(Environment env) {
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
                return ValueHelper.asNumber(leftValue, "Left operand for '/'")
                        / ValueHelper.asNumber(rightValue, "Right operand for '/'");
            case ">":
                return ValueHelper.asNumber(leftValue, "Left operand for '>'")
                        > ValueHelper.asNumber(rightValue, "Right operand for '>'");
            case "<":
                return ValueHelper.asNumber(leftValue, "Left operand for '<'")
                        < ValueHelper.asNumber(rightValue, "Right operand for '<'");
            case "==":
                return evaluateEquality(leftValue, rightValue);
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }

    private Object evaluateAddition(Object leftValue, Object rightValue) {
        // Supports string concatenation if either side is textual.
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
