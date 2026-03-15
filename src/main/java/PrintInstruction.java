public class PrintInstruction implements Instruction {
    private final Expression expression;

    public PrintInstruction(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Environment env) {
        Object value = expression.evaluate(env);
        if (value instanceof Number) {
            double d = ((Number) value).doubleValue();
            if (d == Math.rint(d)) {
                System.out.println((long) d);
                return;
            }
        }
        System.out.println(value);
    }
}
