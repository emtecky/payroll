import org.sqlite.JDBC;
import javax.swing.*;
import java.sql.*;
import java.util.Properties;


class Database {
    static String url = "jdbc:sqlite:/Users/emilymannlein/employees.db";
    Connection makeConnection() {
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("CONNECTED TO DATABASE");
            return connection;
        } catch (SQLException e) {
            System.err.println("CONNECTION FAILED");
        }
        return null;
    }

    JDBC jdbc = new JDBC();

    Database() throws SQLException {
        try {
            jdbc.connect(url, new Properties());
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

     static String EmployeeUname() throws SQLException, InterruptedException {
        Main main = new Main();
        String username = main.getUfield().getText();
        Connection connection = DriverManager.getConnection(url);
        String query = "SELECT username FROM employees WHERE email = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet email = statement.executeQuery();
        username = email.getString(1);
        statement.close();
        return username;
    }


    void salaryInfo() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        JTextField fname_search = new JTextField();
        JTextField lname_search = new JTextField();
        String first_name = fname_search.getText();
        String last_name = lname_search.getText();
        String query = "SELECT * FROM salaries WHERE first_name = ? AND last_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
       statement.setString(1, first_name);
        statement.setString(2, last_name);
        ResultSet resultSet = statement.executeQuery();
        double salary = Integer.parseInt(resultSet.getString("base_salary"));
        JLabel display = new JLabel(statement.executeQuery().getString(1));
        display.setVisible(true);
        getPayroll(first_name,last_name,salary);
    }
    void employeeInfo() throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(url);
        ImageIcon imageIcon = new ImageIcon();
        JLabel img = new JLabel(imageIcon);
        JTextField fname_search = new JTextField();
        JLabel flabel = new JLabel("First name");
        flabel.setLabelFor(fname_search);
        fname_search.setVisible(true);
        JTextField lname_search = new JTextField();
        JLabel llabel = new JLabel("Last name");
        llabel.setLabelFor(lname_search);
        lname_search.setVisible(true);
        String first_name = fname_search.getText();
        String last_name = lname_search.getText();
        String idquery = "SELECT id FROM employees WHERE first_name = ? AND last_name = ?;";
        String dquery = "SELECT department FROM employees WHERE id = ?;";
        String jquery = "SELECT job_title FROM employees WHERE id = ?;";
        String fname = "SELECT first_name FROM employees WHERE id = ?";
        String lname = "SELECT last_name FROM employees WHERE id = ?";
        String gquery = "SELECT gender FROM employees WHERE id = ?";
        String pquery= "SELECT pay_type FROM employees WHERE id = ?";
        String equery = "SELECT email FROM employees WHERE id = ?;";
        String aquery = "SELECT address FROM employees WHERE id = ?;";
        String cquery = "SELECT city FROM employees WHERE address = ?;";
        String zquery = "SELECT zip FROM employees WHERE city = ?;";
        PreparedStatement getid = connection.prepareStatement(idquery);
        getid.setString(1, first_name);
        getid.setString(2, last_name);
        ResultSet id = getid.executeQuery();
        PreparedStatement getdep = connection.prepareStatement(dquery);
        getdep.setString(1, String.valueOf(id));
        JLabel display = new JLabel(getdep.executeQuery().getString(1));
        display.setVisible(true);
        PreparedStatement getjob = connection.prepareStatement(jquery);
        getjob.setString(1, String.valueOf(id));
        JLabel job = new JLabel(getjob.executeQuery().getString(1));
        job.setVisible(true);
        PreparedStatement sfname = connection.prepareStatement(fname);
        sfname.setString(1, String.valueOf(id));
        JLabel name1 = new JLabel(sfname.executeQuery().getString(1));
        name1.setVisible(true);
        PreparedStatement slname = connection.prepareStatement(lname);
        slname.setString(1, String.valueOf(id));
        JLabel name2 = new JLabel(slname.executeQuery().getString(1));
        name2.setVisible(true);
        PreparedStatement gender = connection.prepareStatement(gquery);
       gender.setString(1, String.valueOf(id));
        JLabel gen = new JLabel(gender.executeQuery().getString(1));
        gen.setVisible(true);
        PreparedStatement paytype = connection.prepareStatement(pquery);
        paytype.setString(1,String.valueOf(id));
        JLabel pt = new JLabel(paytype.executeQuery().getString(1));
        pt.setVisible(true);
        PreparedStatement email = connection.prepareStatement(equery);
        email.setString(1,String.valueOf(id));
        JLabel em = new JLabel(email.executeQuery().getString(1));
       em.setVisible(true);
        PreparedStatement addr = connection.prepareStatement(aquery);
        addr.setString(1,em.getText());
        JLabel ad = new JLabel(addr.executeQuery().getString(1));
       ad.setVisible(true);
        PreparedStatement city = connection.prepareStatement(cquery);
       city.setString(1,ad.getText());
        JLabel cl = new JLabel(city.executeQuery().getString(1));
        cl.setVisible(true);
        PreparedStatement zip = connection.prepareStatement(zquery);
        zip.setString(1,cl.getText());
        JLabel zp = new JLabel(zip.executeQuery().getString(1));
       zp.setVisible(true);
       if(imageIcon.getImage()!=null){
         img.setVisible(true);
       }
       else img = new JLabel("Image not available");
       img.setVisible(true);
    }
    void getPayroll(String first_name, String last_name, double salary) throws SQLException {
    double net = net(first_name,last_name,salary);
    new JLabel("Net pay:" + net).setVisible(true);
        new JLabel("Gross pay:" + salary).setVisible(true);
    }

    static void hoursWorked(double salary){
        int week = 0;
        double overtime;
        JTable weektable = new JTable();
        JTextField hourbox = new JTextField();
        hourbox.setVisible(true);
        JTextField daybox = new JTextField();
        daybox.setVisible(true);
        JLabel hrs = new JLabel("Enter hours worked");
        JLabel hour = new JLabel("Daily hours:");
        JLabel dl = new JLabel("Day:");
        hrs.setVisible(true);
        String day = daybox.getText();
        int hours = Integer.parseInt(hourbox.getText());
        weektable.add(day,dl);
        weektable.add(String.valueOf(hours),hour);
        for(int i = 0; i < weektable.getRowCount(); i++){
            for(int j = 0; j < weektable.getRowCount(); j++){
                week += hours;
            }
        }
        double rate = salary/week*52;
        if(hours < 8){
           double overrate = rate * 1.5;
           int overhrs  = week - 40;
          overtime = overhrs * overrate;
        }
        else overtime = 0;
        double regular = rate * week;
        double total = overtime + salary;
        new JLabel("Overtime:"+overtime).setVisible(true);
        new JLabel("Regular pay:"+regular).setVisible(true);
        new JLabel("Total:"+total).setVisible(true);
    }

    static void EmployeeTime() throws SQLException, InterruptedException {
        JButton submit = new JButton("Submit");
        Connection connection = DriverManager.getConnection(url);
        JTextField fname_search = new JTextField();
        fname_search.setVisible(true);
        JTextField lname_search = new JTextField();
        lname_search.setVisible(true);
        String first_name = fname_search.getText();
        String last_name = lname_search.getText();
        String pquery = "SELECT salary_type FROM salaries WHERE first_name = ? AND last_name = ?";
        PreparedStatement pt = connection.prepareStatement(pquery);
        pt.setString(1,first_name);
        pt.setString(2,last_name);
        String paytype = pt.executeQuery().getString(1);
        String squery = "SELECT base_salary FROM salaries WHERE first_name = ? AND last_name = ?";
        PreparedStatement bs = connection.prepareStatement(squery);
        bs.setString(1,first_name);
        bs.setString(2,last_name);
        ResultSet base = bs.executeQuery();
        double salary = Integer.parseInt(base.getString(1));
        if(paytype.equals("hourly")){
            hoursWorked(salary);
        }
        deductions(first_name,last_name,salary);
        submit.addActionListener(e -> submit.setSelected(true));

    }
    double net(String first_name, String last_name, double salary) throws SQLException {
        double deductions = deductions(first_name, last_name, salary);
        return salary-deductions;
    }

   static double deductions(String first_name, String last_name, double salary) throws SQLException {
        int medical = 0;
        String mquery = "SELECT medical FROM salaries WHERE first_name = ? AND last_name = ?";
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement md = connection.prepareStatement(mquery);
        md.setString(1,first_name);
        md.setString(2,last_name);
       String med = md.executeQuery().getString(1);
       if (med.equals("single")) {
           medical = 50;
       }
       else if(med.equals("family")){
           medical = 100;
       }
       int stipend = getStipend(first_name, last_name);
       salary += stipend + medical;
        double state = StateTax(first_name,last_name,salary);
        double fed = salary * (7.65/100);
        double soc = salary * (6.2/100);
       new JLabel("Medical:"+medical).setVisible(true);
        new JLabel("Stipend:"+stipend).setVisible(true);
        new JLabel("State tax:"+state).setVisible(true);
        new JLabel("Employee Federal tax (7.62%):"+fed).setVisible(true);
        new JLabel("Employee social security (6.2%): "+soc).setVisible(true);
       double deductions = (soc+fed+state);
       new JLabel("Total deductions:"+deductions).setVisible(true);
       return deductions;
    }
