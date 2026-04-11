package interpreter;

import environment.Environment;
import evaluator.Instruction;
import parser.Parser;
import scanner.Token;
import scanner.Tokenizer;

import java.util.List;

/**
 * Orchestrates tokenization, parsing, and instruction execution.
 */
public class Interpreter {
    private final Environment<Object> environment = new Environment<>();

    /**
     * Runs a full source program from raw text.
     *
     * @param sourceCode calculator language source
     */
    public void run(String sourceCode) {
        List<Token> tokens = new Tokenizer(sourceCode).tokenize();
        List<Instruction> instructions = new Parser(tokens).parse();
        for (Instruction instruction : instructions) {
            instruction.execute(environment);
        }
    }
}
