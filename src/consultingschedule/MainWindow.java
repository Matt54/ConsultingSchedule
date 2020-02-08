package consultingschedule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow {
    
    MainWindow()
    {
        stage = new Stage();
        stage.setTitle("Consulting Schedule Application By Matt Pfeiffer");
        scene.getStylesheets().add("consultingschedule/StyleSheet.css");
        labelName.setId("big-label");
        
        categoryHeader = CreateCategoryHeader();
        interactionHeader = CreateTableInteractionHeader();
        reportsHeader = CreateReportsHeader();
        
        //copyright label set at full width
        copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        view = new VBox(categoryHeader,copyrightLabel);
        
        view.setAlignment(Pos.CENTER);
        view.setId("main-window");
        root.getChildren().add(view);
        
        stage.setScene(scene);
        stage.setResizable(false);
        consultant = new Consultant();

    } 
    
    public void LoadDataAndGenerateWindow()
    {
        tvAppointments = CreateAppointmentTV();
        tvCustomers = CreateCustomerTV();
        tvReportType = CreateReportTypeTV();
        taReports = CreateReportsTA();
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
        consultant.populateReportTypeViews();
        
        InformationGenerator infoGen = new InformationGenerator(consultant);
        //infoGen.CreateRandomCustomers(20, infoGen.ReadCustomerCSVFile());
        //infoGen.DeleteAllConsultantCustomers();
        //infoGen.CreateRandomAppointments(200, infoGen.ReadAppointmentCSVFile());
        //infoGen.ReadAppointmentCSVFile();
        
        LoadDataAndGenerateWindow();
        selectCategoryCalendar();
        
        labelName.setText("Hello, " + user + ". Select from the options below.");
        
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
        
        CheckUpcomingAppointments();
        
    }
    
    public void WriteToActivityLog() throws IOException{
        
        Path defaultPath = FileSystems.getDefault().getPath(".").toAbsolutePath();
        File defaultDirectory = new File(defaultPath.toString());
        defaultDirectory.mkdir();
        Path path = Paths.get(defaultPath.toString() + "/LogFile.txt");
 
        FileWriter fileWriter;
        if(Files.notExists(path)) fileWriter = new FileWriter(path.toString(), false);
        else fileWriter = new FileWriter(path.toString(), true);
            
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
    
    public TextArea CreateReportsTA()
    {
        TextArea ta = new TextArea();
        ta.setId("textarea-reports");
        ta.setText(consultant.GetConsultantReport());
        return ta;
    }
    
    public TableView CreateReportTypeTV()
    {
        TableView tv = new TableView<ReportTypeView>(consultant.getAllReportTypeViews());
        
        TableColumn<ReportTypeView, String> monthAndYear= new TableColumn<>("Month, Year");
        monthAndYear.setCellValueFactory(new PropertyValueFactory<>("monthAndYear"));
        tv.getColumns().add(monthAndYear);
        
        TableColumn<ReportTypeView, String> type = new TableColumn<>("Appointment Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tv.getColumns().add(type);
        
        TableColumn<ReportTypeView, String> numberOfAppointments = new TableColumn<>("Number of Appointments");
        numberOfAppointments.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
        tv.getColumns().add(numberOfAppointments);
        
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tv.setPrefWidth(615);
        tv.setPrefHeight(300);
        
        return tv;
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
        
        /*
        TableColumn<AppointmentView, String> endTime = new TableColumn<>("End Time");
        endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        tv.getColumns().add(endTime);
        */
        
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
            selectCategoryReports();
        });
        
        return new HBox(btnCalendar,btnAppointments,btnCustomers,btnReports, btnSignOut); 
    }
    
    public HBox CreateReportsHeader(){
        headerLabel = new Label("Report Type");
        headerLabel.setId("header-label");
        
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(reportTypes)); 
        comboBox.getSelectionModel().selectFirst();
        comboBox.setId("combobox-reports");
        
        Button btn = new Button("Run Report");
        btn.setOnAction(e -> { 
            
            view.getChildren().clear();
            view.getChildren().add(categoryHeader);
            
            if(comboBox.getSelectionModel().getSelectedItem().equals("Consultants' Scheduled Hours")) 
            {
                taReports.setText(consultant.GetHoursReport());
                view.getChildren().add(taReports);
            }
            else if (comboBox.getSelectionModel().getSelectedItem().equals("Schedules for each Consultant")) 
            {
                taReports.setText(consultant.GetConsultantReport());
                view.getChildren().add(taReports);
            }
            else view.getChildren().add(tvReportType);
            
            view.getChildren().add(reportsHeader);
            view.getChildren().add(copyrightLabel);

            stage.sizeToScene();
        });
        
        HBox hbox = new HBox(headerLabel, comboBox, btn);
        
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
    
    String reportTypes[] = 
               {    "Number of Appointment Types by Month",
                    "Schedules for each Consultant",
                    "Consultants' Scheduled Hours"
               };
    
    public HBox CreateTableInteractionHeader()
    {
        headerLabel = new Label();
        headerLabel.setId("header-label");
        
        
        Button btnAdd = new Button("Add");
        btnAdd.setId("interaction-button");
        btnAdd.setOnAction(e -> { 
            if(selectedCategory == Selected.CUSTOMERS) OpenCustomerWindow();
            else OpenAppointmentWindow();
        });
        
        Button btnDelete = new Button("Delete");
        btnDelete.setId("interaction-button");
        btnDelete.setOnAction(e -> { 

            if(selectedCategory == Selected.CUSTOMERS)
            {
                selectedCustomer = (CustomerView) tvCustomers.getSelectionModel().getSelectedItem();
                if(selectedCustomer != null)
                {
                    if(DeleteConfirmation()) consultant.deleteCustomer(consultant.lookupCustomer(selectedCustomer.getName()));
                }
            }
            else
            {
                selectedAppointment = (AppointmentView) tvAppointments.getSelectionModel().getSelectedItem();
                if(selectedAppointment != null)
                {
                    if(DeleteConfirmation()) consultant.deleteAppointment(consultant.lookupAppointment(selectedAppointment.getAppointmentId()));
                }
            }
        });
        
        Button btnModify = new Button("Modify");
        
        btnModify.setId("interaction-button");
        btnModify.setOnAction(e -> { 

            if(selectedCategory == Selected.CUSTOMERS)
            {
                selectedCustomer = (CustomerView) tvCustomers.getSelectionModel().getSelectedItem();
                if(selectedCustomer != null) customerWindow = new CustomerWindow(this, selectedCustomer,consultant);
            }
            else
            {
                selectedAppointment = (AppointmentView) tvAppointments.getSelectionModel().getSelectedItem();
                appointmentWindow = new AppointmentWindow(this, selectedAppointment, consultant);
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
                    
                    if(selectedCategory == Selected.CUSTOMERS)
                    {
                        CustomerView foundCustomer = consultant.lookupCustomerView(searchTerm);
                        if(foundCustomer != null) tvCustomers.getSelectionModel().select(foundCustomer);
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer name not found.");
                            alert.showAndWait();
                        }
                    }
                    else
                    {
                        AppointmentView foundAppointment = consultant.lookupAppointmentView(searchTerm);
                        if(foundAppointment != null) tvAppointments.getSelectionModel().select(foundAppointment);
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment title not found.");
                            alert.showAndWait();
                        }
                    }
                }
            });
        
        VBox spaceWidth = new VBox();
        spaceWidth.setPrefWidth(10);

        HBox hbox = new HBox(headerLabel, btnAdd, btnDelete, btnModify, btnSearch, spaceWidth, tfSearch);
        
        hbox.setBackground(new Background(new BackgroundFill(
               Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
    
    public boolean DeleteConfirmation(){
        String prompt;
        String title;
        if(selectedCategory == Selected.CUSTOMERS) 
        {
            prompt = "Are you sure you want to delete the customer and all his/her appointments?";
            title = "Confirm Customer Delete";
        }
        else
        {
            prompt = "Are you sure you want to delete this appointment?";
            title = "Confirm Appointment Delete"; 
        }
        
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.WARNING,
                prompt,
                yes,
                no);

        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) return true;
        else return false;
    }
    
    public void OpenCustomerWindow(){
        customerWindow = new CustomerWindow(this,consultant);
    }
    
    public void OpenAppointmentWindow(){
        appointmentWindow = new AppointmentWindow(this,consultant);
    }
    
    public void selectCategoryCustomer()
    {
        view.getChildren().clear();
        tfSearch.setText("");
        tfSearch.setPromptText("search for customer name");
        view.getChildren().add(categoryHeader);
        view.getChildren().add(tvCustomers);
        Platform.runLater(()->tvCustomers.refresh()); //this llambda fixes a misalignment bug between the rows and header of the tableview
        view.getChildren().add(interactionHeader);
        view.getChildren().add(copyrightLabel);
        stage.sizeToScene();
        selectedCategory = Selected.CUSTOMERS;
    }
    
    public void selectCategoryCalendar()
    {
        calendarView = CreateCalendarView(); //to ensure that we have updated the calendar
        view.getChildren().clear();
        view.getChildren().add(categoryHeader);
        view.getChildren().add(calendarView);
        view.getChildren().add(copyrightLabel);
        stage.sizeToScene();
        selectedCategory = Selected.CALENDAR;
    }
    
    public void selectCategoryAppointment()
    {
        view.getChildren().clear();
        tfSearch.setText("");
        tfSearch.setPromptText("search for appointment title");
        view.getChildren().add(categoryHeader);
        view.getChildren().add(tvAppointments);
        Platform.runLater(()->tvAppointments.refresh()); //this llambda fixes a misalignment bug between the rows and header of the tableview
        view.getChildren().add(interactionHeader);
        view.getChildren().add(copyrightLabel);
        stage.sizeToScene();
        selectedCategory = Selected.APPOINTMENTS;
    }
    
    public void selectCategoryReports()
    {
        view.getChildren().clear();
        view.getChildren().add(categoryHeader);
        headerLabel.setText("Report Type:");
        Platform.runLater(()->tvReportType.refresh()); //this llambda fixes a misalignment bug between the rows and header of the tableview
        view.getChildren().add(tvReportType);
        view.getChildren().add(reportsHeader);
        view.getChildren().add(copyrightLabel);
        stage.sizeToScene();
        selectedCategory = Selected.REPORTS;
    }
    
    public void show(){
        stage.show();
        stage.sizeToScene();
    }
    
    public void hide(){
        stage.hide();
    }
    
    enum Selected{
        NONE,
        CUSTOMERS,
        APPOINTMENTS,
        CALENDAR,
        REPORTS
    }
    Selected selectedCategory = Selected.CALENDAR;
    
    AppointmentView selectedAppointment;
    CustomerView selectedCustomer;
    CalendarView calendarView;
    
    AppointmentWindow appointmentWindow;
    CustomerWindow customerWindow;
    Consultant consultant;
    
    TextArea taReports;
    TableView tvReportType;
    TableView tvCustomers;
    TableView tvAppointments;
    
    Label headerLabel;
    TextField tfSearch;
    VBox view;
    HBox hboxImage;
    HBox interactionHeader;
    HBox reportsHeader;
    HBox categoryHeader;
    Label labelName = new Label();
    Label copyrightLabel;
    public static Button btnSignOut;
    private String user;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
}
