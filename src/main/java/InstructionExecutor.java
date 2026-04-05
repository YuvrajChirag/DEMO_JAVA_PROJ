import java.util.List;

/**
 * Executes instruction lists against a shared environment.
 */
public final class InstructionExecutor {
    private InstructionExecutor() {
    }

    public static <T> void executeAll(List<Instruction<T>> instructions, Environment<T> environment) {
        for (Instruction<T> instruction : instructions) {
            instruction.execute(environment);
        }
    }
}
