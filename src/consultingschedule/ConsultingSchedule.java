package consultingschedule;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.*;  
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

import javax.sql.DataSource;


public class ConsultingSchedule extends Application {
    
    MainWindow mainWindow;
    SignInWindow signInWindow;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        
        
        
        //MainWindow mainWindow = new MainWindow();
        
        /*
        Connection dbConnection = connectToDB();
        System.out.println(dbConnection);
        */
        
        //User testUser = new User(1, "name", "password");
                /*,
                true,
                java.sql.Date.valueOf(LocalDate.now()),
                "name",
                java.sql.Timestamp.valueOf(LocalDateTime.now()));*/
        
        //Insert tested and works
        //insertUser(testUser);
        
        ///update tested and works
        //testUser.setCreator("GOD");
        //updateUser(testUser);
        
        //Get user by name and password tested and works
        //User dbUser = getUserByNameAndPassword("name", "password");
        //dbUser.setCreator("JOHN");
        //updateUser(dbUser);
        
        //Get user by ID tested and works
        //User dbUser = getUserByID(1);
        //dbUser.setCreator("MATT");
        //updateUser(dbUser);
        
        //User dbUser = getUserByNameAndPassword("name", "password");
        
        //delete tested and works
        //deleteUser(1);
        
        /*
        Country usa = new Country(1, "USA");
        usa.addToDB();
        
        City kc = new City(1,"Kansas City",1);
        kc.addToDB();
        
        Address fakeAddress1 = new  Address(1,
                                        "123 Silly Lane",
                                        "Apt. 1",
                                        1,
                                        "123456",
                                        "111-222-3333");
        fakeAddress1.addToDB();
        
        Customer matt = new Customer(1,"DELETEME",1);
        matt.addToDB();
        
        
        Appointment appt1 = new Appointment(1,
                                            matt.getCustomerId(),
                                            1,
                                            "title",
                                            "description",
                                            "location",
                                            "contact",
                                            "type",
                                            "url",
                                            LocalDateTime.now(),
                                            LocalDateTime.now());
        appt1.addToDB();
        */
        
        
        Locale locale = Locale.getDefault();
        Locale  france = new Locale("fr");
        
        //System.out.println(locale);
        //System.out.println(languageConvert(france,"Hello"));
        
        mainWindow = new MainWindow();
        
        //SignInWindow signInWindow = new SignInWindow();
        signInWindow = new SignInWindow();
        
        //signInWindow.show();
        
        SignInWindow.btnSignIn.setDefaultButton(true);
        SignInWindow.btnSignIn.setOnAction(e -> {       
            String name = SignInWindow.getName();
            String password = SignInWindow.getPassword();
            boolean isSignedIn = handleSignIn(name, password);
            if(isSignedIn){
                mainWindow.handleSignIn();
                mainWindow.show();
                signInWindow.hide();
            }
        });
        
        SetupMainWindow();

    }
    
    public void SetupMainWindow(){
        MainWindow.btnSignOut.setOnKeyPressed(event -> {
        if (event.getCode().equals(KeyCode.ENTER)) MainWindow.btnSignOut.fire();
        });
        
        MainWindow.btnSignOut.setOnAction(e -> { 
            signInWindow.show();
            mainWindow.hide();
            handleSignOut();
        });
    }
    
    public ResourceBundle GetMyResourceBundle(Locale locale){
        ResourceBundle rb;
        Locale  france = new Locale("fr");
        //Locale  english = new Locale("en_us");
        if (locale.equals(france) ) rb = ResourceBundle.getBundle("resources/fr");
        else rb = ResourceBundle.getBundle("resources/en_US");
        return rb;
    }

    public boolean handleSignIn(String name, String pw)
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        currentUser.setUser(getUserByNameAndPassword(name, pw));
        if (currentUser.getUser() == null) SignInError();
        else return true;
        return false;   //TODO: we should probably use a thrown exception for the sign in error
    }
    
    void SignInError()
    {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Invalid Input");
        errorAlert.setContentText("Username and Password do not match our records.");
        errorAlert.showAndWait();
    }
    
    public void handleSignOut()
    {
        mainWindow = new MainWindow();
        SetupMainWindow();
    }
    
    
    /*
    public interface UserDao{
        User getUser();
        Set<User> getAllUsers();
        User getUserByUserNameAndPassword();
        boolean insertUser();
        boolean updateUser();
        boolean deleteUser();
    }
    */
    
    public User getUserByNameAndPassword(String name, String pw) {
        Connection connection = connectToDB();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE userName=? AND password=?");
            statement.setString(1, name);
            statement.setString(2, pw);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next())
            {
                return extractUserData(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public User getUserByID(int id) {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userId=" + id);
            if(rs.next())
            {
                return extractUserData(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public Set getUsers(){
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            Set users = new HashSet();
            if(rs.next())
            {
                User user = extractUserData(rs);
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private User extractUserData(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId( rs.getInt("userId") );
        user.setUserName( rs.getString("userName") );
        user.setPassword( rs.getString("password") );
        return user;
    }
    
    public boolean insertUser(User user){
        Connection connection = connectToDB();
        try {
            int success = executeUserSQLStatement(user,
                    connection,
                    "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    false);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUser(User user){
        Connection connection = connectToDB();
        try {
            int success = executeUserSQLStatement(user,
                    connection,
                    "UPDATE user SET userId=?, userName=?, password=?, active=?,createDate=?,createdBy=?,lastUpdate=?, lastUpdateBy=? WHERE userId=?",
                    true);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeUserSQLStatement(User user, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, user.getUserId());
        statement.setString(2, user.getUserName());
        statement.setString(3, user.getPassword());
        statement.setBoolean(4, true); 
        statement.setDate(5, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(6, "Admin");
        statement.setTimestamp(7, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(8, "Admin");
        if(isUpdate) statement.setInt(9, user.getUserId());
        int success = statement.executeUpdate();
        return success;
    }
    
    public boolean deleteUser(int id) {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            int success = stmt.executeUpdate("DELETE FROM user WHERE userId=" + id);
          if(success == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static Connection connectToDB()
    {
        String url = "jdbc:mysql://3.227.166.251/U06oS9";
        String userName = "U06oS9";
        String pw = "53688826761";
        try{
            Connection conn = DriverManager.getConnection(url,userName,pw);
            return conn;
        }
        catch(Exception e){
             System.out.println(e);
         } 
        return null;
    }
    
    public static String languageConvert(Locale locale, String input) {
     ResourceBundle rb = ResourceBundle.getBundle("resources/"+ locale.getLanguage(), locale);
     return rb.getString(input);
   }
    
    /*
    public static DataSource getMysqlDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();

    // Set dataSource Properties
    dataSource.setServerName("3.227.166.251");
    dataSource.setPortNumber(3306);
    dataSource.setDatabaseName("U06oS9");
    dataSource.setUser("U06oS9");
    dataSource.setPassword("53688826761");
    return dataSource;
  }
    */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
