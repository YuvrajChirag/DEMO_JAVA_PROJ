import java.util.List;
import java.util.function.Function;

/**
 * Coordinates the complete execution pipeline:
 * Tokenizer -> Parser -> Evaluator.
 */
public class Interpreter {
    private final Function<String, List<Token>> tokenizer;
    private final Function<List<Token>, Parser<List<Instruction<Object>>>> parserFactory;
    private final Evaluator<List<Instruction<Object>>> evaluator;

    /**
     * Constructor injection keeps dependencies explicit and testable.
     */
    public Interpreter(
            Function<String, List<Token>> tokenizer,
            Function<List<Token>, Parser<List<Instruction<Object>>>> parserFactory,
            Evaluator<List<Instruction<Object>>> evaluator) {
        this.tokenizer = tokenizer;
        this.parserFactory = parserFactory;
        this.evaluator = evaluator;
    }

    public void run(String sourceCode) {
        List<Token> tokens = tokenizer.apply(sourceCode);
        Parser<List<Instruction<Object>>> parser = parserFactory.apply(tokens);
        List<Instruction<Object>> instructions = parser.parse();
        evaluator.evaluate(instructions);
    }

    public static Interpreter defaultInterpreter() {
        return new Interpreter(
                source -> new Tokenizer(source).tokenize(),
                CalcParser::new,
                new InstructionEvaluator(new Environment<>()));
    }
}
