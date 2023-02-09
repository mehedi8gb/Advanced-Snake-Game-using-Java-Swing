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
    private static int unitSize = 15;
    private static int Delay = 100;
    private static int gameUnits = (WIDTH * HEIGHT) / (unitSize * unitSize);
    private static int x[] = new int[gameUnits];
    private static int y[] = new int[gameUnits];
    private int body = 8;
    private Boolean running = false;
    private int fruitSize = 0, fruitCount = 0;
    private int fruitEaten = 0;
    private int fruitX;
    private int fruitY;
    private char direction = 'R';
    public boolean directionChanged = false;
    private boolean gameOver = false;
    public FontMetrics fMetrics;
    private boolean fruitCounter = false;
    private Image fruit;
    private Image head_U;
    private Image head_D;
    private Image head_L;
    private Image head_R;
    private Image body_X;
    private Image body_Y;
    private Image tail_R;
    private Image tail_L;
    private Image tail_D;
    private Image tail_U;
    private Image bigFruit;
    private static Image bgBoard[] = new Image[10];
    private static int level = 1;
    private static AudioPlayer gameOverSound = new AudioPlayer("res/audio/failure.mp3");
    private static AudioPlayer eatSound = new AudioPlayer("res/audio/eating-sound.mp3");
    private static AudioPlayer moveSound = new AudioPlayer("res/audio/move.mp3");

    // End of variables declaration

    public GamePanel() {
        random = new Random();
        continueBtn = new JButton();
        returnMenuBtn = new JButton();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new GameKeyAdapter());
        loadImage();
        startGame();
    }

    /**
     * 
     */
    private void loadImage() {
        try {
            bgBoard[1] = new ImageIcon(getClass().getResource("res/level/Game-Board-lv-1.png")).getImage();
            // bgBoard[2] = new
            // ImageIcon(getClass().getResource("res/level/Game-Board-lv-1.png")).getImage();

            fruit = new ImageIcon(getClass().getResource("res/fruit.png")).getImage();
            bigFruit = new ImageIcon(getClass().getResource("res/fruit-big.png")).getImage();

            head_U = new ImageIcon(getClass().getResource("res/head-U.png")).getImage();
            head_D = new ImageIcon(getClass().getResource("res/head-D.png")).getImage();

            head_L = new ImageIcon(getClass().getResource("res/head-L.png")).getImage();
            head_R = new ImageIcon(getClass().getResource("res/head-R.png")).getImage();

            body_X = new ImageIcon(getClass().getResource("res/body-X.png")).getImage();
            body_Y = new ImageIcon(getClass().getResource("res/body-Y.png")).getImage();

            tail_R = new ImageIcon(getClass().getResource("res/tail-R.png")).getImage();
            tail_L = new ImageIcon(getClass().getResource("res/tail-L.png")).getImage();

            tail_U = new ImageIcon(getClass().getResource("res/tail-U.png")).getImage();
            tail_D = new ImageIcon(getClass().getResource("res/tail-D.png")).getImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        newfruit();
        timer = new Timer(Delay, this);
        timer.start();
        setPositionCenter();
    }

    private void setPositionCenter() {
        x[0] = WIDTH / 2 - unitSize;
        y[0] = HEIGHT / 2 - unitSize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // for (int i = 0; i < HEIGHT / unitSize; i++) {
        // g.drawLine(i * unitSize, 0, i * unitSize, HEIGHT);
        // g.drawLine(0, i * unitSize, WIDTH, i * unitSize);
        // }

        if (running && !gameOver) {
            g.drawImage(bgBoard[level], 0, 0, null);
            gameRunning(g);
        } else if (!running && !gameOver) {
            g.drawImage(bgBoard[level], 0, 0, null);
            gamePaused(g);
        } else if (!running && gameOver) {
            gameOver(g);
        }

        // ////// for testing //
        // g.drawOval(fruitX, Delay, WIDTH, HEIGHT);
        // g.setColor(Color.white);
    }

    private void gameRunning(Graphics g) {

        // draw game components and parts
        drawComponentsAndParts(g, false);

        // set point bar
        g.setColor(Color.blue);
        g.setFont(new Font("Arial Black", Font.ITALIC, 30));
        fMetrics = getFontMetrics(g.getFont());
        g.drawString("Point: " + fruitEaten, (WIDTH - fMetrics.stringWidth("Point: " + fruitEaten)) - 20, 30);
    }

    public void checkfruit() {
        if (fruitCount == 5) {
            fruitCounter = true;
            fruitSize = unitSize;
        }
        if (fruitCount == 6) {
            fruitEaten += 50;
            fruitCount = 0;
            fruitCounter = false;
            fruitSize = 0;
        }
        if (x[0] >= fruitX && x[0] <= fruitX + fruitSize && y[0] >= fruitY && y[0] <= fruitY + fruitSize) {

            eatSound.start();
            fruitEaten++;
            body++;
            fruitCount++;
            fruitCounter = false;
            newfruit();
        }
    }

    private void newfruit() {
        boolean found = false;
        boolean collision;
        while (!found) {
            collision = false;
            fruitX = random.nextInt((int) (WIDTH / unitSize)) * unitSize;
            fruitY = random.nextInt((int) (HEIGHT / unitSize)) * unitSize;

            /* Dont spawn fruit on body parts */
            /* For every body part, check if newfruit is in the same coordinate */
            for (int i = body; i > 0; i--) {
                if ((fruitX == x[i]) && (fruitY == y[i])) {
                    collision = true;
                    break;
                }
            }

            /* Dont spawn fruit on walls */
            /* For every wall, check if newfruit is in the same coordinate */

            if (fruitX <= 15 || fruitX >= 570 || fruitY <= 15 || fruitY >= 570) {
                collision = true;
            }

            if (!collision) {
                found = true;
            }
        }
    }

    private void gamePaused(Graphics g) {
        drawComponentsAndParts(g, false);
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
        g.drawString("Point: " + fruitEaten, (WIDTH - point.stringWidth("Point: " + fruitEaten)) / 2, 90);

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

    private void drawComponentsAndParts(Graphics g, Boolean pause) {

        // draw fruit
        if (fruitCounter) {
            g.setColor(Color.RED);
            g.drawImage(bigFruit, fruitX, fruitY, this);
            // fruitCounter = false;
        } else {
            g.drawImage(fruit, fruitX, fruitY, this);
        }

        if (!pause) {
            for (int i = 0; i < body; i++) {
                /* head */
                if (i == 0) {

                    /* Fill head based on the current direction */
                    if (direction == 'U') {
                        g.drawImage(head_U, x[i], y[i], this);

                    } else if (direction == 'D') {
                        g.drawImage(head_D, x[i], y[i], this);
                    } else if (direction == 'L') {
                        g.drawImage(head_L, x[i], y[i], this);
                    } else {
                        g.drawImage(head_R, x[i], y[i], this);
                    }
                }
                /* body */
                else {

                    int turnX = -1, turnY = -1;
                    if (directionChanged) {
                        turnX = x[0];
                        turnY = y[0];
                    }

                    if (i != body - 1) {
                        if (turnX != -1 && x[i] == turnX && direction == 'R' || direction == 'L') {
                            turnX = -1;

                            g.drawImage(body_X, x[i], y[i], this);
                        } else {
                            g.drawImage(body_X, x[i], y[i], this);
                        }
                        if (turnY != -1 && y[i] == turnY && direction == 'U' || direction == 'D') {
                            turnY = -1;
                            g.drawImage(body_Y, x[i], y[i], this);
                        } else {
                            g.drawImage(body_Y, x[i], y[i], this);
                        }
                    } else {
                        if (turnX != -1 && x[i] == turnX && direction == 'R') {
                            turnX = -1;
                            g.drawImage(tail_R, x[i], y[i], this);
                        }

                    }
                }

            }
        }
    }

    public void move() {
        for (int i = body; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] -= unitSize;
                break;
            case 'D':
                y[0] += unitSize;
                break;
            case 'R':
                x[0] += unitSize;
                break;
            case 'L':
                x[0] -= unitSize;
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
            if (x[0] <= 15) {
                running = false;
                gameOver = true;
            }
            // if head touch right border
            if (x[0] >= 570) {
                running = false;
                gameOver = true;
            }
            // if head touch top border
            if (y[0] <= 15) {
                running = false;
                gameOver = true;
            }
            // if head touch buttom border
            if (y[0] >= 570) {
                running = false;
                gameOver = true;
            }

        }
        if (gameOver) {
            gameOverSound.start();
        } else {
            gameOverSound.stop();
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
        setPositionCenter();
        fruitEaten = 0;
        body = 6;
        paintComponent(getGraphics());
    }

    private void returnMenuBtnActionPerformed(ActionEvent evt) {
        MenuPanel.menuBtnSound.start();
        Launch.HEIGHT = 425;
        Launch.MainWindow.setTitle("Snake Game");
        Launch.menuPanel.setVisible(true);
        MenuPanel.frame.setVisible(true);
        MenuPanel.frame.add(Launch.menuPanel);
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
            checkfruit();
            checkCollisions();
        }
        repaint();

    }

    class GameKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            moveSound.start();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        directionChanged = true;
                        setDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        directionChanged = true;
                        setDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        directionChanged = true;
                        setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        directionChanged = true;
                        setDirection('D');
                    }
                    break;
                case KeyEvent.VK_SPACE:
                System.out.println("\r" + "X: " + x[0] + "; Y : " + y[0]);
                System.out.println("\r" + "f-X: " + fruitX + "; f-Y : " + fruitY);
                    break;
                case KeyEvent.VK_ENTER:
                    timer.start();
                    running = true;
                    gameOver = false;
                    move();
                    checkfruit();
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