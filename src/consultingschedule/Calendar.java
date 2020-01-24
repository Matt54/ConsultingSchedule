/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author matthewp
 */
public class Calendar extends GridPane {
    
    int numColumns = 7;
    int numRows = 6;
    
    Cell[] cells = new Cell[numColumns * numRows];
    
    Calendar(){
        
        HBox[] rows = new HBox[numRows];
        
        for (int i = 0; i < 7; i++) {
            cells[1] = new Cell();
        }
        
    }
    

    
    
    public class Cell{
        
        Cell(){
            view = new VBox();
            SetupCell();
        }
        
        Cell(String dayName){
            isHeader = true;
            label = new Label(dayName);
            label.setId("big-label");
            view = new VBox(label);
            SetupCell();
            //view.setAlignment(Pos.CENTER);
        }
        
        Cell(int _dayIndex){
            isDayOfMonth = true;
            dayIndex = _dayIndex;
            
            label = new Label( Integer.toString(dayIndex) );
            view = new VBox(label);
            SetupCell();
            //view.setAlignment(Pos.TOP_LEFT);
        }

        Cell(int _dayIndex, int _numAppointments)
        {
            hasAppointment = true;
            dayIndex = _dayIndex;
            numAppointments = _numAppointments;
            Button btn = new Button(numAppointments + " Appointments");
            SetupCell();
        }
        
        private void SetupCell()
        {
            view.setPrefSize(100, 100);
        }
        
        public VBox getView ()
        {
            return view;
        }
        
        Label label;
        Button btn;
        
        int dayIndex;
        int numAppointments;
        String dayName;
        
        VBox view;
        Boolean hasAppointment = false;
        Boolean isHeader = false;
        Boolean isDayOfMonth = false;     
    }
}
