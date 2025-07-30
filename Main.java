import org.sqlite.JDBC;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;


class Database {
    String url = "jdbc:sqlite:/Users/emilymannlein/employees.db";
    Connection makeConnection() {
        try(Connection connection = DriverManager.getConnection(url)){
            System.out.println("CONNECTED TO DATABASE");
            return connection;
        }
        catch(SQLException e){
            System.err.println("CONNECTION FAILED");
        }
        return makeConnection();
    }


    JDBC jdbc = new JDBC();

    Database() throws SQLException {
        try{
            jdbc.connect(url,new Properties());
        }
        catch (SQLException e){
            throw new SQLException();
        }
    }

    String EmployeeUname() throws SQLException {
        Scanner scanner = new Scanner(System.in);
       String username;
       String first_name = scanner.nextLine();
        Connection connection = DriverManager.getConnection(url);
        String query = "SELECT email FROM employees WHERE first_name = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, first_name);
        ResultSet email = statement.executeQuery();
        username = email.getString(1);
        statement.close();
        return username;
    }

    char[] SetPassword() throws SQLException, InterruptedException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        String pquery = "SELECT password FROM employees WHERE email=?";
        String bquery = "SELECT birthday FROM employees WHERE email =?;";
        // *password default needs stored as hash
        //check if password is default

        ResultSet password = statement.executeQuery(pquery);
        ResultSet birthday = statement.executeQuery(bquery);
        char[] pword = main.Pfield().getPassword();
        if(password==birthday){
            System.out.println("Change password");
            byte[] pd = digest.digest(Arrays.toString(pword).getBytes());
            String pchange = "UPDATE employees SET password = ? WHERE email = ?;";
           PreparedStatement prep = connection.prepareStatement(pchange);
           //change password to new value
         prep.setString(1, Arrays.toString(pd));
            prep.setString(2, EmployeeUname());
            if(main.doEnter()){
                prep.executeUpdate();
                connection.commit();
            }
            statement.close();
            prep.close();
        }
       scanner.close();
       connection.close();
       return pword;
    }


}


}
