package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    private static final int CANVAS_WIDTH = 500;
    private static final int CANVAS_HEIGHT = 500;
    public static int gridSize = 100;
    public static final int rectSize = CANVAS_WIDTH / gridSize;
    private static int UPDATE_INTERVAL = 150;
    private static final int INCREASE_INTERVAL_BY = 20;
    private static final int DECREASE_INTERVAL_BY = 20;

    private Universe universe;
    private JLabel generationLabel;
    private JLabel aliveLabel;
    private JPanel components;
    private JToggleButton pauseResumeButton;
    private boolean pause = false;
    private boolean restart = false;
    private JTextField speedAlert;

    public GameOfLife() {
        super("Game of Life");
        setLayout(new BorderLayout());

        createComponents();

        universe = new Universe(gridSize);

        DrawCanvas canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        Thread updateThread = new Thread(() -> {
            while (true) {
                update();
                repaint();
                try {
                    while (pause) {
                        Thread.sleep(1000);
                    }
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException ignore) {}
            }
        });

        updateThread.start();
    }

    public void update() {
        if (!pause) {
            if (restart) {
                restart = false;
                universe = new Universe(gridSize);
            }
            universe.generate();
            generationLabel.setText(String.format("Generation #%d", universe.generationNumber));
            aliveLabel.setText(String.format("Alive: %d", universe.numberOfAliveCells));
        }
    }

    private void createComponents() {
        components = new JPanel();
        components.setPreferredSize(new Dimension(250, 250));
        components.setLayout(new BoxLayout(components, BoxLayout.Y_AXIS));
        createText();
        createToggles();
        add(components, BorderLayout.WEST);
    }

    private void createText() {
        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        generationLabel = new JLabel("Generation #0");
        generationLabel.setName("GenerationLabel");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 16);
        generationLabel.setFont(font);
        text.add(generationLabel);
        aliveLabel = new JLabel("Alive: 0");
        aliveLabel.setName("AliveLabel");
        aliveLabel.setFont(font);
        text.add(aliveLabel);
        components.add(text);
    }

    private void createToggles() {
        JPanel toggles = new JPanel();
        toggles.setLayout(new BoxLayout(toggles, BoxLayout.Y_AXIS));
        pauseResumeButton = new JToggleButton("Pause");
        pauseResumeButton.setName("PlayToggleButton");
        toggles.add(pauseResumeButton);
        pauseResumeButton.addActionListener(e -> {
            pause = !pause;
            if (pause) {
                pauseResumeButton.setText("Resume");
            } else {
                pauseResumeButton.setText("Pause");
            }
        });
        JButton restartButton = new JButton("Restart");
        restartButton.setName("ResetButton");
        toggles.add(restartButton);
        restartButton.addActionListener(e -> restart = !restart);
        JButton increaseSpeed = new JButton("Increase Speed");
        toggles.add(increaseSpeed);
        increaseSpeed.addActionListener(e -> {
            if (UPDATE_INTERVAL - DECREASE_INTERVAL_BY <= 0) {
                speedAlert.setText("Can't increase speed further");
            } else {
                UPDATE_INTERVAL -= DECREASE_INTERVAL_BY;
            }
        });
        JButton decreaseSpeed = new JButton("Decrease Speed");
        toggles.add(decreaseSpeed);
        decreaseSpeed.addActionListener(e -> UPDATE_INTERVAL += INCREASE_INTERVAL_BY);
        JPanel alertPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        speedAlert = new JTextField(17);
        speedAlert.setEditable(false);
        Font font2 = new Font("Courier", Font.BOLD, 16);
        speedAlert.setFont(font2);
        alertPanel.add(speedAlert);
        toggles.add(alertPanel);
        components.add(toggles);
    }

    private class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setLayout(new GridLayout(gridSize, gridSize));
            setBackground(Color.WHITE);
            g.setColor(Color.BLACK);
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (universe.currentGrid[i][j] == Cell.ALIVE) {
                        g.drawRect(rectSize * j, rectSize * i, rectSize, rectSize);
                        g.fillRect(rectSize * j, rectSize * i, rectSize, rectSize);
                    } else {
                        g.drawRect(rectSize * j, rectSize * i, rectSize, rectSize);
                    }
                }
            }
        }
    }
}
