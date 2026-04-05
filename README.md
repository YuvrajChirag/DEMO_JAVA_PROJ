# 📌 Mini Calc Language Interpreter

A lightweight Java interpreter for a custom, indentation-aware scripting language.
It processes source code in three stages—**Tokenizer → Parser → Evaluator**—to execute assignments, arithmetic/string expressions, conditionals, loops, and print statements.

---

## ⚙️ How the System Works (Tokenizer → Parser → Evaluator)

1. **Tokenizer**
   - Reads raw source text line by line.
   - Normalizes line endings.
   - Converts characters into tokens (numbers, strings, identifiers, operators, control symbols).
   - Emits structural tokens (`INDENT`, `DEDENT`, `NEWLINE`) for block-aware syntax.

2. **Parser**
   - Consumes the token stream.
   - Builds an internal executable representation:
     - **Instructions** (`AssignInstruction`, `PrintInstruction`, `IfInstruction`, `RepeatInstruction`)
     - **Expression nodes** (`NumberNode`, `StringNode`, `VariableNode`, `BinaryOpNode`)
   - Enforces grammar rules and provides line-aware parse errors.

3. **Evaluator (Execution Engine)**
   - Executes instructions in order using a shared `Environment` (variable store).
   - Evaluates expressions recursively.
   - Produces output for print instructions.

---

## 🧠 Module Ownership

- **Tokenizer** – implemented by **Shipi Shaw**
- **Parser** – implemented by **Mausam Kumari**
- **Evaluator** – implemented by **Yuvraj Chirag**

---

## 🚀 Setup & Usage Instructions

### Prerequisites
- Java 17+ (or Java 11+ if your environment supports `Files.readString`)

### Compile
```bash
javac src/main/java/*.java
```

### Run
```bash
java -cp src/main/java Main examples/program1.calc
```

### Run with other examples
```bash
java -cp src/main/java Main examples/program2.calc
java -cp src/main/java Main examples/program3.calc
java -cp src/main/java Main examples/program4.calc
```

---

## 📂 Project Structure

```text
DEMO_JAVA_PROJ/
├── examples/
│   ├── program1.calc
│   ├── program2.calc
│   ├── program3.calc
│   └── program4.calc
├── src/main/java/
│   ├── Main.java
│   ├── Interpreter.java
│   ├── Tokenizer.java
│   ├── Parser.java
│   ├── Token.java
│   ├── TokenType.java
│   ├── Environment.java
│   ├── Instruction.java
│   ├── InstructionExecutor.java
│   ├── AssignInstruction.java
│   ├── PrintInstruction.java
│   ├── IfInstruction.java
│   ├── RepeatInstruction.java
│   ├── Expression.java
│   ├── NumberNode.java
│   ├── StringNode.java
│   ├── VariableNode.java
│   ├── BinaryOpNode.java
│   └── ValueHelper.java
└── README.md
```

---

## 🧩 Example Input & Output

### Example (`examples/program1.calc`)
```text
x := 10
y := 20
>> x + y
```

### Output
```text
30
```

---

## ✨ Features

- Clean **Tokenizer → Parser → Evaluator** architecture.
- Block syntax using indentation (`INDENT` / `DEDENT`) with validation.
- Variable assignment and lookup.
- Arithmetic operations: `+`, `-`, `*`, `/`.
- Comparison and equality: `>`, `<`, `==`.
- String literals and concatenation support via `+`.
- Conditional execution with `? condition =>` blocks.
- Looping with `@ count =>` blocks.
- Line-aware runtime and parse error messages.

---

## 🛠️ Technologies Used

- **Java** (core language features)
- **Java Collections Framework** (`List`, `Map`, `Deque`)
- **NIO** (`java.nio.file.Files`, `Path`) for source file reading

---

## Engineering Notes

This refactor emphasizes maintainability and software engineering best practices:
- **SOLID-aligned structure** through clear class responsibilities.
- **DRY principles** via shared helpers (`ValueHelper`, `InstructionExecutor`).
- **Separation of concerns** across lexical analysis, parsing, and execution.
- **Readable naming** and method extraction to simplify future extension.
- **Inline documentation** for non-trivial logic and design intent.
