import javax.swing.*;

public class MyFrame {
    public MyFrame() {
        JFrame frame = new JFrame("Horse Racing Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1920, 1080);
        ImageIcon icon = new ImageIcon("HorseRace Starter\\images\\icon.png");
        frame.setIconImage(icon.getImage());
    }

}
