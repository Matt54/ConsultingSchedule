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
        labelHeader.setId("big-header-label");
        
        HBox headerHBox = new HBox(labelHeader);
        headerHBox.setAlignment(Pos.CENTER);
        headerHBox.setBackground(new Background(new BackgroundFill(
               Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        
        stage = new Stage();
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        
        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> { 
            if( ValidateInput() ){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You can't have an empty input.");
                alert.showAndWait();
            }
            else{
                if(isModify) ModifyExistingCustomer();
                else CreateNewCustomer();
            }
        });
        
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> { 
            mainWindow.show();
            stage.hide();
        });

        HBox btnHBox = new HBox(btnSave,btnCancel);
        
        
        Label copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        

        VBox view = new VBox(headerHBox,gridPane,btnHBox,copyrightLabel);
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
        
        if( !newCountry.getCountryName().equals(customerView.getCountry()) )
        {
            Country modifiedCountry = consultant.lookupCountry(customerView.getCountry());
            modifiedCountry.setCountryName(newCountry.getCountryName());
            modifiedCountry.addToDB();
            newCity.setCountryId(modifiedCountry.getCountryId());
        }
        
        if( !newCity.getCityName().equals(customerView.getCity()) 
                || !newCity.getCountryId().equals(consultant.lookupCity(customerView.getCity()).getCountryId()) )
        {
            City modifiedCity = consultant.lookupCity(customerView.getCity());
            modifiedCity.setCityName(newCity.getCityName());
            modifiedCity.setCountryId(newCity.getCountryId());
            modifiedCity.addToDB();
            newAddress.setCityId(modifiedCity.getCityId());
        }
        
        if( (!newAddress.getPhone().equals(customerView.getPhone()) ) 
                || (!newAddress.getAddress().equals(customerView.getAddress())) 
                || (!newAddress.getPostalCode().equals(customerView.getZip()))
                || !newAddress.getCityId().equals(consultant.lookupAddress(customerView.getAddress()).getCityId()) )
        {
            Address modifiedAddress = consultant.lookupAddress(customerView.getAddress());
            modifiedAddress.setPhone(newAddress.getPhone());
            modifiedAddress.setAddress(newAddress.getAddress());
            modifiedAddress.setPostalCode(newAddress.getPostalCode());
            modifiedAddress.setCityId(newAddress.getCityId());
            modifiedAddress.addToDB();
            newCustomer.setAddressId(modifiedAddress.getAddressId());
        }

        if( !newCustomer.getCustomerName().equals(customerView.getName()) 
                || !newCustomer.getAddressId().equals(consultant.lookupCustomer(customerView.getName()).getAddressId()) )
        {
            Customer modifiedCustomer = consultant.lookupCustomer(customerView.getName());
            modifiedCustomer.setName(newCustomer.getCustomerName());
            modifiedCustomer.setAddressId(newCustomer.getAddressId());
            modifiedCustomer.updateDB();
            
            consultant.deleteCustomerView(customerView);
            consultant.AddCustomerToView(modifiedCustomer);
        }

        mainWindow.show();
        stage.hide();
    }
    
    public void CreateNewCustomer()
    {
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
    
    private boolean ValidateInput(){
        for (Node child : gridPane.getChildren()) {
            if (child instanceof TextField){
                TextField tf = (TextField)child;
                if(tf.getText().equals("")) return true;
            }
        }
        return false;
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
