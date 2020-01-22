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
import javafx.scene.layout.Pane;
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
        hboxGreeting = new HBox(labelName);
        hboxGreeting.setAlignment(Pos.CENTER);
        //hboxGreeting.setId("greeting-hbox");
        hboxGreeting.setBackground(new Background(new BackgroundFill(
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        
        HBox categoryHeader = CreateCategoryHeader();
        interactionHeader = CreateTableInteractionHeader();
        
        
        Image gif = new Image("Arrows.gif");
        ImageView gifView = new ImageView(gif);
        gifView.setFitWidth(615);
        gifView.setFitHeight(100);
        //gifView.setPreserveRatio(true);
        //Pane stackPane = new Pane(gifView);
        //gifView.setX(55);
        //gifView.setY(80);
        //double width = 75;
        //gifView.setFitWidth(width);
        //gifView.setFitHeight(width);
        //Pane stackPane = new Pane(imageView,gifView);
        
        //Image image = new Image("LogoDraft1.png");
        //ImageView imageView = new ImageView(image);
        hboxImage = new HBox(gifView);
        hboxImage.setAlignment(Pos.CENTER);
        hboxImage.setBackground(new Background(new BackgroundFill(
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        
        //copyright label set at full width
        copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        view = new VBox(hboxGreeting,categoryHeader,hboxImage,copyrightLabel);
        view.setAlignment(Pos.CENTER);
        view.setId("main-window");
        root.getChildren().add(view);
        
        stage.setScene(scene);
        
        consultant = new Consultant();
        //createDefaultCustomers();
        //Customer customer = new Customer(1, //ID
          //      "name", //name
            //    1 //addressID
              //  );
        
                /*
                true, //isActive
                java.sql.Date.valueOf(LocalDate.now()), //Date
                "name", //Creater
                java.sql.Timestamp.valueOf(LocalDateTime.now())); //timestamp
                */
                
        CustomerView customer = new CustomerView("Matt",
                    "111-222-3333",
                    "123 Silly Lane",
                    "Kansas City",
                    "123456",
                    "USA");
        
        consultant.addCustomer(customer);
        
        tvCustomers = new TableView<>(consultant.getAllCustomers());
        
        TableColumn<Customer, String> customerName = new TableColumn<>("Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvCustomers.getColumns().add(customerName);
        
        TableColumn<Customer, String> customerPhone = new TableColumn<>("Phone #");
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tvCustomers.getColumns().add(customerPhone);
        
        TableColumn<Customer, String> customerAddress = new TableColumn<>("Address");
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tvCustomers.getColumns().add(customerAddress);
        
        TableColumn<Customer, String> customerCity = new TableColumn<>("City");
        customerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tvCustomers.getColumns().add(customerCity);
        
        TableColumn<Customer, String> customerZip = new TableColumn<>("Zip Code");
        customerZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        tvCustomers.getColumns().add(customerZip);
        
        TableColumn<Customer, String> customerCountry = new TableColumn<>("Country");
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        tvCustomers.getColumns().add(customerCountry);

        /*
        TableColumn<Customer, String> customerName = new TableColumn<>("Customer Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tvCustomers.getColumns().add(customerName);
        */
        
        tvCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tvCustomers.setPrefWidth(615);
        tvCustomers.setPrefHeight(300);
        
        //TableView.TableViewSelectionModel<Customer> tvSelCustomer = tvCustomers.getSelectionModel();
    } 
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        labelName.setText("Hello, " + user + ". Select from the options below.");
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
        btnAdd.setId("interaction-button");
        Button btnDelete = new Button("Delete");
        btnDelete.setId("interaction-button");
        Button btnModify = new Button("Modify");
        btnModify.setId("interaction-button");
        Button btnSearch = new Button("Search");
        btnSearch.setId("interaction-button");
        TextField tfSearch = new TextField();
        tfSearch.setId("search-tf");
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String searchTerm = tfSearch.getText();
                    CustomerView foundCustomer = consultant.lookupCustomer(searchTerm);
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
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        
        return hbox;
    }
    
    public void selectCategoryCustomer()
    {
        if(selectedCategory != Selected.CUSTOMERS)
        {
            //labelName.setText("Customer View - Search and Modify Customers");
            view.getChildren().remove(hboxGreeting);
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
    HBox hboxGreeting;
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
