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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        
        btnSignOut = new Button("Sign Out");
        btnSignOut.setId("header-button");
        
        Button btnCustomers = new Button("Customers");
        btnCustomers.setId("header-button");
        
        
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

        HBox header = new HBox(btnCustomers,btnAppointments,btnCalendar,btnReports, btnSignOut); 
        
        Image image = new Image("LogoDraft1.png");
        ImageView imageView = new ImageView(image);
        hboxImage = new HBox(imageView);
        hboxImage.setAlignment(Pos.CENTER);
        
        //copyright label set at full width
        Label copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        view = new VBox(labelName,header,hboxImage,copyrightLabel);
        view.setAlignment(Pos.CENTER);
        view.setId("main-window");
        root.getChildren().add(view);
        
        stage.setScene(scene);
        
        Consultant consultant = new Consultant();
        //createDefaultCustomers();
        Customer customer = new Customer(1, //ID
                "name", //name
                1, //addressID
                true, //isActive
                java.sql.Date.valueOf(LocalDate.now()), //Date
                "name", //Creater
                java.sql.Timestamp.valueOf(LocalDateTime.now())); //timestamp
        
        consultant.addCustomer(customer);
        
        TableView tvCustomers = new TableView<Customer>(consultant.getAllCustomers());
        
        TableColumn<Customer, String> customerId = new TableColumn<>("Customer ID");
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tvCustomers.getColumns().add(customerId);

        TableColumn<Customer, String> customerName = new TableColumn<>("Customer Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tvCustomers.getColumns().add(customerName);
        
        tvCustomers.setPrefWidth(355);
        tvCustomers.setPrefHeight(300);
        
        //TableView.TableViewSelectionModel<Customer> tvSelCustomer = tvCustomers.getSelectionModel();

        btnCustomers.setOnAction(e -> { 
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(copyrightLabel);
            view.getChildren().add(tvCustomers);
            view.getChildren().add(copyrightLabel);
            stage.sizeToScene();
        });
    } 
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        labelName.setText("Welcome back " + user);
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
    
    
    VBox view;
    HBox hboxImage;
    Label labelName = new Label();
    public static Button btnSignOut;
    private String user;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
}
