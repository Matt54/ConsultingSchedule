/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author matthewp
 */
public class MainWindow {
    
    MainWindow()
    {
        stage = new Stage();
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        labelName.setId("big-label");
        
        HBox categoryHeader = CreateCategoryHeader();
        interactionHeader = CreateTableInteractionHeader();
        
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        hboxImage = new HBox(imageView);
        hboxImage.setAlignment(Pos.CENTER);
        
        //copyright label set at full width
        copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        view = new VBox(labelName,categoryHeader,hboxImage,copyrightLabel);
        view.setAlignment(Pos.CENTER);
        view.setId("main-window");
        root.getChildren().add(view);
        
        stage.setScene(scene);
        
        consultant = new Consultant();
        //createDefaultCustomers();
        Customer customer = new Customer(1, //ID
                "name", //name
                1 //addressID
                );
        
                /*
                true, //isActive
                java.sql.Date.valueOf(LocalDate.now()), //Date
                "name", //Creater
                java.sql.Timestamp.valueOf(LocalDateTime.now())); //timestamp
                */
        
        consultant.addCustomer(customer);
        
        tvCustomers = new TableView<>(consultant.getAllCustomers());
        
        TableColumn<Customer, String> customerId = new TableColumn<>("Customer ID");
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tvCustomers.getColumns().add(customerId);

        TableColumn<Customer, String> customerName = new TableColumn<>("Customer Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tvCustomers.getColumns().add(customerName);
        
        tvCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tvCustomers.setPrefWidth(355);
        tvCustomers.setPrefHeight(300);
        
        //TableView.TableViewSelectionModel<Customer> tvSelCustomer = tvCustomers.getSelectionModel();
    } 
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        labelName.setText("Welcome back " + user);
    }

    public HBox CreateCategoryHeader()
    {
        btnSignOut = new Button("Sign Out");
        btnSignOut.setId("header-button");
        
        Button btnCustomers = new Button("Customers");
        btnCustomers.setId("header-button");
        
        btnCustomers.setOnAction(e -> { 
            selectCategoryCustomer();
        });
        
        Button btnAppointments = new Button("Appointments");
        btnAppointments.setId("header-button");
        btnAppointments.setOnAction(e -> { 
            
        });
        
        Button btnCalendar = new Button("Calendar");
        btnCalendar.setId("header-button");
        btnCalendar.setOnAction(e -> { 
            
        });
        
        Button btnReports = new Button("Reports");
        btnReports.setId("header-button");
        btnReports.setOnAction(e -> { 
            
        });
        
        return new HBox(btnCustomers,btnAppointments,btnCalendar,btnReports, btnSignOut); 
    }
    
    public HBox CreateTableInteractionHeader()
    {
        Button btnAdd = new Button("Add");
        Button btnDelete = new Button("Delete");
        Button btnModify = new Button("Modify");
        Button btnSearch = new Button("Search");
        TextField tfSearch = new TextField();
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String searchTerm = tfSearch.getText();
                    Customer foundCustomer = consultant.lookupCustomer(searchTerm);
                    if(foundCustomer != null)
                    {
                        tvCustomers.getSelectionModel().select(foundCustomer);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Name not found");
                        alert.showAndWait();
                    }
                }
            });
        
        
        HBox hbox = new HBox(btnAdd, btnDelete, btnModify, btnSearch, tfSearch);
        
        hbox.setBackground(new Background(new BackgroundFill(
               Color.gray(0.865), CornerRadii.EMPTY, Insets.EMPTY)));
        
        return hbox;
    }
    
    public void selectCategoryCustomer()
    {
        if(selectedCategory != Selected.CUSTOMERS)
        {
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(copyrightLabel);
            view.getChildren().add(tvCustomers);
            view.getChildren().add(interactionHeader);
            view.getChildren().add(copyrightLabel);
            stage.sizeToScene();
        }
        selectedCategory = Selected.CUSTOMERS;
    }

    /*
    public void StageSwap(Stage otherStage)
    {
        show();
        otherStage.hide();
    }
    */
    
    public void show(){
        stage.show();
        stage.sizeToScene();
    }
    
    public void hide(){
        stage.hide();
    }
    
    /*
    public void createDefaultCustomers()
    {
        customer = new Customer(1, //ID
                "name", //name
                1, //addressID
                true, //isActive
                java.sql.Date.valueOf(LocalDate.now()), //Date
                "name", //Creater
                java.sql.Timestamp.valueOf(LocalDateTime.now())); //timestamp
    }
    */
    
    enum Selected{
        NONE,
        CUSTOMERS,
        APPOINTMENTS,
        CALENDAR,
        REPORTS
    }
    Selected selectedCategory = Selected.NONE;
    
    Consultant consultant;
    TableView tvCustomers;
    VBox view;
    HBox hboxImage;
    HBox interactionHeader;
    Label labelName = new Label();
    Label copyrightLabel;
    public static Button btnSignOut;
    private String user;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
}
