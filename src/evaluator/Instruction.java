package evaluator;

import environment.Environment;

public interface Instruction {
    void execute(Environment<Object> env);
}
