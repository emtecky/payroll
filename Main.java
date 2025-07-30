import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public Main() throws SQLException, InterruptedException {

    }

    public static void main(String[] args) throws SQLException, InterruptedException, NoSuchAlgorithmException {
        Main main = new Main();
        main.main_screen.setVisible(true);
        Database database = new Database();
        database.makeConnection();
        main.getFrame();
        main.getLogin(database);
    }
    private JButton loginButton;
    private JButton exitButton;
    private JButton changeButton;
    private JButton enter;
    private final JRadioButton admin = new JRadioButton("Admin");
   private final JRadioButton employee = new JRadioButton("Employee");
    private final ButtonGroup group = new ButtonGroup();
    private JFrame frame = new JFrame("Login");
    private final JPanel jPanel = new JPanel(new BorderLayout());
    private final JPanel rPanel = new JPanel();
   private JMenu main_screen = getScreen();

    MenuListener menuListener = new MenuListener() {
        @Override
        public void menuSelected(MenuEvent e) {
            if(e.getSource()==employeeScreen()){
                employeeScreen().setSelected(true);
            }
        }

        @Override
        public void menuDeselected(MenuEvent e) {
            if(e.getSource().equals(employeeScreen())){
                employeeScreen().setSelected(false);
            }
        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    };
    JMenuBar bar = getBar();
    private JTextField ufield;
    private JPasswordField pfield;
    private JPasswordField cfield;


    public JFrame getFrame() {
        frame.setVisible(true);
        jPanel.setVisible(true);
        rPanel.setVisible(true);
        frame.setContentPane(jPanel);
        frame.add(rPanel,BorderLayout.WEST);
        jPanel.setBackground(Color.pink);
        group.add(employee);
        group.add(admin);
        rPanel.add(employee);
        rPanel.add(admin);
        return frame;
    }


    public JMenuBar getBar() {
        assert bar != null;
        bar.add(ufield);
        bar.add(pfield);
        return bar;
    }

    public JTextField getUfield() {
        ufield = new JTextField(5);
        frame.add(ufield);
        return ufield;
    }
    public JPasswordField Pfield(){
       pfield = new JPasswordField( 5);
        frame.add(pfield);
        return pfield;
    }
    public JMenu adminScreen(){
        JMenu screen2 = new JMenu();
        main_screen.setVisible(false);
        screen2.setVisible(true);
        JMenuBar bar1 = new JMenuBar();
        bar1.setVisible(true);
        JMenu jMenu = new JMenu("Menu");
        JMenuItem employees = new JMenuItem("Employees");
        JMenuItem payroll = new JMenuItem("Payroll");
        jMenu.add(employees);
        jMenu.add(payroll);
        bar1.add(jMenu);
        bar1.setVisible(true);
        bar.setVisible(false);
        return screen2;
    }
    public JMenu employeeScreen(){
        JMenu screen2 = new JMenu();
        frame.add(screen2);
        screen2.addMenuListener(menuListener);
        main_screen.setVisible(false);
        screen2.setVisible(true);
        JMenuBar bar1 = new JMenuBar();
        JMenu jMenu = new JMenu("Menu");
        JMenuItem timesheet = new JMenuItem("Timesheet");
        JMenuItem pto = new JMenuItem("PTO");
       jMenu.add(timesheet);
        jMenu.add(pto);
        bar1.add(jMenu);
        bar1.setVisible(true);
        bar.setVisible(false);
        return screen2;
    }

    Boolean doEnter(){
        return enter.isSelected();
    }
    void setChangeButton(){
        JMenuBar bar2 = new JMenuBar();
         cfield = new JPasswordField(BorderLayout.WEST);
        bar2.add(pfield);
        bar2.add(cfield);
        bar2.add(enter);
        enter.setVisible(true);
        cfield.setVisible(true);
        rPanel.setVisible(false);
        jPanel.add(bar2);
        frame.add(bar2);
        bar2.setVisible(true);
        bar.setVisible(false);
        loginButton.setVisible(false);
        ufield.setVisible(false);
        enter.addActionListener(e -> {
            enter.setSelected(true);
            if(Arrays.equals(cfield.getPassword(), pfield.getPassword())){
                bar2.setVisible(false);
                bar.setVisible(true);
                rPanel.setVisible(true);
                cfield.setVisible(false);
                ufield.setVisible(true);
                bar.add(pfield);
                pfield.setVisible(true);
                loginButton.setVisible(true);
            }
            else System.out.println("Passwords do not match");
        });
    }
    public JMenu getScreen() {
        frame = getFrame();
        assert main_screen != null;
        main_screen = new JMenu();
        assert loginButton != null;
        loginButton = new JButton("login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(e -> {
            loginButton.setSelected(true);
            try {
                getLogin(new Database());
            } catch (SQLException | InterruptedException | NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        });
                loginButton.setEnabled(true);
        assert exitButton != null;
        exitButton = new JButton("exit");
        exitButton.setVisible(true);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setActionCommand("exit");
        assert changeButton != null;
        changeButton = new JButton("Change password");
        changeButton.addActionListener(e -> {
            changeButton.setSelected(true);
            setChangeButton();
        });
        enter = new JButton("Enter");
        enter.setVisible(false);
       frame.add(loginButton);
       frame.add(exitButton);
       exitButton.setEnabled(true);
        loginButton.setVisible(true);
        loginButton.setEnabled(true);
        frame.add(admin);
        frame.pack();
        admin.setVisible(true);
        admin.addActionListener(e -> admin.setSelected(true));
        admin.setActionCommand("admin");
        frame.add(employee);
        employee.setVisible(true);
        employee.setEnabled(true);
        employee.addActionListener(e -> employee.setSelected(true));
        employee.setActionCommand("employee");
        ufield = getUfield();
        ufield.setEditable(true);
        ufield.setVisible(true);
        pfield = Pfield();
        pfield.setEditable(true);
        pfield.setVisible(true);
        assert frame != null;
        bar = new JMenuBar();
        frame.setJMenuBar(bar);
        bar.add(loginButton);
        bar.add(exitButton);
        bar.add(changeButton);
        return main_screen;
    }

    void getLogin(Database database) throws SQLException, InterruptedException, NoSuchAlgorithmException {
        String username;
        char[] password;

        if(admin.isSelected() && loginButton.isSelected()){
            username = "HR0001";
            password = database.SetPassword();
            if(Objects.equals(ufield.getText(), username)){
                if(Arrays.equals(pfield.getPassword(), password)){
                    adminScreen();
                }
            }
            System.out.println("Invalid login");
        }
        if(employee.isSelected() && loginButton.isSelected()){
            username = database.EmployeeUname();
            password = database.SetPassword();
            if(Objects.equals(ufield.getText(), username)){
                if(Arrays.equals(pfield.getPassword(), password)){
                    employeeScreen();
                }
                System.out.println("Invalid login");
            }
        }
    }
}



}
