package consultingschedule;

public class ReportTypeView {
    protected String type;
    protected String monthAndYear;
    protected String numberOfAppointments;
    
    public ReportTypeView(String _type,
                    String _monthAndYear,
                    String _numberOfAppointments){
        type = _type;
        monthAndYear = _monthAndYear;
        numberOfAppointments = _numberOfAppointments;
    }
    
    public String getType(){
        return type;
    }
    
    public String getMonthAndYear(){
        return monthAndYear;
    }
    
    public String getNumberOfAppointments(){
        return numberOfAppointments;
    }
    
    public void setType(String input){
        type = input;
    }
    
    public void setMonthAndYear(String input){
        monthAndYear = input;
    }
    
    public void setNumberOfAppointments(String input){
        numberOfAppointments = input;
    }
    
}