static int getStipend(String first_name, String last_name) throws SQLException {
        int stipend = 0;
    Connection connection = DriverManager.getConnection(url);
    String deps = "SELECT dependents FROM salaries WHERE first_name = ? AND last_name = ?";
    PreparedStatement getdeps = connection.prepareStatement(deps);
    getdeps.setString(1,first_name);
    getdeps.setString(2,last_name);
    ResultSet dependents = getdeps.executeQuery();
   int numdeps = Integer.parseInt(dependents.getString(1));
   for(int i = 0; i < numdeps; i++){
        stipend = 45 * i;
   }
   return stipend;
}
static double StateTax(String first_name, String last_name, double salary) throws SQLException {
    Connection connection = DriverManager.getConnection(url);
    double percent;
    double state = 0;
    String aquery = "SELECT address FROM employees WHERE first_name = ? AND last_name = ?;";
    String cquery = "SELECT city FROM employees WHERE address = ?;";
    PreparedStatement addr = connection.prepareStatement(aquery);
    addr.setString(1,first_name);
    addr.setString(2,last_name);
    PreparedStatement city = connection.prepareStatement(cquery);
    String ad = addr.executeQuery().getString(1);
    city.setString(1,ad);
    String cs = city.executeQuery().getString(1);
    if(cs.contains("IN")){
       percent = 3.15 / 100;
       state = salary * percent;
    }
    else if(cs.contains("IL")){
        percent = 4.95/100;
       state = salary * percent;
    }
    return state;
}
}
