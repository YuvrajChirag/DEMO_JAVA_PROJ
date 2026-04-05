# 📌 Project Title & Description
## Mini Calc Language Interpreter (Java)

A modular interpreter for a custom indentation-aware scripting language. The project is structured around a clean execution pipeline and applies software engineering best practices (SOLID, DRY, high cohesion, and loose coupling) to keep the codebase maintainable and extensible.

---

## ⚙️ Working Flow
`Tokenizer → Parser → Evaluator`

1. **Tokenizer** converts raw source code into lexical tokens (`Token`).
2. **Parser** builds typed instruction/expression objects from tokens.
3. **Evaluator** executes parsed instructions against an environment.

---

## 🧠 Module Responsibilities
- **Tokenizer – Shipi Shaw**
- **Parser – Mausam Kumari**
- **Evaluator – Yuvraj Chirag**

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
│   ├── Token.java
│   ├── TokenType.java
│   ├── Parser.java
│   ├── CalcParser.java
│   ├── Evaluator.java
│   ├── InstructionEvaluator.java
│   ├── Instruction.java
│   ├── InstructionExecutor.java
│   ├── Expression.java
│   ├── LiteralNode.java
│   ├── NumberNode.java
│   ├── StringNode.java
│   ├── VariableNode.java
│   ├── BinaryOpNode.java
│   ├── AssignInstruction.java
│   ├── PrintInstruction.java
│   ├── IfInstruction.java
│   ├── RepeatInstruction.java
│   ├── Environment.java
│   └── ValueHelper.java
└── README.md
```

---

## 🚀 Setup & Usage Instructions

### Prerequisites
- Java 17+

### Compile
```bash
javac src/main/java/*.java
```

### Run
```bash
java -cp src/main/java Main examples/program1.calc
```

### Try More Examples
```bash
java -cp src/main/java Main examples/program2.calc
java -cp src/main/java Main examples/program3.calc
java -cp src/main/java Main examples/program4.calc
```

---

## 🧩 Example Input & Output

### Input (`examples/program1.calc`)
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
- Layered architecture: **Tokenizer → Parser → Evaluator**.
- Generics for reusable contracts and typed AST components:
  - `Expression<T>`
  - `Instruction<T>`
  - `Parser<T>`
  - `Evaluator<T>`
  - `Environment<T>`
  - `LiteralNode<T>`
- Dependency injection in `Interpreter` for better testability.
- Better runtime validation (e.g., division by zero, negative repeat count).
- Clear comments and descriptive method names for maintainability.

---

## 🛠️ Technologies Used
- **Java 17**
- **Java Collections Framework** (`List`, `Map`, `Deque`)
- **Java NIO** (`Files`, `Path`) for file IO
- **Functional interfaces** (`Function`) for dependency wiring
