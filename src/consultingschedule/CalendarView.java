package consultingschedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import javafx.beans.binding.Bindings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CalendarView extends VBox {

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
        
        LocalDate dateToday = LocalDate.now();
        
        yearNumber = dateToday.getYear();
        monthNumber = dateToday.getMonthValue();
        
        allAppointments = Appointments;
        
        Button updateBtn = new Button("Refresh");
        updateBtn.setOnAction((e) -> {
            UpdateCalendarView();
        });
        updateBtn.setId("refresh-button");
        
        ComboBox monthsComboBox = new ComboBox(FXCollections.observableArrayList(months)); 
        monthsComboBox.getSelectionModel().select(monthNumber - 1);
        
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

    private Color GetColorFromIndex(int index, boolean isHeader){
        int base = 250;
        
        if(!isHeader) return Color.rgb(base, base, base);
        else return Color.rgb(base - 25, base - 25, base - 25);
        
        /*
        if(index % 2 == 0) return Color.rgb(base, base, base);
        else return Color.rgb(base - 25, base - 25, base - 25);
        */
        
        /*
        int base = 230;
        int base2 = 255;
        
        if(isHeader)
        {
            if(index % 2 == 0) return Color.rgb(base, base, base);
            else return Color.rgb(base - 10, base - 10, base - 10);
        }
        else
        {
            if(index % 2 == 0) return Color.rgb(base2, base2, base2);
            else return Color.rgb(base2 - 10, base2 - 10, base2 - 10);
        }
        */
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
                for (int i = 0; i < 7; i++) 
                {
                    cells[i].getView().setBackground(new Background(new BackgroundFill(
                            GetColorFromIndex(i,true), CornerRadii.EMPTY, Insets.EMPTY)));
                    rows[r].getChildren().add( cells[i].getView() );
                }
                /*
                rows[r].setBackground(new Background(new BackgroundFill(
                    Color.rgb(220, 220, 220), CornerRadii.EMPTY, Insets.EMPTY)));
                */
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

                                    ZonedDateTime startUTC = allAppointments.get(a).getStartTime().atZone(ZoneId.of("UTC"));
                                    ZonedDateTime startZdt = startUTC.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
                                    LocalDateTime startLdt = startZdt.toLocalDateTime();

                                    displayText += allAppointments.get(a).getTitle() + " (" + allAppointments.get(a).getType() + ") " + ": " 
                                            + dateFormat.format(Date.from( startLdt.atZone( ZoneId.systemDefault()).toInstant()))
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
                    
                    cells[index].getView().setBackground(new Background(new BackgroundFill(
                            GetColorFromIndex(i,false), CornerRadii.EMPTY, Insets.EMPTY)));
                    
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
        int daysInMonth = localDate.lengthOfMonth();
        
        
        int firstDayValue = dayOfWeek.getValue();

        if(weekNumber != 0) 
        {
            dayNum = daySearch - 1;
            if(firstDayValue == 7) dayNum += 7;
        }
        else 
        {
            if(firstDayValue == 7) firstDayValue = 0;
            dayNum = 1;
        }
        
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
                for (int i = 0; i < 7; i++) 
                {
                    rows[r].getChildren().add( cells[i].getView() );
                    cells[i].getView().setBackground(new Background(new BackgroundFill(
                            GetColorFromIndex(i,true), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                /*
                rows[r].setBackground(new Background(new BackgroundFill(
                    Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
                */
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
                    cells[index].getView().setBackground(new Background(new BackgroundFill(
                            GetColorFromIndex(i,false), CornerRadii.EMPTY, Insets.EMPTY)));
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

            Image image = new Image("calendar.png");
            imageView = new ImageView(image);
            
            Image imageHover = new Image("calendar_hover.png");
            imageViewHover = new ImageView(imageHover);

            Button btn = new Button();
            btn.setGraphic(imageView);
            btn.setId("button-appointment");
            
            

            Label aptLabel = new Label( Integer.toString(numAppointments));

            aptLabel.setMouseTransparent(true);

            btn.setOnAction(e -> { 
                Alert appointmentInformation = new Alert(Alert.AlertType.INFORMATION);
                appointmentInformation.setTitle("Daily Schedule Information");
                if(numAppointments == 1) appointmentInformation.setHeaderText("You have 1 Appointment on this day");
                else appointmentInformation.setHeaderText("You have " + numAppointments + " Appointments on this day");
                appointmentInformation.setContentText(displayText);
                appointmentInformation.showAndWait();
            });
            
            
            
            StackPane stackPane = new StackPane(btn,aptLabel);
            btn.setTranslateY(-15);
            aptLabel.setTranslateY(-10);

            
            view = new VBox(label,stackPane);
            view.setId("cell");
            SetupCell();
            imageView.setFitWidth(cw/3);
            imageView.setFitHeight(cw/3);
            imageViewHover.setFitWidth(cw/3);
            imageViewHover.setFitHeight(cw/3);
            view.setAlignment(Pos.TOP_LEFT);
            
            btn.graphicProperty().bind(
                Bindings.when(
                        btn.hoverProperty()
                )
                        .then(imageView)
                        .otherwise(imageViewHover)
            );
        }
        
        private void SetupCell()
        {
            view.setPrefSize(cw, cw);
        }
        
        public VBox getView ()
        {
            return view;
        }
        
        int cw = 100;
        ImageView imageViewHover;
        ImageView imageView;
        Label label;
        int dayIndex;
        int numAppointments;
        List<Integer> listIds;
        String dayName;
        VBox view;
        Boolean hasAppointment = false;
        Boolean isHeader = false;
        Boolean isDayOfMonth = false;     
    }
}
