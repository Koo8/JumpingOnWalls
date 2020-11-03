import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        GamePanel panel = new GamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        panel.setBackground(Color.lightGray);
        //getContentPane().add(panel);
        add(panel);
        addKeyListener(new KeyChecker(panel));
        addMouseListener(new MouseChecker(panel));

    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle(" Jumping on Wall");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
       // System.out.println(frame.getInsets().top);  // figure out the top title bar height

    }
}
