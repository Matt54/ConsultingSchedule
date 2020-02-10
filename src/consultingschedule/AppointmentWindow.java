
package consultingschedule;

import static consultingschedule.ConsultingSchedule.LDTtoUTC;
import static consultingschedule.ConsultingSchedule.UTCtoLDT;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class AppointmentWindow {
    
    AppointmentWindow(MainWindow main, Consultant _consultant)
    {
        consultant = _consultant;
        labelHeader = new Label("Add Appointment");
        gridPane = new GridPane();
        
        CreateTitleField(gridPane, "Title:", "",0 );
        CreateCustomersField(gridPane,"", 1);
        CreateContactField(gridPane, "Contact:", "",2 );
        CreateURLField(gridPane, "URL:", "",3 );
        
        CreateLineSeperation(gridPane);
        
        CreateTypeField(gridPane, "Type:", "",0 );
        CreateDateField(gridPane, 1, null);
        CreateTimeField(gridPane, 2, null);
        CreateLocationField(gridPane, "Location:", "",3 );

        CreateSpaceSeperation(gridPane);
        
        CreateDescriptionField(gridPane, "Description:", "",5 );

        SetupWindow(main);
    }
    
    AppointmentWindow(MainWindow main, AppointmentView av ,Consultant _consultant)
    {
        consultant = _consultant;
        isModify = true;
        appointmentView = av;
        labelHeader = new Label("Modify Appointment");
        gridPane = new GridPane();
        
        CreateTitleField(gridPane, "Title:", appointmentView.getTitle(),0 );
        CreateCustomersField(gridPane,consultant.lookupCustomer(consultant.lookupAppointment(appointmentView.getAppointmentId()).getCustomerId()).getCustomerName(), 1);
        CreateContactField(gridPane, "Contact:", consultant.lookupAppointment(appointmentView.getAppointmentId()).getContact() ,2 );
        CreateURLField(gridPane, "URL:", consultant.lookupAppointment(appointmentView.getAppointmentId()).getUrl(),3 );
        
        CreateLineSeperation(gridPane);
        
        CreateTypeField(gridPane, "Type:", appointmentView.getType(),0 );
        CreateDateField(gridPane, 1, consultant.lookupAppointment(appointmentView.getAppointmentId()).getStartTime());
        CreateTimeField(gridPane, 2, consultant.lookupAppointment(appointmentView.getAppointmentId()).getStartTime());
        CreateLocationField(gridPane, "Location:", consultant.lookupAppointment(appointmentView.getAppointmentId()).getLocation(),3 );
        
        CreateSpaceSeperation(gridPane);
        
        CreateDescriptionField(gridPane, "Description:", consultant.lookupAppointment(appointmentView.getAppointmentId()).getDescription(),5 );

        SetupWindow(main);
    }
    
    
    
    public void CreateTitleField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        titleTextField = new TextField(name);
        titleTextField.setId("long-textfield");
        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(titleTextField, 1,index,1,1);
    }
    
    public void CreateCustomersField(GridPane gridPane,String customerName, int index)
    {
        Label label = new Label("Customer Name:");
       
        String[] customers = new String[consultant.getAllCustomers().size()];

        for (int i = 0; i <customers.length; i++) customers[i] = (consultant.getAllCustomers().get(i).getCustomerName());
        
        customerComboBox = new ComboBox(FXCollections.observableArrayList(customers)); 
        customerComboBox.setId("combobox-long");
        
        if(customerName=="") customerComboBox.getSelectionModel().selectFirst();
        else {
            boolean found = false;
            for(String customer : customers){
                if(customer == customerName) 
                {
                    customerComboBox.getSelectionModel().select(customerName);
                    found = true;
                }
            }
            if(!found) customerComboBox.getSelectionModel().selectFirst();
        }

        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(customerComboBox, 1,index,1,1);
    }
    
    public void CreateContactField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        contactTextField = new TextField(name);
        contactTextField.setId("type-textfield");
        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(contactTextField, 1,index,1,1);
    }

    public void CreateURLField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        urlTextField = new TextField(name);
        urlTextField.setId("type-textfield");
        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(urlTextField, 1,index,1,1);
    }
    
    public void CreateLineSeperation(GridPane gridPane){
        
        double width = 20;
        
        Line line = new Line(width/2, 20, width/2, 190);
        line.setStrokeWidth(width/10);
        
        VBox vBox = new VBox(line);
        vBox.setPrefWidth(width);
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox,2,0,1,4);
    }
    
    public void CreateTypeField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        typeTextField = new TextField(name);
        typeTextField.setId("long-textfield");
        gridPane.add(label, 3,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(typeTextField, 4,index,1,1);
    }
    
    public void CreateLocationField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        locationTextField = new TextField(name);
        locationTextField.setId("type-textfield");
        gridPane.add(label, 3,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(locationTextField, 4,index,1,1);
    }

    public void CreateDateField(GridPane gridPane, int index, LocalDateTime localDateTime)
    {
        LocalDateTime startLdt = UTCtoLDT(localDateTime);
        LocalDate localDate = startLdt.toLocalDate();
        
        Label label = new Label("Select Date:");
        datePicker = new DatePicker(); 
        
        if(localDate!=null) datePicker.setValue(localDate);
        
        gridPane.add(label, 3,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(datePicker, 4,index,1,1);
    }
    
    public void CreateTimeField(GridPane gridPane, int index, LocalDateTime localDateTime)
    {
        LocalDateTime startLdt = UTCtoLDT(localDateTime);
        LocalTime localTime = startLdt.toLocalTime();

        Label label = new Label("Select Time:");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mm a");
        
        String[] timesFormatted = new String[times.length];
        for (int i = 0; i < timesFormatted.length; i++) timesFormatted[i] = times[i].format(dateFormat);

        timeComboBox = new ComboBox(FXCollections.observableArrayList(timesFormatted)); 
        timeComboBox.setId("combobox-long");
        timeComboBox.getSelectionModel().selectFirst();

            for(LocalTime time : times){
                if(time == localTime) timeComboBox.getSelectionModel().select(time.format(dateFormat));
            }

        gridPane.add(label, 3,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(timeComboBox, 4,index,1,1);
    }
    
    public void CreateSpaceSeperation(GridPane gridPane){

        HBox hBox = new HBox();
        hBox.setPrefHeight(10);
        gridPane.add(hBox,0,4,4,1);
    }
    
    public void CreateDescriptionField(GridPane gridPane, String prompt, String name, int index)
    {
        Label label = new Label(prompt);
        descriptionTextArea = new TextArea(name);
        gridPane.add(label, 0,index,1,1);
        gridPane.setHalignment(label,HPos.RIGHT);
        gridPane.add(descriptionTextArea, 1,index,4,1);
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
            
            try{
                ValidateInputs();
                if(isModify) ModifyExistingAppointment( GenerateAppointmentFromInput() );
                else CreateNewAppointment( GenerateAppointmentFromInput() );
                mainWindow.show();
                stage.hide();
            }
            catch(Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
                alert.setHeaderText(exception.getClass().getSimpleName());
                alert.setTitle(exception.getClass().getSimpleName());
                alert.showAndWait();
            }
            
        });
        
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> { 
            mainWindow.show();
            stage.hide();
        });

        HBox btnHBox = new HBox(btnSave,btnCancel);
        btnHBox.setAlignment(Pos.CENTER);

        Label copyrightLabel = new Label("Copyright ® 2019 Matt Pfeiffer Consulting");
        copyrightLabel.setId("copyright-label");
        copyrightLabel.setAlignment(Pos.CENTER);
        copyrightLabel.setMaxWidth(Double.MAX_VALUE);
        
        VBox marginVBox = new VBox();
        marginVBox.setPrefWidth(10);
        VBox marginVBox2 = new VBox();
        marginVBox2.setPrefWidth(10);
        
        HBox contentHBox = new HBox(marginVBox,gridPane,marginVBox2);
        
        HBox marginHBox = new HBox();
        marginHBox.setPrefHeight(10);
        HBox marginHBox2 = new HBox();
        marginHBox2.setPrefHeight(5);

        VBox view = new VBox(headerHBox,marginHBox,contentHBox,btnHBox,marginHBox2,copyrightLabel);
        
        
        view.setAlignment(Pos.CENTER);
        view.setBackground(new Background(new BackgroundFill(
               Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        
        root.getChildren().add(view);
        stage.setScene(scene);
        stage.show();
    }
    
    private Appointment GenerateAppointmentFromInput(){
        LocalDate localDate = datePicker.getValue();
        int index = timeComboBox.getSelectionModel().getSelectedIndex();
        LocalTime startTime = times[index];
        LocalTime endTime = times[index + 1];

        LocalDateTime startUTCLDT = LDTtoUTC(LocalDateTime.of(localDate, startTime));
        LocalDateTime endUTCLDT = LDTtoUTC(LocalDateTime.of(localDate, endTime));

        Appointment newAppointment = new Appointment(1,
                                            consultant.lookupCustomer(customerComboBox.getSelectionModel().getSelectedItem().toString()).getCustomerId(),
                                            1,
                                            titleTextField.getText(),
                                            descriptionTextArea.getText(),
                                            locationTextField.getText(),
                                            contactTextField.getText(),
                                            typeTextField.getText(),
                                            urlTextField.getText(),
                                            startUTCLDT,
                                            endUTCLDT);
        return newAppointment;
    }
    
    private void ModifyExistingAppointment(Appointment newAppointment){
        newAppointment.setAppointmentId(appointmentView.getAppointmentId());
        newAppointment.updateDB();
        
        consultant.deleteAppointmentView(appointmentView.getAppointmentId());
        //consultant.deleteAppointment(consultant.lookupAppointment(appointmentView.getAppointmentId()));
        //consultant.addAppointment(newAppointment);
        consultant.updateAppointment(appointmentView.getAppointmentId(), newAppointment);
        consultant.AddAppointmentToView(consultant.lookupAppointment(appointmentView.getAppointmentId()));
        
    }
    
    private void CreateNewAppointment(Appointment newAppointment){
        newAppointment.addToDB();
        consultant.addAppointment(newAppointment);
        consultant.AddAppointmentToView(newAppointment);
    }
    
    private void ValidateInputs(){
        
        boolean valid = true;
        
        if(descriptionTextArea.getText().equals("")) valid = false;
        if(urlTextField.getText().equals("")) valid = false;
        if(contactTextField.getText().equals("")) valid = false;
        if(locationTextField.getText().equals("")) valid = false;
        if(titleTextField.getText().equals("")) valid = false;
        if(typeTextField.getText().equals("")) valid = false;
        if(customerComboBox.getSelectionModel().isEmpty()) valid = false;
        if(timeComboBox.getSelectionModel().isEmpty()) valid = false;
        if(datePicker.getValue() == null) valid = false;
        
        if(!valid) throw new IllegalArgumentException("You have empty input values.");

        LocalDate localDate = datePicker.getValue();
        int index = timeComboBox.getSelectionModel().getSelectedIndex();
        LocalTime time = times[index];
        
        LocalDateTime uTCLDT = LDTtoUTC(LocalDateTime.of(localDate, time));
        
        if(uTCLDT.getHour() < 14 || uTCLDT.getHour() > 23) throw new IllegalArgumentException("You can't schedule an appointment outside of business hours (14-23 UTC.).");
        
        if(uTCLDT.getDayOfWeek().getValue() > 5) throw new IllegalArgumentException("You can't schedule an appointment during the weekend.");
        
        if(!isModify)
        {
            for(Appointment appointment : consultant.getAllAppointments())
            {
                LocalDateTime aptTime = appointment.getStartTime();
                if(uTCLDT.getYear() == aptTime.getYear())
                {
                    if(uTCLDT.getDayOfYear() == aptTime.getDayOfYear())
                    {
                        if(uTCLDT.getHour() == aptTime.getHour()) throw new IllegalArgumentException("You have another appointment during this hour.");
                    }
                }
            }
        }

    }
    
    TextField urlTextField;
    TextField locationTextField;
    TextField contactTextField;
    TextField titleTextField;
    TextField typeTextField;
    TextArea descriptionTextArea;
    ComboBox customerComboBox;
    ComboBox timeComboBox;
    DatePicker datePicker;
    
    Consultant consultant;
    boolean isModify = false;
    GridPane gridPane;
    Label labelHeader;
    AppointmentView appointmentView;
    MainWindow mainWindow;
    StackPane root = new StackPane();
    Scene scene = new Scene(root);
    Stage stage;
    
    LocalTime times[] = {
                    LocalTime.of(0, 0),
                    LocalTime.of(1, 0),
                    LocalTime.of(2, 0),
                    LocalTime.of(3, 0),
                    LocalTime.of(4, 0),
                    LocalTime.of(5, 0),
                    LocalTime.of(6, 0),
                    LocalTime.of(7, 0),
                    LocalTime.of(8, 0),
                    LocalTime.of(9, 0),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    LocalTime.of(12, 0),
                    LocalTime.of(13, 0),
                    LocalTime.of(14, 0),
                    LocalTime.of(15, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(17, 0),
                    LocalTime.of(18, 0),
                    LocalTime.of(19, 0),
                    LocalTime.of(20, 0),
                    LocalTime.of(21, 0),
                    LocalTime.of(22, 0),
                    LocalTime.of(23, 0),
                };
}
