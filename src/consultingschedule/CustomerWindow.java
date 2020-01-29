/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    CustomerWindow(MainWindow main, Consultant _consultant)
    {
        consultant = _consultant;
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

    CustomerWindow(MainWindow main, CustomerView customer, Consultant _consultant)
    {
        consultant = _consultant;
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
            if(isModify) ModifyExistingCustomer();
            else CreateNewCustomer();
            
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
    
    public void ModifyExistingCustomer()
    {
        mainWindow.show();
        stage.hide();
    }
    
    public void CreateNewCustomer()
    {
        Boolean emptyInput = false;
        
        for (Node child : gridPane.getChildren()) {
            if (child instanceof TextField){
                TextField tf = (TextField)child;
                if(tf.getText().equals(""))emptyInput = true;
            }
        }
        
        if(emptyInput){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't have an empty input.");
            alert.showAndWait();
        }
        else{
            Customer newCustomer = new Customer();
            Address newAddress = new Address();
            City newCity = new City();
            Country newCountry = new Country();
            int index = 0;
            for (Node child : gridPane.getChildren()) {
                if (child instanceof TextField){
                    TextField tf = (TextField)child;
                    switch (index){
                            case 0:
                                newCustomer.setName(tf.getText());
                                break;
                            case 1:
                                newAddress.setPhone(tf.getText());
                                break;
                            case 2:
                                newAddress.setAddress(tf.getText());
                                newAddress.setAddress2("");
                                break;
                            case 3:
                                newCity.setCityName(tf.getText());
                                break;
                            case 4:
                                newAddress.setPostalCode(tf.getText());
                                break;
                            case 5:
                                newCountry.setCountryName(tf.getText());
                                break;
                    }
                    index++;
                }
            }
            
            newCountry.addToDB();
            newCity.setCountryId(newCountry.getCountryId());
            
            newCity.addToDB();
            newAddress.setCityId(newCity.getCityId());
            
            newAddress.addToDB();
            newCustomer.setAddressId(newAddress.getAddressId());
            
            newCustomer.addToDB();
            
            consultant.addCountry(newCountry);
            consultant.addCity(newCity);
            consultant.addAddress(newAddress);
            consultant.addCustomer(newCustomer);
            consultant.AddCustomerToView(newCustomer);
            
            mainWindow.show();
            stage.hide();
        }
        
        
        /*
            if(tfID.getText().equals(""))emptyInput = true;
            if(tfName.getText().equals(""))emptyInput = true;
            if(tfCost.getText().equals(""))emptyInput = true;
            if(tfInv.getText().equals(""))emptyInput = true;
            if(tfMax.getText().equals(""))emptyInput = true;
            if(tfMin.getText().equals(""))emptyInput = true;
            if(tfMachineID.getText().equals(""))emptyInput = true;
            if(emptyInput){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You can't have an empty input.");
                alert.showAndWait();
                return false;
            }
        */
    }
    
    
    Consultant consultant;
    boolean isModify = false;
    GridPane gridPane;
    Label labelHeader;
    CustomerView customerView;
    MainWindow mainWindow;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
}
