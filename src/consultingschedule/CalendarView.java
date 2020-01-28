/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author matthewp
 */
public class CalendarView extends VBox {

    //TODO: There's a problem with the weekly view for week 1 when sunday is day 1
    
    Cell[] cells;
    HBox[] rows;
    HBox header;

    ObservableList<Appointment> allAppointments;
    
    public CheckBox isWeeklyCheck;
    ComboBox weeksComboBox;

    String months[] = 
               {    "January",
                    "February",
                    "March", 
                    "April", 
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
               };
 
    String years[] = 
               {    "2020",
                    "2021",
                    "2022"
               };
    
    String weeks[] = 
               {    "Week 1",
                    "Week 2",
                    "Week 3",
                    "Week 4",
                    "Week 5"
               };
    
    int monthNumber = 1;
    int yearNumber = 2020;
    int weekNumber = 0;
    
    CalendarView(ObservableList<Appointment> Appointments){
        
        allAppointments = Appointments;
        
        Button updateBtn = new Button("Refresh");
        updateBtn.setOnAction((e) -> {
            UpdateCalendarView();
        });
        updateBtn.setId("refresh-button");
        
        ComboBox monthsComboBox = new ComboBox(FXCollections.observableArrayList(months)); 
        monthsComboBox.getSelectionModel().selectFirst();
        
        monthsComboBox.valueProperty().addListener((obs, oldText, newText) -> {
            monthNumber = GetMonthNumber((String)newText);
        });
        
        ComboBox yearsComboBox = new ComboBox(FXCollections.observableArrayList(years)); 
        yearsComboBox.getSelectionModel().selectFirst();
        yearsComboBox.setId("combobox-years");
        
        yearsComboBox.valueProperty().addListener((obs, oldText, newText) -> {
            yearNumber = Integer.parseInt((String)newText);
        });
        
        weeksComboBox = new ComboBox(FXCollections.observableArrayList(weeks)); 
        weeksComboBox.getSelectionModel().selectFirst();
        
        weeksComboBox.valueProperty().addListener((obs, oldText, newText) -> {
            weekNumber = GetWeekNumber((String)newText);
        });
        
        weeksComboBox.setDisable(true);

        isWeeklyCheck = new CheckBox();

        Label isWeeklyLabel = new Label("View By Week?");
        isWeeklyLabel.setId("label-week");
      
        header = new HBox(updateBtn, monthsComboBox, yearsComboBox, isWeeklyLabel , isWeeklyCheck,  weeksComboBox);
        
        header.setAlignment(Pos.CENTER);
        header.setBackground(new Background(new BackgroundFill(
                    Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
        
        CreateMonthlyCalendar();
    }
    
    public int GetMonthNumber(String month)
    {
        switch (month)
        {
            case "January":
                    return 1;
            case  "February":
                    return 2;
            case  "March":
                    return 3;
            case  "April":
                    return 4;
            case  "May":
                    return 5;
            case   "June":
                    return 6;
            case  "July":
                    return 7;
            case  "August":
                    return 8;
            case  "September":
                    return 9;
            case  "October":
                    return 10;
            case  "November":
                    return 11;
            case  "December":
                    return 12;
        }
        return -1;
    }
    
    public int GetWeekNumber(String week)
    {
        switch (week)
        {
            case "Week 1":
                    return 0;
            case  "Week 2":
                    return 1;
            case  "Week 3":
                    return 2;
            case  "Week 4":
                    return 3;
            case  "Week 5":
                    return 4;
        }
        return -1;
    }
    
    public void UpdateCalendarView()
    {
        if( isWeeklyCheck.isSelected() ) 
        {
            weeksComboBox.setDisable(false);
            CreateWeeklyCalendar();
        }
        else 
        {
            weeksComboBox.setDisable(true);
            CreateMonthlyCalendar();
        }
    }
    
    private void CreateMonthlyCalendar()
    {
        getChildren().clear();
        
        int numColumns = 7;
        int numRows = 6;
        cells = new Cell[numColumns * numRows];
        
        LocalDate localDate = LocalDate.of(yearNumber, monthNumber, 01);
        java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int firstDayValue = dayOfWeek.getValue();
        if(firstDayValue == 7) firstDayValue = 0;
        int daysInMonth = localDate.lengthOfMonth();
        HBox[] rows = new HBox[numRows];
        int dayNum = 1;
        
        for (int r = 0; r < rows.length; r++)
        {
            rows[r] = new HBox();
            if(r == 0)
            {
                cells[0] = new Cell("Sunday");
                cells[1] = new Cell("Monday");
                cells[2] = new Cell("Tuesday");
                cells[3] = new Cell("Wednesday");
                cells[4] = new Cell("Thursday");
                cells[5] = new Cell("Friday");
                cells[6] = new Cell("Saturday");
                for (int i = 0; i < 7; i++) rows[r].getChildren().add( cells[i].getView() );
                rows[r].setBackground(new Background(new BackgroundFill(
                    Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else
            {
                for (int i = 0; i < 7; i++) {
                    int index = i + 7 * r;
                    int compareDays = index - 7;
                    
                    if(compareDays < firstDayValue) cells[index] = new Cell();
                    else{
                        if(dayNum < daysInMonth + 1)
                        {
                            LocalDate thisDay = LocalDate.of(yearNumber, monthNumber, dayNum);
                            int numAppointments = 0;
                            String displayText = "";
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            for (int a = 0; a < allAppointments.size(); a++) {
                                if( (allAppointments.get(a).getStartTime().getDayOfYear() == thisDay.getDayOfYear() )
                                        & (allAppointments.get(a).getStartTime().getYear() == yearNumber) )
                                {
                                    displayText += allAppointments.get(a).getTitle() + " (" + allAppointments.get(a).getType() + ") " + ": " 
                                            + dateFormat.format(Date.from( allAppointments.get(a).getStartTime().atZone( ZoneId.systemDefault()).toInstant()))
                                            + "\n";
                                    numAppointments++;
                                }
                            }
                            
                            if( numAppointments > 0 ) cells[index] = new Cell(dayNum, numAppointments, displayText);
                            else cells[index] = new Cell(dayNum);
                            dayNum++;
                        }
                        else cells[index] = new Cell();
                    }
                    rows[r].getChildren().add( cells[index].getView() );
                }
            }
            getChildren().add(rows[r]);
        }
        getChildren().add(header);
    }
    
    private void CreateWeeklyCalendar()
    {
        getChildren().clear();
        
        int day = allAppointments.get(0).getStartTime().getDayOfYear();
        int year = allAppointments.get(0).getStartTime().getYear();
        
        int numColumns = 7;
        int numRows = 2;
        cells = new Cell[numColumns * numRows];
        
        
        int daySearch = 1;
        int dayNum;
        LocalDate localDate = LocalDate.of(yearNumber, monthNumber, daySearch);
        
        if(weekNumber != 0)
        {
            for(int num = 0; num < weekNumber; num++)
            {
                do{
                    localDate = LocalDate.of(yearNumber, monthNumber, daySearch);
                    daySearch++;
                } while (localDate.getDayOfWeek().getValue() != java.time.DayOfWeek.SUNDAY.getValue());
            }
        }

        
        java.time.DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int firstDayValue = dayOfWeek.getValue();
        if(firstDayValue == 7) firstDayValue = 0;
        int daysInMonth = localDate.lengthOfMonth();
            
        
        if(weekNumber != 0) dayNum = daySearch - 1;
        else dayNum = 1;
        
        HBox[] rows = new HBox[numRows];

        for (int r = 0; r < rows.length; r++)
        {
            rows[r] = new HBox();
            if(r == 0)
            {
                cells[0] = new Cell("Sunday");
                cells[1] = new Cell("Monday");
                cells[2] = new Cell("Tuesday");
                cells[3] = new Cell("Wednesday");
                cells[4] = new Cell("Thursday");
                cells[5] = new Cell("Friday");
                cells[6] = new Cell("Saturday");
                for (int i = 0; i < 7; i++) rows[r].getChildren().add( cells[i].getView() );
                rows[r].setBackground(new Background(new BackgroundFill(
                    Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else
            {
                for (int i = 0; i < 7; i++) {
                    int index = i + 7 * r;
                    int compareDays = index - 7;
                    
                    if( (compareDays < firstDayValue) & weekNumber == 0 ) cells[index] = new Cell();
                    else{
                        if(dayNum < daysInMonth + 1)
                        {
                            LocalDate thisDay = LocalDate.of(yearNumber, monthNumber, dayNum);
                            int numAppointments = 0;
                            String displayText = "";
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            for (int a = 0; a < allAppointments.size(); a++) {
                                if( (allAppointments.get(a).getStartTime().getDayOfYear() == thisDay.getDayOfYear() )
                                        & (allAppointments.get(a).getStartTime().getYear() == yearNumber) )
                                {
                                    displayText += allAppointments.get(a).getTitle() + " (" + allAppointments.get(a).getType() + ") " + ": " 
                                            + dateFormat.format(Date.from( allAppointments.get(a).getStartTime().atZone( ZoneId.systemDefault()).toInstant()))
                                            + "\n";
                                    numAppointments++;
                                }
                            }
                            
                            if( numAppointments > 0 ) cells[index] = new Cell(dayNum, numAppointments, displayText);
                            else cells[index] = new Cell(dayNum);
                            dayNum++;
                        }
                        else cells[index] = new Cell();
                    }
                    rows[r].getChildren().add( cells[index].getView() );
                }
            }
            getChildren().add(rows[r]);
        }
        getChildren().add(header);
    }

    public class Cell{
        
        Cell(){
            view = new VBox();
            view.setId("cell");
            SetupCell();
        }
        
        Cell(String dayName){
            isHeader = true;
            label = new Label(dayName);
            label.setId("day-label");
            view = new VBox(label);
            view.setId("cell-header");
            SetupCell();
            view.setPrefHeight(25);
            view.setAlignment(Pos.CENTER);
        }
        
        Cell(int _dayIndex){
            isDayOfMonth = true;
            dayIndex = _dayIndex;
            label = new Label( Integer.toString(dayIndex) );
            view = new VBox(label);
            view.setId("cell");
            SetupCell();
            view.setAlignment(Pos.TOP_LEFT);
        }

        Cell(int _dayIndex, int _numAppointments, String displayText)
        {
            hasAppointment = true;
            dayIndex = _dayIndex;
            label = new Label( Integer.toString(dayIndex) );
            numAppointments = _numAppointments;
            //listIds = list;
            Button btn = new Button(numAppointments + " appt");
            btn.setId("button-appointment");

            btn.setOnAction(e -> { 
                Alert appointmentInformation = new Alert(Alert.AlertType.INFORMATION);
                appointmentInformation.setTitle("Daily Schedule Information");
                if(numAppointments == 1) appointmentInformation.setHeaderText("You have 1 Appointment on this day");
                else appointmentInformation.setHeaderText("You have " + numAppointments + " Appointments on this day");
                appointmentInformation.setContentText(displayText);
                appointmentInformation.showAndWait();
            });
            
            view = new VBox(label,btn);
            view.setId("cell");
            SetupCell();
            view.setAlignment(Pos.TOP_LEFT);
        }
        
        private void SetupCell()
        {
            int cw = 100;
            view.setPrefSize(cw, cw);
            
        }
        
        public VBox getView ()
        {
            return view;
        }
        
        Label label;
        //Button btn;
        
        int dayIndex;
        int numAppointments;
        List<Integer> listIds;
        //int[] appointmentID;
        
        String dayName;
        
        VBox view;
        Boolean hasAppointment = false;
        Boolean isHeader = false;
        Boolean isDayOfMonth = false;     
    }
}
