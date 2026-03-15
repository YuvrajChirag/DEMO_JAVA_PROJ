import java.util.List;

public class RepeatInstruction implements Instruction {
    private final Expression countExpression;
    private final List<Instruction> body;

    public RepeatInstruction(Expression countExpression, List<Instruction> body) {
        this.countExpression = countExpression;
        this.body = body;
    }

    @Override
    public void execute(Environment env) {
        Object countValue = countExpression.evaluate(env);
        if (!(countValue instanceof Number)) {
            throw new RuntimeException("Repeat count must be numeric but got: " + countValue);
        }

        int count = (int) Math.floor(((Number) countValue).doubleValue());
        for (int i = 0; i < count; i++) {
            for (Instruction instruction : body) {
                instruction.execute(env);
            }
        }
    }
}
