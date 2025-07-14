import javafx.scene.control.RadioButton;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

    }
    JPanel jPanel = new JPanel();
    JMenu screen = getScreen();
    JMenuBar bar = new JMenuBar();
    RadioButton employee = new RadioButton("Employee");
    RadioButton admin = new RadioButton("Admin");
    JMenuItem menuItem = new JMenuItem();
    JButton loginButton = new JButton("Login");
    JFrame frame = new JFrame();
    JLabel userLabel = new JLabel();
    JLabel passLabel = new JLabel();

    public JFrame getFrame() {
        jPanel.setVisible(true);
        setFrame(frame);
        frame.setVisible(true);
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        frame.setTitle("login");
    }

    public JMenu getScreen() {
        frame = getFrame();
        assert screen != null;
        screen.add(bar);
        screen.add(loginButton);
        assert admin != null;
        admin.setVisible(true);
        admin.setText("Select user");
        assert employee != null;
        employee.setVisible(true);
        screen.add(menuItem);
        assert userLabel != null;
        userLabel.setText("Username:");
        assert passLabel != null;
        passLabel.setText("Password:");
        assert frame != null;
        frame.setJMenuBar(bar);
        return screen;
    }

}
