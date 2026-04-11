package evaluator;

import environment.Environment;
import parser.Expression;

import java.util.List;

public class IfInstruction implements Instruction {
    private final Expression<Boolean> conditionExpression;
    private final List<Instruction> bodyInstructions;

    public IfInstruction(Expression<Boolean> conditionExpression, List<Instruction> bodyInstructions) {
        this.conditionExpression = conditionExpression;
        this.bodyInstructions = bodyInstructions;
    }

    @Override
    public void execute(Environment<Object> env) {
        if (conditionExpression.evaluate(env)) {
            for (Instruction instruction : bodyInstructions) {
                instruction.execute(env);
            }
        }
    }
}
