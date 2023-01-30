import javax.swing.JFrame;

public class Launch extends JFrame {

    // Variables declaration - do not modify
    public static int HEIGHT = 425;
    public static int WIDTH = 700;
    public static MenuPanel menuPanel;
    public static Launch MainWindow;
    // End of variables declaration

    public Launch() {
        menuPanel = new MenuPanel(this);
        this.add(menuPanel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Launch.WIDTH, Launch.HEIGHT);
        this.setLocationRelativeTo(null);
    }

    public Launch(Boolean b) {
        this.add(new GamePanel());
        this.setTitle("Game Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MainWindow = new Launch(true);
        MainWindow.setVisible(true);
    }
}
