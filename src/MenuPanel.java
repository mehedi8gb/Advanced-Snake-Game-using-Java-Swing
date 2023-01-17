import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MenuPanel extends JPanel implements ActionListener {

    // Variables declaration - do not modify
    private JButton continueBtn;
    private JButton exitBtn;
    private JLabel imgLabel;
    private JLabel mainTitle;
    private JButton newGameBtn;
    private JButton settingsBtn;
    public static SettingPanel settingPanel;
    public static JFrame frame;
    public static GamePanel gamePanel;
    public static Game game;
    // End of variables declaration

    public MenuPanel(Game obj) {
        MenuPanel.frame = obj;
        gamePanel = new GamePanel();
        game = new Game(true);
        mainTitle = new JLabel();
        exitBtn = new JButton();
        settingsBtn = new JButton();
        newGameBtn = new JButton();
        continueBtn = new JButton();
        imgLabel = new JLabel();

        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setLayout(null);

        mainTitle.setFont(new Font("Cambria", 1, 36));
        mainTitle.setForeground(new Color(51, 0, 0));
        mainTitle.setText("Welcome Eaters");
        add(mainTitle);
        mainTitle.setBounds(210, 30, 340, 70);

        newGameBtn.setText("New Game");
        newGameBtn.setFocusable(false);
        newGameBtn.setBackground(Color.white);
        newGameBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                newGameBtnMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                newGameBtnMouseExited(evt);
            }
        });
        newGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newGameBtnActionPerformed(evt);
            }
        });
        add(newGameBtn);
        newGameBtn.setBounds(230, 150, 230, 40);

        // -------------- end new btn

        continueBtn.setText("Continue");
        continueBtn.setFocusable(false);
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
        continueBtn.setBounds(230, 200, 230, 40);

        // --------------------- end continueBtn

        settingsBtn.setText("Game Settings");
        settingsBtn.setFocusable(false);
        settingsBtn.setBackground(Color.white);
        settingsBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                settingsBtnMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                settingsBtnMouseExited(evt);
            }
        });
        settingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                settingsBtnActionPerformed(evt);
            }
        });
        add(settingsBtn);
        settingsBtn.setBounds(230, 250, 230, 40);

        // ---------- end setting btn

        exitBtn.setText("Exit");
        exitBtn.setFocusable(false);
        exitBtn.setBackground(Color.white);
        exitBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                exitBtnMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                exitBtnMouseExited(evt);
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });
        add(exitBtn);
        exitBtn.setBounds(230, 300, 230, 40);

        // ---------- end exit btn

        imgLabel.setIcon(new ImageIcon(getClass().getResource("res/bg.jpg")));
        add(imgLabel);
        imgLabel.setBounds(0, 0, 700, 400);
    }

    public void mouseEntered(int i) {
        switch (i) {
            case 1:
                newGameBtn.setBackground(Color.cyan);
                newGameBtn.setFont(new Font("Cambria", 1, 15));
                newGameBtn.setBounds(210, 150, 270, 40);
                break;
            case 2:
                continueBtn.setBackground(Color.cyan);
                continueBtn.setFont(new Font("Cambria", 1, 15));
                continueBtn.setBounds(210, 200, 270, 40);
                break;
            case 3:
                settingsBtn.setBackground(Color.cyan);
                settingsBtn.setFont(new Font("Cambria", 1, 15));
                settingsBtn.setBounds(210, 250, 270, 40);
                break;
            case 4:
                exitBtn.setBackground(Color.cyan);
                exitBtn.setFont(new Font("Cambria", 1, 15));
                exitBtn.setBounds(210, 300, 270, 40);
                break;
        }
    }

    public void mouseExited(int i) {
        switch (i) {
            case 1:
                newGameBtn.setBackground(Color.white);
                newGameBtn.setFont(new Font("Cambria", 1, 12));
                newGameBtn.setBounds(230, 150, 230, 40);
                break;
            case 2:
                continueBtn.setBackground(Color.white);
                continueBtn.setFont(new Font("Cambria", 1, 12));
                continueBtn.setBounds(230, 200, 230, 40);
                break;
            case 3:
                settingsBtn.setBackground(Color.white);
                settingsBtn.setFont(new Font("Cambria", 1, 12));
                settingsBtn.setBounds(230, 250, 230, 40);
                break;
            case 4:
                exitBtn.setBackground(Color.white);
                exitBtn.setFont(new Font("Cambria", 1, 12));
                exitBtn.setBounds(230, 300, 230, 40);
                break;
        }

    }

    // new button

    private void newGameBtnMouseEntered(MouseEvent evt) {
        mouseEntered(1);
    }

    private void newGameBtnMouseExited(MouseEvent evt) {
        mouseExited(1);
    }

    private void newGameBtnActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        Game.HEIGHT = 700;
        frame.setVisible(false);
        game.setVisible(true);

    }
    // continue button

    private void continueBtnActionPerformed(ActionEvent evt) {

    }

    private void continueBtnMouseEntered(MouseEvent evt) {
        mouseEntered(2);
    }

    private void continueBtnMouseExited(MouseEvent evt) {
        mouseExited(2);
    }

    // settings button
    private void settingsBtnActionPerformed(ActionEvent evt) {
        settingPanel = new SettingPanel();
        settingPanel.setVisible(true);
        this.setVisible(false);
        frame.add(settingPanel);
        frame.setTitle("Game Settings");
    }

    private void settingsBtnMouseEntered(MouseEvent evt) {
        mouseEntered(3);
    }

    private void settingsBtnMouseExited(MouseEvent evt) {
        mouseExited(3);
    }

    // exit button
    private void exitBtnActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void exitBtnMouseEntered(MouseEvent evt) {
        mouseEntered(4);
    }

    private void exitBtnMouseExited(MouseEvent evt) {
        mouseExited(4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
