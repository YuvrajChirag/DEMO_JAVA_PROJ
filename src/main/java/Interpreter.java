import java.util.List;

/**
 * Coordinates the full language pipeline:
 * Tokenizer -> Parser -> Evaluator (instruction execution).
 */
public class Interpreter {
    public void run(String sourceCode) {
        List<Token> tokens = tokenize(sourceCode);
        List<Instruction> instructions = parse(tokens);
        execute(instructions);
    }

    private List<Token> tokenize(String sourceCode) {
        return new Tokenizer(sourceCode).tokenize();
    }

    private List<Instruction> parse(List<Token> tokens) {
        return new Parser(tokens).parse();
    }

    private void execute(List<Instruction> instructions) {
        Environment environment = new Environment();
        InstructionExecutor.executeAll(instructions, environment);
    }
}
