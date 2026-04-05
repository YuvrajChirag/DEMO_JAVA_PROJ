import java.util.List;

/**
 * Evaluates parsed instruction lists.
 */
public class InstructionEvaluator implements Evaluator<List<Instruction<Object>>> {
    private final Environment<Object> environment;

    public InstructionEvaluator(Environment<Object> environment) {
        this.environment = environment;
    }

    @Override
    public void evaluate(List<Instruction<Object>> parsedProgram) {
        InstructionExecutor.executeAll(parsedProgram, environment);
    }
}
