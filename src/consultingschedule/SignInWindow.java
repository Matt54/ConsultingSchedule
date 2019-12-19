/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.awt.event.ActionListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;

/**
 *
 * @author matthewp
 */

public class SignInWindow extends VBox {
    SignInWindow(){
        
        //header image
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        HBox hboxImage = new HBox(imageView);
        hboxImage.setAlignment(Pos.CENTER);
        
        //name
        labelName = new Label("User Name: ");
        TextField tfName = new TextField("");
        tfName.setPromptText("UserName");
        tfName.textProperty().addListener((obs, oldText, newText) -> {
            name = newText;
        });
        
        //password
        labelPw = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.textProperty().addListener((obs, oldText, newText) -> {
            password = newText;
        });
        
        //button
        btnSignIn = new Button();
        btnSignIn.setText("Sign In");

        //grid aligns labels,textfields, and button
        GridPane gridPane = new GridPane();
        gridPane.add(labelName, 0,0,1,1);
        gridPane.setHalignment(labelName,HPos.RIGHT);
        gridPane.add(tfName, 1,0,1,1);
        gridPane.add(labelPw, 0,1,1,1);
        gridPane.setHalignment(labelPw,HPos.RIGHT);
        gridPane.add(passwordField, 1,1,1,1);
        gridPane.add(btnSignIn,1,2,1,1);
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setAlignment(Pos.CENTER);

        //copyright label set at full width
        Label copyrightLabel = new Label("Copyright ® Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        //vbox combines into one view to display
        view = new VBox(hboxImage, gridPane, copyrightLabel);
        view.setId("sign-in-window");
    }
    
    public static String getName(){
        return name;
    }
    
    public static String getPassword(){
        return password;
    }
    
    static String name;
    static String password;
    public static Button btnSignIn;
    
    private Label labelName;
    private Label labelPw;
    public VBox view;
}

