import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.*;

public class GamePanel extends JPanel implements ActionListener {

    // Variables declaration - do not modify
    private JButton continueBtn;
    private JButton returnMenuBtn;
    private Timer timer;
    private Random random;
    private static int HEIGHT = 600;
    private static int WIDTH = 600;
    private static int Unit_size = 15;
    private static int Delay = 80;
    private static int x[] = new int[50000];
    private static int y[] = new int[50000];
    private int body = 6;
    private Boolean running = false;
    private int fruitSize = 0, appleCount = 0;
    private int appleEaten = 0;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean gameOver = false;
    public FontMetrics fMetrics;
    private boolean appleCounter = false;

    // End of variables declaration

    public GamePanel() {
        random = new Random();
        continueBtn = new JButton();
        returnMenuBtn = new JButton();

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new GameKeyAdapter());
        startGame();
        // setLayout(null);
    }

    public void startGame() {
        newApple();
        timer = new Timer(Delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < HEIGHT / Unit_size; i++) {
            g.drawLine(i * Unit_size, 0, i * Unit_size, HEIGHT);
            g.drawLine(0, i * Unit_size, WIDTH, i * Unit_size);
        }
        if (running && !gameOver) {
            gameRunning(g);
        } else if (!running && !gameOver) {
            gamePaused(g);
        } else if (!running && gameOver) {
            gameOver(g);
        }

        // ////// for testing //
        // g.drawOval(appleX, Delay, WIDTH, HEIGHT);
        // g.setColor(Color.white);
    }

    private void gameRunning(Graphics g) {

        // draw apple
        if (appleCounter) {
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, Unit_size * 2, Unit_size * 2);
        } else {
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, Unit_size, Unit_size);
        }

        // draw snake
        for (int i = 0; i < body; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRect(x[i], y[i], Unit_size, Unit_size);
                // g.draw3DRect(x[i], y[i], 18, 18, getFocusTraversalKeysEnabled());
                // g.drawOval(x[i], y[i], 18, 18);
                // g.drawLine(x[i], y[i], 18, 18);
                // g.fill3DRect(x[i], y[i], 18, 18, true);
            } else {
                g.setColor(Color.white);

                g.fillRect(x[i], y[i], Unit_size, Unit_size);
                // g.fillRoundRect(x[i], y[i], Unit_size, Unit_size,WIDTH, HEIGHT);
                // g.fill3DRect(x[i], y[i], 18, 18, false);
            }
        }
        // set point bar
        g.setColor(Color.blue);
        g.setFont(new Font("Arial Black", Font.ITALIC, 30));
        fMetrics = getFontMetrics(g.getFont());
        g.drawString("Point: " + appleEaten, (WIDTH - fMetrics.stringWidth("Point: " + appleEaten)) - 20, 30);
    }

    public void checkApple() {
        if (appleCount == 5) {
            appleCounter = true;
            fruitSize = Unit_size;
        }
        if (appleCount == 6) {
            appleEaten += 50;
            appleCount = 0;
            appleCounter = false;
        }
        if (x[0] >= appleX && x[0] <= appleX + fruitSize
                && y[0] >= appleY && y[0] <= appleY + fruitSize) {

            appleEaten++;
            body++;
            appleCount++;
            appleCounter = false;
            newApple();
        }
    }

    private void newApple() {
        appleX = random.nextInt((WIDTH / Unit_size)) * (Unit_size);
        appleY = random.nextInt((HEIGHT / Unit_size)) * (Unit_size);
    }

    private void gamePaused(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(appleX, appleY, Unit_size, Unit_size);

        for (int i = 0; i < body; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRect(x[i], y[i], Unit_size, Unit_size);
            } else {
                g.setColor(Color.white);
                g.fillRect(x[i], y[i], Unit_size, Unit_size);
            }
        }

        g.setColor(Color.blue);
        g.setFont(new Font("Arial White", Font.BOLD, 30));
        fMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Paused", (WIDTH - fMetrics.stringWidth("Game Paused")) - 20, 30);
        g.setColor(Color.white);
        g.drawString("Press Enter key to continue!",
                (WIDTH - fMetrics.stringWidth("Press Enter key to continue!")) / 2, HEIGHT / 2);
    }

    private void gameOver(Graphics g) {
        continueBtn.setVisible(true);
        returnMenuBtn.setVisible(true);
        g.setColor(Color.blue);
        g.setFont(new Font("Arial Black", Font.ITALIC, 50));
        FontMetrics point = getFontMetrics(g.getFont());
        g.drawString("Point: " + appleEaten, (WIDTH - point.stringWidth("Point: " + appleEaten)) / 2, 90);

        g.setColor(Color.red);
        g.setFont(new Font("Arial Black", Font.BOLD, 80));
        FontMetrics end = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WIDTH - end.stringWidth("Game Over!")) / 2, HEIGHT / 2);

        continueBtn.setText("Continue play");
        continueBtn.setFocusable(true);
        continueBtn.setBackground(Color.white);
        continueBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                continueBtnMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                continueBtnMouseExited(evt);
            }
        });
        continueBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                continueBtnActionPerformed(evt);
            }
        });
        add(continueBtn);
        continueBtn.setBounds(300, 500, 230, 40);

        // returnMenuBtn starts
        returnMenuBtn.setText("Go Menu");
        returnMenuBtn.setFocusable(false);
        returnMenuBtn.setBackground(Color.white);
        returnMenuBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                returnBtnMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                returnBtnMouseExited(evt);
            }
        });
        returnMenuBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                returnMenuBtnActionPerformed(evt);
            }
        });
        add(returnMenuBtn);
        returnMenuBtn.setBounds(50, 500, 230, 40);
    }

    public void move() {
        for (int i = body; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] -= Unit_size;
                break;
            case 'D':
                y[0] += Unit_size;
                break;
            case 'R':
                x[0] += Unit_size;
                break;
            case 'L':
                x[0] -= Unit_size;
                break;
        }
    }

    public void checkCollisions() {
        if (running && !gameOver) {
            for (int i = body; i > 0; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    running = false;
                    gameOver = true;
                }
            }

            // if head touch left border
            if (x[0] < 0) {
                running = false;
                gameOver = true;
            }
            // if head touch right border
            if (x[0] > WIDTH) {
                running = false;
                gameOver = true;
            }
            // if head touch top border
            if (y[0] < 0) {
                running = false;
                gameOver = true;
            }
            // if head touch buttom border
            if (y[0] > HEIGHT) {
                running = false;
                gameOver = true;
            }
        }
        if (!running) {
            timer.stop();
        }
    }

    public void setDirection(char dir) {
        direction = dir;
    }

    private void continueBtnActionPerformed(ActionEvent evt) {
        running = false;
        gameOver = false;
        setDirection('R');
        continueBtn.setVisible(false);
        returnMenuBtn.setVisible(false);
        x = new int[5000];
        y = new int[5000];
        appleEaten = 0;
        body = 6;
        paintComponent(getGraphics());
    }

    private void returnMenuBtnActionPerformed(ActionEvent evt) {
        Game.HEIGHT = 425;
        Game.MainWindow.setTitle("Game Screen");
        Game.menuPanel.setVisible(true);
        MenuPanel.frame.setVisible(true);
        MenuPanel.frame.add(Game.menuPanel);
        MenuPanel.game.setVisible(false);
    }

    private void continueBtnMouseEntered(MouseEvent evt) {
        mouseEntered(1);
    }

    private void continueBtnMouseExited(MouseEvent evt) {
        mouseExited(1);
    }

    private void returnBtnMouseEntered(MouseEvent evt) {
        mouseEntered(2);
    }

    private void returnBtnMouseExited(MouseEvent evt) {
        mouseExited(2);
    }

    public void mouseEntered(int i) {
        switch (i) {
            case 1:
                continueBtn.setBackground(Color.cyan);
                continueBtn.setFont(new Font("Arial Black", 1, 14));
                break;
            case 2:
                returnMenuBtn.setBackground(Color.cyan);
                returnMenuBtn.setFont(new Font("Arial Black", 1, 14));
                break;
        }
    }

    public void mouseExited(int i) {
        switch (i) {
            case 1:
                continueBtn.setBackground(Color.black);
                continueBtn.setFont(new Font("Cambria", 1, 14));
                break;
            case 2:
                returnMenuBtn.setBackground(Color.black);
                returnMenuBtn.setFont(new Font("Cambria", 1, 14));
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !gameOver) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        setDirection('R');
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        setDirection('D');
                    break;
                case KeyEvent.VK_ENTER:
                    timer.start();
                    running = true;
                    gameOver = false;
                    move();
                    checkApple();
                    checkCollisions();
                    break;
                default:
                    timer.stop();
                    running = false;
                    gameOver = false;
                    paintComponent(getGraphics());
                    break;
            }
        }
    }

}