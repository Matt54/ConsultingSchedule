/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author matthewp
 */
public class SignInWindow extends VBox {
    SignInWindow(){
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        HBox hboxImage = new HBox(imageView);
        
        Label labelName = new Label("User Name: ");
        Label labelPw = new Label("Password: ");
        
        TextField tfName = new TextField();
        tfName.setPromptText("UserName");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        Label errorLabel = new Label("");
        
        Button btnSignIn = new Button();
        btnSignIn.setText("Sign In");
        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
                errorLabel.setText("invalid credentials");
            }
        });
        
        GridPane gridPane = new GridPane();
        gridPane.add(labelName, 0,0,1,1);
        gridPane.setHalignment(labelName,HPos.RIGHT);
        gridPane.add(tfName, 1,0,1,1);
        gridPane.add(labelPw, 0,1,1,1);
        gridPane.setHalignment(labelPw,HPos.RIGHT);
        gridPane.add(passwordField, 1,1,1,1);
        gridPane.add(btnSignIn,1,2,1,1);
        //gridPane.setHalignment(btnSignIn,HPos.CENTER);
        gridPane.add(errorLabel,0,3,2,1);
        gridPane.setHalignment(errorLabel,HPos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        view = new VBox(hboxImage, gridPane);
    }
    public VBox view;
}
