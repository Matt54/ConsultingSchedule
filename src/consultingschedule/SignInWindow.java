/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author matthewp
 */

public class SignInWindow extends VBox {
    SignInWindow(){

        root = new StackPane();
        scene = new Scene(root);
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        
        //header image
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        Image gif = new Image("globe_small.gif");
        ImageView gifView = new ImageView(gif);
        gifView.setX(55);
        gifView.setY(80);
        double width = 75;
        gifView.setFitWidth(width);
        gifView.setFitHeight(width);
        Pane stackPane = new Pane(imageView,gifView);
        
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
        SignInWindow.btnSignIn.setOnAction(e -> {   
            stage.hide();
        });

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
        Label copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        //vbox combines into one view to display
        view = new VBox(stackPane, gridPane, copyrightLabel);
        view.setId("sign-in-window");
        
        root.getChildren().add(view);
        
        stage = new Stage();
        stage.setTitle("Sign In");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        show();
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public void show(){
        stage.show();
        stage.sizeToScene();
    }
    
    public void hide(){
        stage.hide();
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
    VBox view;
    StackPane root;
    Stage stage;
    Scene scene;
}

