package evaluator;

import environment.Environment;

/**
 * Executable instruction unit produced by the parser.
 */
public interface Instruction {
    /**
     * Executes instruction side-effects against runtime environment.
     *
     * @param env runtime variable environment
     */
    void execute(Environment<Object> env);
}
