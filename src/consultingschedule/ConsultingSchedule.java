/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.*;  
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.sql.DataSource;

/**
 *
 * @author matthewp
 */
public class ConsultingSchedule extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        
        StackPane root = new StackPane();
        Scene sceneMain = new Scene(root);//, 875, 510);
        sceneMain.getStylesheets().add("consultingschedule/StyleSheet.css");
        
        Connection dbConnection = connectToDB();
        System.out.println(dbConnection);
        
        User testUser = new User(1,
                "name",
                "password",
                true,
                java.sql.Date.valueOf(LocalDate.now()),
                "name",
                java.sql.Timestamp.valueOf(LocalDateTime.now()));
        
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
        
        Locale locale = Locale.getDefault();
        System.out.println(locale);
        Locale  france = new Locale("fr");
        System.out.println(languageConvert(france,"Hello"));
        
        /*
        //Add An Image 
        Image image = new Image("Placeholder.png");
        ImageView imageView = new ImageView(image);
        HBox hboxImage = new HBox(imageView);
        
        Label labelName = new Label("User Name: ");
        Label labelPw = new Label("Password: ");
        
        TextField tfName = new TextField();
        TextField tfPw = new TextField();
        
        HBox hboxName = new HBox(labelName,tfName);
        hboxName.setAlignment(Pos.CENTER_RIGHT);
        HBox hboxPw = new HBox(labelPw,tfPw);
        hboxPw.setAlignment(Pos.CENTER_RIGHT);
        VBox vboxInput = new VBox(hboxName,hboxPw);
        
        GridPane gridPane = new GridPane();
        gridPane.add(labelName, 0,0,1,1);
        gridPane.add(tfName, 1,0,1,1);
        gridPane.add(labelPw, 0,1,1,1);
        gridPane.add(tfPw, 1,1,1,1);
        gridPane.setAlignment(Pos.CENTER);
        
        Button btnSignIn = new Button();
        btnSignIn.setText("Sign In");
        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //DO STUFF ON BUTTON PRESS
            }
        });
        HBox hboxSignIn = new HBox(btnSignIn);
        hboxSignIn.setAlignment(Pos.CENTER);
        
        VBox mainScreen = new VBox(hboxImage, gridPane, hboxSignIn);
        */
        
        SignInWindow signInWindow = new SignInWindow();
        
        root.getChildren().add(signInWindow.view);
        
        //Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Matt Pfeiffer Consulting");
        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }
    
    public interface Dao{
        User getUser();
        Set<User> getAllUsers();
        User getUserByUserNameAndPassword();
        boolean insertUser();
        boolean updateUser();
        boolean deleteUser();
    }
    
    public User getUserByNameAndPassword(String name, String pw) {
        Connection connection = connectToDB();
        try {
            //Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userName=" + "userName" + " AND password=" + pw);
            
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
        user.setStatus(rs.getBoolean("active"));
        user.setDateAdded(rs.getDate("createDate"));
        user.setCreator(rs.getString("createdBy"));
        user.setUpdateTime(rs.getTimestamp("lastUpdate"));
        user.setUpdatedBy(rs.getString("lastUpdateBy"));
        return user;
    }
    
    public boolean insertUser(User user){
        Connection connection = connectToDB();
        try {
            /*
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (NULL, ?, ?, ?)");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.getStatus());
            statement.setDate(5, java.sql.Date.valueOf(user.getDateAdded()));
            statement.setString(6, user.getCreator());
            statement.setTimestamp(7, java.sql.Timestamp.valueOf(user.getLastUpdate()));
            statement.setString(8, user.getLastUpdateBy());
            */
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
        statement.setBoolean(4, user.getStatus());
        statement.setDate(5, java.sql.Date.valueOf(user.getDateAdded()));
        statement.setString(6, user.getCreator());
        statement.setTimestamp(7, java.sql.Timestamp.valueOf(user.getLastUpdate()));
        statement.setString(8, user.getLastUpdateBy());
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
