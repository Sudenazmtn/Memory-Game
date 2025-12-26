import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private final JLabel levelLabel = new JLabel("Level: 1", SwingConstants.LEFT);
    private final JLabel infoLabel  = new JLabel("Hazır", SwingConstants.CENTER);
    private final JLabel scoreLabel = new JLabel("Toplam Puan: 0", SwingConstants.RIGHT);

    private final JButton startBtn = new JButton("Level 1 Başlat");
    private final JButton hintBtn  = new JButton("Yeniden Göster (1/1)");

    private final JPanel boardPanel = new JPanel();

    private TileButton[] tiles;
    private LevelController controller;

    public GameFrame() {
        super("Zihin Güçlendirme - Level 1-2-3");

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel top = new JPanel(new BorderLayout(10,10));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel left = new JPanel(new GridLayout(2,1));
        left.add(levelLabel);
        left.add(new JLabel(""));

        JPanel center = new JPanel(new GridLayout(2,1));
        center.add(infoLabel);
        center.add(scoreLabel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.add(hintBtn);
        buttons.add(startBtn);

        top.add(left, BorderLayout.WEST);
        top.add(center, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.EAST);

        boardPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 520);
        setLocationRelativeTo(null);

        startBtn.addActionListener(e -> {
            if (controller != null) controller.startLevel();
            refreshScore();
        });

        hintBtn.addActionListener(e -> {
            if (controller != null && controller.hasHint()) controller.useHint();
        });

        buildLevel1();
        setVisible(true);
    }

    private void buildLevel1() {
        levelLabel.setText("Level: 1");
        startBtn.setText("Level 1 Başlat");
        hintBtn.setEnabled(false);

        rebuildBoard(2,5);

        controller = new Level1Controller(
                tiles, infoLabel, scoreLabel,
                this::buildLevel2
        );

        attachTileListeners();
        refreshScore();
    }

    private void buildLevel2() {
        levelLabel.setText("Level: 2");
        startBtn.setText("Level 2 Başlat");
        hintBtn.setEnabled(false); // oyun başlayınca controller açacak

        rebuildBoard(2,8);

        controller = new Level2Controller(
                tiles, infoLabel, scoreLabel,
                hintBtn,
                this::buildLevel3
        );

        attachTileListeners();
        refreshScore();
    }

    private void buildLevel3() {
        levelLabel.setText("Level: 3");
        startBtn.setText("Level 3 Başlat");
        hintBtn.setEnabled(false);

        rebuildBoard(4,4);

        controller = new Level3Controller(
                tiles, infoLabel, scoreLabel
        );

        attachTileListeners();
        refreshScore();
    }

    private void rebuildBoard(int rows, int cols) {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(rows, cols, 10, 10));

        tiles = new TileButton[rows * cols];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new TileButton(i);
            boardPanel.add(tiles[i]);
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void attachTileListeners() {
        for (TileButton t : tiles) {
            t.addActionListener(e -> {
                if (controller != null) controller.onTileClick(t);
                refreshScore();
            });
        }
    }

    private void refreshScore() {
        scoreLabel.setText("Toplam Puan: " + ScoreManager.getTotalScore());
    }
}