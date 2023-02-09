import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SettingPanel extends JPanel implements ActionListener {

    // Variables declaration
    JLabel imgLabel;
    private JButton backBtn;
    private JComboBox<String> LevelDropDown;
    private JSeparator Separator;
    private JSeparator Separator1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JRadioButton level1;
    private JRadioButton level2;
    private JRadioButton level3;
    private JLabel mainTitle;
    // private JPanel MenuPanel;

    // End of variables declaration
    public SettingPanel() {
        imgLabel = new JLabel();
        backBtn = new JButton();
        mainTitle = new JLabel();
        backBtn = new JButton();
        level3 = new JRadioButton();
        level1 = new JRadioButton();
        level2 = new JRadioButton();
        jLabel1 = new JLabel();
        Separator = new JSeparator();
        LevelDropDown = new JComboBox<>();
        jLabel2 = new JLabel();
        Separator1 = new JSeparator();
        imgLabel = new JLabel();

        this.setPreferredSize(new Dimension(Launch.WIDTH, Launch.HEIGHT));
        setLayout(null);

        backBtn.setText("Back");
        backBtn.setFocusable(false);
        backBtn.setBackground(Color.white);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        add(backBtn);
        backBtn.setBounds(550, 10, 130, 30);
        setLayout(null);
        mainTitle.setFont(new Font("Cambria", 1, 36)); 
        mainTitle.setForeground(new Color(51, 0, 0));
        mainTitle.setText("Game Settings");
        add(mainTitle);
        mainTitle.setBounds(210, 30, 340, 70);

        level3.setText("3");
        level3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                level3ActionPerformed(evt);
            }
        });
        add(level3);
        level3.setBounds(450, 110, 40, 20);

        level1.setSelected(true);
        level1.setText("1");
        add(level1);
        level1.setBounds(370, 110, 40, 20);

        level2.setText("2");
        level2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                level2ActionPerformed(evt);
            }
        });
        add(level2);
        level2.setBounds(410, 110, 40, 20);

        jLabel1.setFont(new Font("Segoe UI", 1, 12)); 
        jLabel1.setText("Game Dificulty:");
        add(jLabel1);
        jLabel1.setBounds(260, 110, 110, 16);
        add(Separator);
        Separator.setBounds(260, 130, 220, 10);

        LevelDropDown.setModel(new DefaultComboBoxModel<>(new String[] { "level 1", "level 2", "level 3", "level 4" }));
        add(LevelDropDown);
        LevelDropDown.setBounds(370, 150, 110, 22);

        jLabel2.setFont(new Font("Segoe UI", 1, 12)); 
        jLabel2.setText("Game Level:");
        add(jLabel2);
        jLabel2.setBounds(260, 150, 110, 16);
        add(Separator1);
        Separator1.setBounds(260, 170, 220, 10);

        imgLabel.setIcon(new ImageIcon(getClass().getResource("res/bg.jpg")));
        add(imgLabel);
        imgLabel.setBounds(0, 0, 700, 400);
    }


    private void level2ActionPerformed(ActionEvent evt) {                                       

    }                                      

    private void level3ActionPerformed(ActionEvent evt) {                                       
     
    }   

    private void backBtnActionPerformed(ActionEvent evt) {
        MenuPanel.menuBtnSound.start();
        this.setVisible(false);
        Launch.menuPanel.setVisible(true);
        Launch.MainWindow.setTitle("Game Screen");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
