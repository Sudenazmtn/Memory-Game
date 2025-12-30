package ui;

import controller.color.*;
import controller.math.MathLevel1;
import controller.math.MathLevel2;
import controller.math.MathLevel3;
import controller.math.MathLevel4;
import controller.word.WordLevel1;
import controller.word.WordLevel2;
import controller.word.WordLevel3;
import controller.word.WordLevel4;
import util.*;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private ScoreManager scoreManager;
    private TimeManager timeManager;
    private int currentLevel = 1;
    private GameType selectedType;

    public GameFrame() {
        setTitle("Hafıza Geliştirme Oyunu");
        setSize(650, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        showMainMenu();

        add(mainPanel);
        setVisible(true);
    }

    private void showMainMenu() {
        scoreManager = new ScoreManager();
        timeManager = new TimeManager();
        currentLevel = 1;

        GameTypeSelectPanel selectPanel = new GameTypeSelectPanel(type -> {
            this.selectedType = type;
            startNewLevel();
        });

        mainPanel.add(selectPanel, "MENU");
        cardLayout.show(mainPanel, "MENU");
    }

    private void startNewLevel() {
        JPanel levelPanel = null;
        if (selectedType == GameType.COLOR) {
            switch (currentLevel) {
                case 1 -> levelPanel = new ColorLevel1(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 2 -> levelPanel = new ColorLevel2(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 3 -> levelPanel = new ColorLevel3(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 4 -> levelPanel = new ColorLevel4(scoreManager, timeManager, this::onLevelFinished).startLevel();
            }
        }

        if (selectedType == GameType.WORD) {
            switch (currentLevel) {
                case 1 -> levelPanel = new WordLevel1(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 2 -> levelPanel = new WordLevel2(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 3 -> levelPanel = new WordLevel3(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 4 -> levelPanel = new WordLevel4(scoreManager, timeManager, this::onLevelFinished).startLevel();
            }
        }

        if (selectedType == GameType.MATH) {
            switch (currentLevel) {
                case 1 -> levelPanel = new MathLevel1(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 2 -> levelPanel = new MathLevel2(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 3 -> levelPanel = new MathLevel3(scoreManager, timeManager, this::onLevelFinished).startLevel();
                case 4 -> levelPanel = new MathLevel4(scoreManager, timeManager, this::onLevelFinished).startLevel();
            }
        }

        if (levelPanel != null) {
            mainPanel.add(levelPanel, "LEVEL");
            cardLayout.show(mainPanel, "LEVEL");
        }
    }

    private void onLevelFinished() {
        if (currentLevel < 4) {
            NextLevelPanel nextPanel = new NextLevelPanel(currentLevel, () -> {
                currentLevel++;
                startNewLevel();
            });
            mainPanel.add(nextPanel, "NEXT");
            cardLayout.show(mainPanel, "NEXT");
        } else {
            ResultPanel resultPanel = new ResultPanel(selectedType.name(), scoreManager, timeManager, this::showMainMenu);
            mainPanel.add(resultPanel, "RESULT");
            cardLayout.show(mainPanel, "RESULT");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}