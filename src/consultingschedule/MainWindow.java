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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author matthewp
 */
public class MainWindow {
    MainWindow()
    {
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        labelName.setId("big-label");
        //labelName.setAlignment(Pos.CENTER);
        //labelName.setTextAlignment(TextAlignment.CENTER);
        
        btnSignOut = new Button("Sign Out");
        btnSignOut.setId("header-button");
        Button btnCustomers = new Button("Customers");
        btnCustomers.setId("header-button");
        Button btnAppointments = new Button("Appointments");
        btnAppointments.setId("header-button");
        Button btnCalendar = new Button("Calendar");
        btnCalendar.setId("header-button");
        Button btnReports = new Button("Reports");
        btnReports.setId("header-button");

        HBox header = new HBox(btnCustomers,btnAppointments,btnCalendar,btnReports, btnSignOut); 
        
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        HBox hboxImage = new HBox(imageView);
        hboxImage.setAlignment(Pos.CENTER);
        
        //copyright label set at full width
        Label copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        VBox view = new VBox(labelName,header,hboxImage,copyrightLabel);
        view.setAlignment(Pos.CENTER);
        view.setId("main-window");
        root.getChildren().add(view);
    }
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        labelName.setText("Welcome back " + user);
    }
    
    Label labelName = new Label();
    public static Button btnSignOut;
    private String user;
    StackPane root = new StackPane();
    public Scene scene = new Scene(root);
    //Stage windowPopup = new Stage();
}
