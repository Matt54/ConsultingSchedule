/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
        hboxGreeting = new HBox(labelName);
        hboxGreeting.setAlignment(Pos.CENTER);

        hboxGreeting.setBackground(new Background(new BackgroundFill(
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        
        HBox categoryHeader = CreateCategoryHeader();
        interactionHeader = CreateTableInteractionHeader();
        
        
        Image gif = new Image("arrows_2.gif");
        ImageView gifView = new ImageView(gif);
        gifView.setFitWidth(615);
        gifView.setFitHeight(100);

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

    } 
    
    public void LoadDataAndGenerateWindow()
    {
        tvAppointments = CreateAppointmentTV();
        tvCustomers = CreateCustomerTV();
        calendarView = CreateCalendarView();
    }
    
    public CalendarView CreateCalendarView(){
        view.getChildren().remove(calendarView);
        CalendarView cv = new CalendarView(consultant.getAllAppointments());
        cv.isWeeklyCheck.setOnAction((e) -> {
            cv.UpdateCalendarView();
            stage.sizeToScene();
        });
        return cv;
    }
    
    public void handleSignIn()
    {
        CurrentUser currentUser = CurrentUser.getInstance();
        user = currentUser.getUser().getUserName();
        
        //all data pulling happens here
        consultant.setUserId(currentUser.getUser().getUserId());
        consultant.PopulateFromDB(currentUser.getUser().getUserId());
        LoadDataAndGenerateWindow();
        
        labelName.setText("Hello, " + user + ". Select from the options below.");
        
        //CheckUpcomingAppointments();
        
        //write to text file
        try{
            WriteToActivityLog();
        }
        catch(IOException e)
        {
            Alert ioAlert = new Alert(Alert.AlertType.WARNING);
            ioAlert.setTitle("ALERT");
            ioAlert.setHeaderText("Exception!");
            ioAlert.setContentText(e.getMessage());
            ioAlert.showAndWait();
        }
        
    }
    
    public void WriteToActivityLog() throws IOException{
        File directory = new File("C:/temp/ConsultingApp");
        directory.mkdir();
        Path path = Paths.get("C:/temp/ConsultingApp/LogFile.txt");
 
        String pathString = "C:/temp/ConsultingApp/LogFile.txt";
        FileWriter fileWriter;
        if(Files.notExists(path)) fileWriter = new FileWriter(pathString, false);
        else fileWriter = new FileWriter(pathString, true);
            
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(LocalDateTime.now().toString());
        printWriter.close();
        fileWriter.close();

    }
    
    public void CheckUpcomingAppointments(){
        LocalDateTime now = LocalDateTime.now();
        for(Appointment appointment : consultant.getAllAppointments())
        {
            LocalDateTime aptTime = appointment.getStartTime();
            if(now.getYear() == aptTime.getYear())
            {
                if(now.getDayOfYear() == aptTime.getDayOfYear())
                {
                    if(abs(now.getMinute() - aptTime.getMinute()) < 15)
                    {
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        Alert appointmentInformation = new Alert(Alert.AlertType.WARNING);
                        appointmentInformation.setTitle("Appointment Reminder");
                        appointmentInformation.setHeaderText("You have an appointment within 15 minutes!");
                        appointmentInformation.setContentText(appointment.getTitle() + " (" + appointment.getType() + ") " + ": " 
                                                + dateFormat.format(java.util.Date.from( appointment.getStartTime().atZone( ZoneId.systemDefault()).toInstant())));
                        appointmentInformation.showAndWait();
                    }
                }
            }
        }
    }
    
    public TableView CreateCustomerTV()
    {
        TableView tv = new TableView<CustomerView>(consultant.getAllCustomerViews());
        
        TableColumn<CustomerView, String> customerName = new TableColumn<>("Name");
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tv.getColumns().add(customerName);
        
        TableColumn<CustomerView, String> customerCountry = new TableColumn<>("Country");
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        tv.getColumns().add(customerCountry);
        
        TableColumn<CustomerView, String> customerCity = new TableColumn<>("City");
        customerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tv.getColumns().add(customerCity);
        
        TableColumn<CustomerView, String> customerAddress = new TableColumn<>("Address");
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tv.getColumns().add(customerAddress);
        
        TableColumn<CustomerView, String> customerZip = new TableColumn<>("Zip Code");
        customerZip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        tv.getColumns().add(customerZip);
        
        TableColumn<CustomerView, String> customerPhone = new TableColumn<>("Phone #");
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tv.getColumns().add(customerPhone);
        
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tv.setPrefWidth(615);
        tv.setPrefHeight(300);
        
        return tv;
    }
    
    public TableView CreateAppointmentTV()
    {
        
        TableView tv = new TableView<>(consultant.getAllAppointmentViews());
        
        
        TableColumn<AppointmentView, String> aptTitle = new TableColumn<>("Title");
        aptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tv.getColumns().add(aptTitle);
        
        TableColumn<AppointmentView, String> aptType = new TableColumn<>("Type");
        aptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tv.getColumns().add(aptType);
        
        TableColumn<AppointmentView, String> start = new TableColumn<>("Start Time");
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        tv.getColumns().add(start);
        
        TableColumn<AppointmentView, String> endTime = new TableColumn<>("End Time");
        endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        tv.getColumns().add(endTime);
        
        TableColumn<AppointmentView, String> customerName = new TableColumn<>("Customer");
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tv.getColumns().add(customerName);
        
        TableColumn<AppointmentView, String> aptLocation = new TableColumn<>("Location");
        aptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tv.getColumns().add(aptLocation);

        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tv.setPrefWidth(615);
        tv.setPrefHeight(300);
        
        return tv;
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
            selectCategoryAppointment();
        });
        
        Button btnCalendar = new Button("Calendar");
        btnCalendar.setId("header-button");
        btnCalendar.setOnAction(e -> { 
            selectCategoryCalendar();
        });
        
        Button btnReports = new Button("Reports");
        btnReports.setId("header-button");
        btnReports.setOnAction(e -> { 
            
        });
        
        return new HBox(btnCalendar,btnAppointments,btnCustomers,btnReports, btnSignOut); 
    }
    
    public HBox CreateTableInteractionHeader()
    {
        headerLabel = new Label();
        headerLabel.setId("header-label");
        
        
        Button btnAdd = new Button("Add");
        btnAdd.setId("interaction-button");
        btnAdd.setOnAction(e -> { 
            //customerWindow = new CustomerWindow(this,consultant);
            if(selectedCategory == Selected.CUSTOMERS) OpenCustomerWindow();
            else OpenAppointmentWindow();
        });
        
        Button btnDelete = new Button("Delete");
        btnDelete.setId("interaction-button");
        btnDelete.setOnAction(e -> { 

            selectedCustomer = (CustomerView) tvCustomers.getSelectionModel().getSelectedItem();
            if(selectedCustomer != null)
            {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(AlertType.WARNING,
                        "Are you sure you want to delete the customer and all his/her appointments?",
                        yes,
                        no);

                alert.setTitle("Confirm Customer Delete");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no) == yes) consultant.deleteCustomer(consultant.lookupCustomer(selectedCustomer.getName()));
                
            }
        });
        
        Button btnModify = new Button("Modify");
        
        btnModify.setId("interaction-button");
        btnModify.setOnAction(e -> { 
            selectedCustomer = (CustomerView) tvCustomers.getSelectionModel().getSelectedItem();
            if(selectedCustomer != null)
            {
                customerWindow = new CustomerWindow(this, selectedCustomer,consultant);
            }
        });
        
        Button btnSearch = new Button("Search");
        btnSearch.setId("interaction-button");
        
        tfSearch = new TextField();
        tfSearch.setId("search-tf");
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String searchTerm = tfSearch.getText();
                    CustomerView foundCustomer = consultant.lookupCustomerView(searchTerm);
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
        
        
        HBox hbox = new HBox(headerLabel, btnAdd, btnDelete, btnModify, btnSearch, tfSearch);
        
        hbox.setBackground(new Background(new BackgroundFill(
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
    
    public void OpenCustomerWindow(){
        customerWindow = new CustomerWindow(this,consultant);
    }
    
    public void OpenAppointmentWindow(){
        appointmentWindow = new AppointmentWindow(this,consultant);
    }
    
    public void selectCategoryCustomer()
    {
        if(selectedCategory != Selected.CUSTOMERS)
        {
            view.getChildren().remove(hboxGreeting);
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(copyrightLabel);
            view.getChildren().remove(calendarView);
            view.getChildren().remove(tvAppointments);
            view.getChildren().remove(interactionHeader);
            tfSearch.setPromptText("search name");
            headerLabel.setText("Customers:");
            
            view.getChildren().add(tvCustomers);
            view.getChildren().add(interactionHeader);
            view.getChildren().add(copyrightLabel);
            stage.sizeToScene();
        }
        selectedCategory = Selected.CUSTOMERS;
    }
    
    public void selectCategoryCalendar()
    {
        if(selectedCategory != Selected.CALENDAR)
        {
            calendarView = CreateCalendarView(); //to ensure that we have updated the calendar
            view.getChildren().remove(hboxGreeting);
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(copyrightLabel);
            view.getChildren().remove(tvCustomers);
            view.getChildren().remove(tvAppointments);
            view.getChildren().remove(interactionHeader);
            
            view.getChildren().add(calendarView);
            view.getChildren().add(copyrightLabel);
            stage.sizeToScene();
        }
        selectedCategory = Selected.CALENDAR;
    }
    
    public void selectCategoryAppointment()
    {
        if(selectedCategory != Selected.APPOINTMENTS)
        {
            view.getChildren().remove(hboxGreeting);
            view.getChildren().remove(hboxImage);
            view.getChildren().remove(copyrightLabel);
            view.getChildren().remove(calendarView);
            view.getChildren().remove(tvCustomers);
            view.getChildren().remove(interactionHeader);
            tfSearch.setPromptText("search title");
            headerLabel.setText("Appointments:");
            
            view.getChildren().add(tvAppointments);
            view.getChildren().add(interactionHeader);
            view.getChildren().add(copyrightLabel);
            stage.sizeToScene();
        }
        selectedCategory = Selected.APPOINTMENTS;
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
    
    CustomerView selectedCustomer;
    CalendarView calendarView;
    
    AppointmentWindow appointmentWindow;
    CustomerWindow customerWindow;
    Consultant consultant;
    TableView tvCustomers;
    TableView tvAppointments;
    
    Label headerLabel;
    TextField tfSearch;
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
