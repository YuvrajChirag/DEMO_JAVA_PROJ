import java.util.List;

/**
 * Executes instruction lists against a shared environment.
 */
public final class InstructionExecutor {
    private InstructionExecutor() {
        // Utility class
    }

    public static void executeAll(List<Instruction> instructions, Environment environment) {
        for (Instruction instruction : instructions) {
            instruction.execute(environment);
        }
    }
}
