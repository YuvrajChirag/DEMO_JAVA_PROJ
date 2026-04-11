# Mini Calc Language Interpreter (Java)

A simple interpreter for a custom indentation-aware scripting language.

## Execution Flow
`Tokenizer -> Parser -> Interpreter`

1. **Tokenizer** converts source code into tokens.
2. **Parser** converts tokens into executable instructions.
3. **Interpreter** executes instructions using runtime variables.

## Project Structure

```text
DEMO_JAVA_PROJ/
├── examples/
│   ├── program1.calc
│   ├── program2.calc
│   ├── program3.calc
│   └── program4.calc
├── src/main/java/
│   ├── Main.java
│   ├── environment/
│   │   └── Environment.java
│   ├── evaluator/
│   │   ├── AssignInstruction.java
│   │   ├── IfInstruction.java
│   │   ├── Instruction.java
│   │   ├── PrintInstruction.java
│   │   └── RepeatInstruction.java
│   ├── interpreter/
│   │   └── Interpreter.java
│   ├── parser/
│   │   ├── BinaryOpNode.java
│   │   ├── Expression.java
│   │   ├── NumberNode.java
│   │   ├── Parser.java
│   │   ├── StringNode.java
│   │   └── VariableNode.java
│   └── scanner/
│       ├── Token.java
│       ├── TokenType.java
│       └── Tokenizer.java
└── README.md
```

## Requirements
- Java 17+

## Compile
```bash
javac $(find src/main/java -name "*.java")
```

## Run
```bash
java -cp src/main/java Main examples/program1.calc
```

## Try Other Examples
```bash
java -cp src/main/java Main examples/program2.calc
java -cp src/main/java Main examples/program3.calc
java -cp src/main/java Main examples/program4.calc
```
