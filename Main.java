import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class Main {


    public Main() throws SQLException, InterruptedException {

    }

    public static void main(String[] args) throws SQLException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {
        Main main = new Main();
        Database database = new Database();
        database.makeConnection();
        main.getFrame();
       main.getLogin(database);


    }

    private JButton loginButton;
    private JButton exitButton;
    // private JButton changeButton;
    private JButton enter;
    private final JRadioButton admin = new JRadioButton("Admin");
    private final JRadioButton employee = new JRadioButton("Employee");
    private final ButtonGroup group = new ButtonGroup();
    private JFrame frame = new JFrame("Login");
    private final JFrame frame2 = new JFrame("Change Password");
    private JFrame aFrame = new JFrame("Admin");
    private final JFrame eFrame = new JFrame("Employee");
    private final JPanel jPanel = new JPanel(new BorderLayout());
    private final JPanel rPanel = new JPanel();
    private JMenu main_screen = getScreen();

    MenuListener menuListener = new MenuListener() {
        @Override
        public void menuSelected(MenuEvent e) {
            if (e.getSource() == employeeScreen()) {
                employeeScreen().setSelected(true);
            }
        }

        @Override
        public void menuDeselected(MenuEvent e) {
            if (e.getSource().equals(employeeScreen())) {
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
        frame.add(rPanel, BorderLayout.WEST);
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

    public JPasswordField Pfield() {
        pfield = new JPasswordField(5);
        frame.add(pfield);
        return pfield;
    }

    public JMenu adminScreen() {
        JMenu screen2 = new JMenu("Menu");
        main_screen.setVisible(false);
        aFrame.setVisible(true);
        aFrame.add(jPanel);
        rPanel.setVisible(false);
        frame.setVisible(false);
        JMenuBar bar1 = new JMenuBar();
        aFrame.add(bar1, BorderLayout.NORTH);
        JMenuItem employees = getEmployeesItem();
        if(employees.isSelected()){
            aFrame = new JFrame("Search employee database");
        }
        JMenuItem salary = getSalaryItem();
        if(salary.isSelected()){
            aFrame = new JFrame("Find employee salary");
        }
        screen2.add(employees);
        screen2.add(salary);
        bar1.add(screen2);
        bar1.setVisible(true);
        bar.setVisible(false);
        return screen2;
    }

    private static JMenuItem getEmployeesItem() {
        JMenuItem employees = new JMenuItem("Employees");
        employees.addActionListener(e -> {
            employees.setSelected(true);
            Database database;
            try {
                database = new Database();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                database.employeeInfo();
            } catch (SQLException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        return employees;
    }

    private static JMenuItem getSalaryItem() {
        JMenuItem salary = new JMenuItem("Salary");
        salary.addActionListener(e -> {
            salary.setSelected(true);
            Database database;
            try {
                database = new Database();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                database.salaryInfo();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return salary;
    }

    public JMenu employeeScreen() {
        JMenu screen2 = new JMenu("Menu");
        eFrame.add(screen2);
        eFrame.setVisible(true);
        screen2.addMenuListener(menuListener);
        main_screen.setVisible(false);
        frame.setVisible(false);
        JMenuBar bar1 = new JMenuBar();
        JMenuItem timesheet = new JMenuItem("Timesheet");
        JMenuItem pto = new JMenuItem("PTO");
        screen2.add(timesheet);
        timesheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timesheet.setSelected(true);
                try {
                    Database.EmployeeTime();
                } catch (SQLException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        screen2.add(pto);
        bar1.add(screen2);
        bar1.setVisible(true);
        bar.setVisible(false);
        return screen2;
    }

    void setChangeButton() {
        JMenuBar bar2 = new JMenuBar();
        cfield = new JPasswordField(BorderLayout.WEST);
        bar2.add(pfield);
        bar2.add(cfield);
        bar2.add(enter);
        enter.setVisible(true);
        cfield.setVisible(true);
        rPanel.setVisible(false);
        jPanel.add(bar2);
        frame2.add(bar2);
        frame.setVisible(false);
        frame2.setVisible(true);
        bar2.setVisible(true);
        bar.setVisible(false);
        loginButton.setVisible(false);
        exitButton.setVisible(true);
        ufield.setVisible(false);
        enter.setEnabled(true);
        enter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enter.doClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                enter.setSelected(true);
                if (changeMatch()) {
                    //Database database = new Database();
                    // database.SetPassword();
                    frame = getFrame();
                    getScreen();
                    frame2.setVisible(false);
                    bar2.setVisible(false);
                    cfield.setVisible(false);

                }
                else System.out.println("Passwords do not match");

            }

            public void mouseEntered(MouseEvent e) {
                enter.doClick();
            }

            public void mouseExited(MouseEvent e) {
            }
        });

    }

Boolean changeMatch(){
    return Arrays.equals(cfield.getPassword(), pfield.getPassword());
}

    public JMenu getScreen() {
        frame = getFrame();
        assert main_screen != null;
        main_screen = new JMenu();
        assert loginButton != null;
        loginButton = new JButton("login");
        loginButton.addActionListener(e -> {
            loginButton.setSelected(true);
            try {
                Database database = new Database();
                if(!Password.need_changed()){
                    getLogin(database);
                }
                else setChangeButton();
            } catch (SQLException | InterruptedException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                throw new RuntimeException(ex);
            }
        });
                loginButton.setEnabled(true);
        assert exitButton != null;
        exitButton = new JButton("exit");
        exitButton.setVisible(true);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setActionCommand("exit");
     /*   assert changeButton != null;
        changeButton = new JButton("Change password");
        changeButton.addActionListener(e -> {
            changeButton.setSelected(true);
            setChangeButton();
        });

      */
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
        //bar.add(changeButton);
        return main_screen;
    }

    void getLogin(Database database) throws SQLException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {
        String username;
        char[] password;
        Password pw = new Password();
        JMenu screen;
        if(admin.isSelected() && loginButton.isSelected()){
            username = "HR0001";
            password = pw.SetPassword();
            if(Objects.equals(ufield.getText(), username) && Arrays.equals(pfield.getPassword(), password)){
                   screen = adminScreen();
                   screen.setVisible(true);
                }
            System.out.println("Invalid login");
        }
        if(employee.isSelected() && loginButton.isSelected()){
            username = Database.EmployeeUname();
            password = pw.SetPassword();
            if(Objects.equals(ufield.getText(), username) && Arrays.equals(pfield.getPassword(), password)){
                    screen = employeeScreen();
                    screen.setVisible(true);
                }
                System.out.println("Invalid login");
            }
        }
    }
