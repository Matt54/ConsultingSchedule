/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author macbook
 */
public class CustomerWindow {
    
    CustomerWindow(MainWindow main)
    {
        labelHeader = new Label("Add Customer");
        gridPane = new GridPane();
        CreatePromptField(gridPane, "Name: ", "",0 );
        CreatePromptField(gridPane, "Phone: ", "",1 );
        CreatePromptField(gridPane, "Address: ", "",2 );
        CreatePromptField(gridPane, "City: ", "",3 );
        CreatePromptField(gridPane, "ZIP Code: ", "",4 );
        CreatePromptField(gridPane, "Country: ", "",5 );
        SetupWindow(main);
    }

    CustomerWindow(MainWindow main, CustomerView customer)
    {
        isModify = true;
        customerView = customer;
        labelHeader = new Label("Modify Customer");
        gridPane = new GridPane();
        CreatePromptField(gridPane, "Name: ", customerView.getName(),0 );
        CreatePromptField(gridPane, "Phone: ", customerView.getPhone(),1 );
        CreatePromptField(gridPane, "Address: ", customerView.getAddress(),2 );
        CreatePromptField(gridPane, "City: ", customerView.getCity(),3 );
        CreatePromptField(gridPane, "ZIP Code: ", customerView.getZip(),4 );
        CreatePromptField(gridPane, "Country: ", customerView.getCountry(),5 );
        SetupWindow(main);
    }
    
    public void SetupWindow(MainWindow main)
    {
        mainWindow = main;
        mainWindow.hide();
        labelHeader.setId("big-label");
        stage = new Stage();
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> { 
            //TODO: Add or Modify Customer
            if(isModify)
            {
            
            }
            else
            {
                
            }
            mainWindow.show();
            stage.hide();
        });
        
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> { 
            mainWindow.show();
            stage.hide();
        });

        HBox btnHBox = new HBox(btnSave,btnCancel);

        VBox view = new VBox(labelHeader,gridPane,btnHBox);
        view.setAlignment(Pos.CENTER);
        view.setBackground(new Background(new BackgroundFill(
               Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        
        root.getChildren().add(view);
        
        stage.setScene(scene);
        
        stage.show();
    }

    public void CreatePromptField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        TextField textField = new TextField(name);
        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(textField, 1,index,1,1);
    }
    
    boolean isModify = false;
    GridPane gridPane;
    Label labelHeader;
    CustomerView customerView;
    MainWindow mainWindow;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
}
