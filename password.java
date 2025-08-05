import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

class Password{
char[] pword;

     static Boolean need_changed() throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(Database.url);
        connection.setAutoCommit(false);
        String username = Database.EmployeeUname();
        String pquery = "SELECT password FROM employees WHERE username=?";
        String bquery = "SELECT birthday FROM employees WHERE username =?;";
        // *password default needs stored as hash
        //check if password is default
        PreparedStatement pstatement = connection.prepareStatement(pquery);
        PreparedStatement bstatement = connection.prepareStatement(bquery);
        pstatement.setString(1,username);
        bstatement.setString(1,username);
        ResultSet password = pstatement.executeQuery();
        ResultSet birthday = bstatement.executeQuery();
        if (Objects.equals(password.getString(1), birthday.getString(1))) {
            return true;
        }
        password.close();
        birthday.close();
        pstatement.close();
        bstatement.close();
        return false;
    }
    char[] SetPassword() throws SQLException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        Main main = new Main();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        Connection connection = DriverManager.getConnection(Database.url);
        connection.setAutoCommit(false);
        Boolean need_changed = need_changed();
         pword = main.Pfield().getPassword();
        KeySpec spec;
       SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        if (need_changed) {
            main.setChangeButton();
            pword = main.Pfield().getPassword();
             spec = new PBEKeySpec(pword,salt,65536,128);
            String pchange = "UPDATE employees SET password = ? WHERE username = ?;";
            byte[] pd = factory.generateSecret(spec).getEncoded();
            PreparedStatement prep = connection.prepareStatement(pchange);
            //change password to new value
            prep.setString(1, Arrays.toString(pd));
            prep.setString(2, Database.EmployeeUname());
                prep.executeUpdate();
                if(main.changeMatch()){
                    System.out.println("match");
                }
            System.out.println("updated");
                connection.commit();
                System.out.println("committed");
            prep.close();
            connection.close();
        }
        return pword;
}
}