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
    private static int Delay = 90;
    private static int gameUnits = (WIDTH*HEIGHT)/(unitSize * unitSize);
    private static int x[] = new int[gameUnits];
    private static int y[] = new int[gameUnits];
    private int body = 6;
    private Boolean running = false;
    private int fruitSize = 0, fruitCount = 0;
    private int fruitEaten = 0;
    private int fruitX;
    private int fruitY;
    private char direction = 'R';
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

    private void loadImage(){
        ImageIcon iia = new ImageIcon("res/fruit.png");
        fruit = iia.getImage();
        ImageIcon iiaa = new ImageIcon("res/fruit-big.png");
        bigFruit = iiaa.getImage();

        ImageIcon UpHead = new ImageIcon("res/head-U.png");
        head_U = UpHead.getImage();
        ImageIcon DownHead = new ImageIcon("res/head-D.png");
        head_D = DownHead.getImage();
        ImageIcon LeftHead = new ImageIcon("res/head-L.png");
        head_L = LeftHead.getImage();
        ImageIcon RightHead = new ImageIcon("res/head-R.png");
        head_R = RightHead.getImage();

        ImageIcon bX = new ImageIcon("res/body-X.png");
        body_X = bX.getImage();
        ImageIcon bY = new ImageIcon("res/body-Y.png");
        body_Y = bY.getImage();

        ImageIcon tR = new ImageIcon("res/tail-R.png");
        tail_R = tR.getImage();
        ImageIcon tL = new ImageIcon("res/tail-L.png");
        tail_L = tL.getImage();
        ImageIcon tU = new ImageIcon("res/tail-U.png");
        tail_U = tU.getImage();
        ImageIcon tD = new ImageIcon("res/tail-D.png");
        tail_D = tD.getImage();
    }

    public void startGame() {
        newfruit();
        timer = new Timer(Delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // for (int i = 0; i < HEIGHT / unitSize; i++) {
        //     g.drawLine(i * unitSize, 0, i * unitSize, HEIGHT);
        //     g.drawLine(0, i * unitSize, WIDTH, i * unitSize);
        // }

        if (running && !gameOver) {
            gameRunning(g);
        } else if (!running && !gameOver) {
            gamePaused(g);
        } else if (!running && gameOver) {
            gameOver(g);
        }

        // ////// for testing //
        // g.drawOval(fruitX, Delay, WIDTH, HEIGHT);
        // g.setColor(Color.white);
    }

    private void gameRunning(Graphics g) {

        // draw fruit
        if (fruitCounter) {
            g.setColor(Color.RED);
            // g.fillOval(fruitX, fruitY, unitSize * 2, unitSize * 2);
            g.drawImage(bigFruit, fruitX,fruitY,this);
            // fruitCounter = false;
        } else {
            // g.setColor(Color.GREEN);
            // g.fillOval(fruitX, fruitY, unitSize, unitSize);
            g.drawImage(fruit, fruitX,fruitY,this);
        }

        // draw snake
        // for (int i = 0; i < body; i++) {
        //     if (i == 0) {
        //         g.setColor(Color.RED);
        //         g.fillRect(x[i], y[i], unitSize, unitSize);
        //         // g.draw3DRect(x[i], y[i], 18, 18, getFocusTraversalKeysEnabled());
        //         // g.drawOval(x[i], y[i], 18, 18);
        //         // g.drawLine(x[i], y[i], 18, 18);
        //         // g.fill3DRect(x[i], y[i], 18, 18, true);
        //     } else {
        //         g.setColor(Color.white);

        //         g.fillRect(x[i], y[i], unitSize, unitSize);
        //         // g.fillRoundRect(x[i], y[i], unitSize, unitSize,WIDTH, HEIGHT);
        //         // g.fill3DRect(x[i], y[i], 18, 18, false);
        //     }
        // }


        for(int i = 0; i< body;i++) {
				/* head */
				if(i == 0) {
					
					
					/* Fill head based on the current direction */
					if(direction == 'U') {
						// g.fillArc(x[i], y[i], unitSize, unitSize, 135, 270);
						// g.fillRect(x[i], y[i]+ unitSize/2, unitSize, unitSize/2);
                        g.drawImage(head_U, x[i], y[i],this);

					} 
					else if(direction == 'D') {
						// g.fillArc(x[i], y[i], unitSize, unitSize, 315, 270);
						// g.fillRect(x[i], y[i], unitSize, unitSize/2);
                        g.drawImage(head_D, x[i], y[i],this);
					}
					else if(direction == 'L') {
						// g.fillArc(x[i], y[i], unitSize, unitSize, 225, 270);
						// g.fillRect(x[i] + unitSize/2, y[i], unitSize/2, unitSize);
                        g.drawImage(head_L, x[i], y[i],this);
					}	
					else {
						// g.fillArc(x[i], y[i], unitSize, unitSize, 45, 270);
						// g.fillRect(x[i], y[i], unitSize/2, unitSize);
                        g.drawImage(head_R, x[i], y[i],this);
					}
				}
				/* body */
				else {
					// g.setColor(Color.green);
					// g.fillOval(x[i], y[i], unitSize, unitSize);
                    if (direction == 'R' || direction == 'L' ) {
                        g.drawImage(body_X,x[i],y[i],this);
                    }else {
                        g.drawImage(body_Y,x[i],y[i],this);
                    }
                    int balance = 0;
                    // if (balance == body) {
                    //     balance = 0;
                    // }
                    if (i+1 == body) {
                        // --balance;
                        if (direction == 'R' || balance == 0) {
                            // balance = body;
                            g.drawImage(tail_R,x[i+1],y[i+1],this);
                        //     g.setColor(Color.green);
						// g.fillOval(x[i]+1 + unitSize, y[i]+1, unitSize, unitSize);
                        } 
                        else if (direction == 'L' || balance == 0){
                            // balance = body;
                            g.drawImage(tail_L,x[i+1],y[i+1],this);
                        }
                        else if (direction == 'U' || balance == 0){
                            g.drawImage(tail_U,x[i+1],y[i+1],this);
                        }
                        else if (direction == 'D' || balance == 0){
                            g.drawImage(tail_D,x[i+1],y[i+1],this);
                        }
                
                    }
                  

				}	
            }

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

            fruitEaten++;
            body++;
            fruitCount++;
            fruitCounter = false;
            newfruit();
        }
    }

    private void newfruit() {
        // fruitX = random.nextInt((WIDTH / unitSize)) * (unitSize);
        // fruitY = random.nextInt((HEIGHT / unitSize)) * (unitSize);
        boolean found = false;
		boolean collision;
        while(!found) {
			collision = false;
			fruitX = random.nextInt((int)(WIDTH/unitSize))*unitSize;
			fruitY = random.nextInt((int)(HEIGHT/unitSize))*unitSize;
			
			/* Dont spawn fruit on body parts */
			/* For every body part, check if newfruit is in the same coordinate */
			for(int i = body;i>0;i--) {
				if((fruitX == x[i]) && (fruitY== y[i])) {
					collision = true;
					break;
				}
			}
			
			/* Dont spawn fruit on walls */
			/* For every wall, check if newfruit is in the same coordinate */
			// for(int i = numWalls;i>0;i--) {
			// 	if((fruitX == wallX[i]) && (fruitY == wallY[i])) {
			// 		collision = true;
			// 		break;
			// 	}
			// }
			
			if(!collision) {
				found = true;
			}
		}
    }

    private void gamePaused(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(fruitX, fruitY, unitSize, unitSize);

        for (int i = 0; i < body; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRect(x[i], y[i], unitSize, unitSize);
            } else {
                g.setColor(Color.white);
                g.fillRect(x[i], y[i], unitSize, unitSize);
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
        fruitEaten = 0;
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
            checkfruit();
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