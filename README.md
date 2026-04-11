# Mini Calc Language Interpreter (Java)

A simple interpreter for a custom indentation-aware scripting language.

## ✨ Features
- Layered architecture: **Tokenizer → Parser → Evaluator**.
- Clear separation of responsibilities across scanner, parser, and evaluator modules.
- Supports assignments, arithmetic expressions, print statements, conditionals, and repeat blocks.
- Clean project organization for maintainability.

## 🛠️ Technologies Used
- **Java 17**
- **Java Collections Framework** (`List`, `Map`, `Deque`)
- **Java NIO** (`Files`, `Path`) for file IO

## 👥 Module Ownership
- **Tokenizer**: Shipli Shaw
- **Parser**: Mausam Kumari
- **Evaluator**: Yuvraj Chirag

## ⚙️ Working Flow
`Tokenizer → Parser → Evaluator`

1. **Tokenizer** converts raw source code into lexical tokens (`Token`).
2. **Parser** builds instruction/expression objects from tokens.
3. **Evaluator** executes parsed instructions against an environment.

## 📁 Project Structure

```text
DEMO_JAVA_PROJ/
├── examples/
│   ├── program1.calc
│   ├── program2.calc
│   ├── program3.calc
│   └── program4.calc
├── src/
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
│   ├── scanner/
│   │   ├── Token.java
│   │   ├── Tokenizer.java
│   │   └── TokenType.java
│   └── Main.java
└── README.md
```

## ▶️ Compile
```bash
javac $(find src -name "*.java")
```

## ▶️ Run
```bash
java -cp src Main examples/program1.calc
```

## 🔁 Try Other Examples
```bash
java -cp src Main examples/program2.calc
java -cp src Main examples/program3.calc
java -cp src Main examples/program4.calc
```

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
