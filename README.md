# 🧠 Multi-Memory Challenge (Ultimate Edition)

A sophisticated, modular, and educational memory game built with **Java Swing**. This project tests visual, linguistic, and mathematical memory through 12 different levels of increasing difficulty.

---

## 🎮 Game Modes

The game offers three distinct cognitive challenges:

- **🎨 Color Match:** Classic visual memory game where you pair identical colors.
- **🧠 Synonym Match:** Enhances vocabulary by pairing words with their synonyms (e.g., "Fast" and "Quick").
- **➗ Logical Math Match:** Challenges mathematical processing by pairing operations with results (e.g., `2 + 3` matches with `5`).

## ✨ Key Features

- **Progressive Difficulty:** 4 levels per mode. As you progress, the grid size increases (from 2x4 up to 4x5) and the preview time decreases.
- **Smart Math Logic:** The engine recognizes multiple questions with the same answer. Both `2+3` and `4+1` will correctly pair with `5`.
- **Persistent Scoring System:** Game data (Score, Time, Mistakes) is automatically logged into local `.txt` files categorized by game type.
- **Modern UI Flow:** Seamless transitions between the main menu, intermediate "Next Level" screens, and the final results dashboard.
- **MVC Architecture:** Clean separation of concerns between Logic (Controllers), Data (Models), and Interface (UI).

## 🛠️ Technical Stack

- **Language:** Java 17+
- **Framework:** Java Swing / AWT
- **Persistence:** Java File I/O (TXT based logging)
- **Concepts:** Polymorphism, Abstract Classes, Interfaces, Lambda Expressions, and CardLayout Management.

## 📁 Project Structure

```text
src/
├── controller/        # Core logic & abstract level management
│   ├── color/         # Color mode specific levels
│   ├── math/          # Math logic with result-based matching
│   └── word/          # Dictionary-based synonym logic
├── model/             # Data objects (TileData)
├── ui/                # Swing components (Panels, Buttons, Frame)
├── util/              # Managers (Score, Time) and Enums
└── README.md          # Project documentation
```

🚀 Getting Started
1) Clone the repository

2) Compile the project: Ensure you have JDK 17 or higher installed.

3) Run the application: Execute the main method located in ui.GameFrame.


📝 Scoring Rules

Correct Match: +10 Points

Wrong Match: -5 Points

Completion Bonus: Calculated based on the remaining time (Optional feature to add).