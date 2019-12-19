/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import static consultingschedule.SignInWindow.btnSignIn;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author matthewp
 */
public class MainWindow {
    MainWindow()
    {
        btnSignOut = new Button("Sign Out");
        Button btnCustomers = new Button("Customers");
        Button btnAppointments = new Button("Appointments");
        Button btnCalendar = new Button("Calendar");
        Button btnReports = new Button("Reports");

        HBox header = new HBox(btnCustomers,btnAppointments,btnCalendar,btnReports, btnSignOut, labelName); 
        
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        HBox hboxImage = new HBox(imageView);
        hboxImage.setAlignment(Pos.CENTER);
        
        VBox view = new VBox(header,hboxImage);
        
        root.getChildren().add(view);
    }
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        labelName.setText("User: " + user);
    }
    
    Label labelName = new Label();
    public static Button btnSignOut;
    private String user;
    StackPane root = new StackPane();
    public Scene scene = new Scene(root);
    //Stage windowPopup = new Stage();
}
