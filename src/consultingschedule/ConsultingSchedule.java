/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.*;  
import java.util.Locale;
import java.util.ResourceBundle;

import javax.sql.DataSource;

/**
 *
 * @author matthewp
 */
public class ConsultingSchedule extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        String url = "jdbc:mysql://3.227.166.251/U06oS9";
        String userName = "U06oS9";
        String pw = "53688826761";
        
        Connection dbConnection = connectToDB(url,userName,pw);
        System.out.println(dbConnection);
        
        /*
        try{
            Connection conn = DriverManager.getConnection(url,userName,pw);
            System.out.println(conn);
        }
        catch(Exception e){
             System.out.println(e);
         }
        */
        
        /*
        try{  
            String url = "jdbc:mysql:3.227.166.251:U06oS9";
            String userName = "U06oS9";
            String pw = "53688826761";
            Connection conn = DriverManager.getConnection(url,userName,pw);
            System.out.println(conn);
         }
         catch(Exception e){
             System.out.println(e);
         }  
        */
        
        Locale locale = Locale.getDefault();
        System.out.println(locale);
        Locale  france = new Locale("fr");
        System.out.println(languageConvert(france,"Hello"));
        
        
        /*
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

          // Get connection from DataSource
          conn = getMysqlDataSource().getConnection();
          stmt = conn.createStatement();
          rs = stmt.executeQuery("SELECT version()");

          if (rs.next()) {
            System.out.println("Database Version : " + rs.getString(1));
          }
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
          try {
            if (rs != null) {
              rs.close();
            }
            if (stmt != null) {
              stmt.close();
            }
            if (conn != null) {
              conn.close();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        */
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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
    
    public static Connection connectToDB(String url, String userName, String pw)
    {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
