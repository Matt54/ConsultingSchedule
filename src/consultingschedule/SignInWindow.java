package consultingschedule;

import static consultingschedule.ConsultingSchedule.languageConvert;
import java.util.Locale;
import java.util.ResourceBundle;
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

public class SignInWindow extends VBox {
    SignInWindow(){

        root = new StackPane();
        scene = new Scene(root);
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        
        //header image
        Image image = new Image("SignInGraphic.png");
        ImageView imageView = new ImageView(image);
        
        Image gif = new Image("globe_small.gif");
        ImageView gifView = new ImageView(gif);
        gifView.setX(35);
        gifView.setY(80);
        double width = 75;
        gifView.setFitWidth(width);
        gifView.setFitHeight(width);
        Pane stackPane = new Pane(imageView,gifView);
        
        //Locale  locale = new Locale("fr");
        //Locale  locale = new Locale("en_US");
        Locale locale = Locale.getDefault();
        ResourceBundle rb = GetMyResourceBundle(locale);
        
        //name
        labelName = new Label(rb.getString("UserPrompt"));
        TextField tfName = new TextField("");
        tfName.setPromptText(rb.getString("UserPrompt"));
        tfName.textProperty().addListener((obs, oldText, newText) -> {
            name = newText;
        });
        
        //password
        labelPw = new Label(rb.getString("PwPrompt"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(rb.getString("PwPrompt"));
        passwordField.textProperty().addListener((obs, oldText, newText) -> {
            password = newText;
        });
        
        //button
        btnSignIn = new Button();
        btnSignIn.setText(rb.getString("SignIn"));
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
        gridPane.add(btnSignIn,0,2,2,1);
        gridPane.setHalignment(btnSignIn,HPos.CENTER);
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setAlignment(Pos.CENTER);

        //copyright label set at full width
        Label copyrightLabel = new Label(rb.getString("Copyright"));
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
        
        imageView.setFitWidth(view.getWidth());
        imageView.setPreserveRatio(true);
        stage.sizeToScene();
        stage.setResizable(false);
    }
    
    public ResourceBundle GetMyResourceBundle(Locale locale){
        ResourceBundle rb;
        Locale  france = new Locale("fr");
        //Locale  english = new Locale("en_us");
        if (locale.equals(france) ) rb = ResourceBundle.getBundle("resources/fr");
        else rb = ResourceBundle.getBundle("resources/en_US");
        return rb;
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

