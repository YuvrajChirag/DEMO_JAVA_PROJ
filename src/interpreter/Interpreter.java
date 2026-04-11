package interpreter;

import environment.Environment;
import evaluator.Instruction;
import parser.Parser;
import scanner.Token;
import scanner.Tokenizer;

import java.util.List;

public class Interpreter {
    private final Environment<Object> environment = new Environment<>();

    public void run(String sourceCode) {
        List<Token> tokens = new Tokenizer(sourceCode).tokenize();
        List<Instruction> instructions = new Parser(tokens).parse();
        for (Instruction instruction : instructions) {
            instruction.execute(environment);
        }
    }
}
