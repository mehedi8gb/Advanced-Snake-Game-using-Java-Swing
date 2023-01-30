import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SettingPanel extends JPanel implements ActionListener {

    // Variables declaration
    JLabel imgLabel;
    private JButton backBtn;
    // private JPanel MenuPanel;

    // End of variables declaration
    public SettingPanel() {
        imgLabel = new JLabel();
        backBtn = new JButton();

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

        imgLabel.setIcon(new ImageIcon(getClass().getResource("res/bg.jpg")));
        add(imgLabel);
        imgLabel.setBounds(0, 0, 700, 400);
    }

    private void backBtnActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        Launch.menuPanel.setVisible(true);
        Launch.MainWindow.setTitle("Game Screen");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
