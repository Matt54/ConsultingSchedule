/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.time.LocalDateTime;

/**
 *
 * @author macbook
 */
public class AppointmentView {
    protected String start;
    protected String end;
    protected String location;
    protected String title;
    protected String type;
    protected String customerName;
    protected int appointmentId;
    
    public AppointmentView(
                        String _title,
                        String cust,
                        String loc,
                        String _type,
                        String _start, 
                        String _end ){
        start = _start;
        end = _end;
        title = _title;
        customerName = cust;
        location = loc;
        type = _type;
    }
    
    public String getTitle(){
        return title;
    }
    public String getCustomerName(){
        return customerName;
    }
    public String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
    public String getStart(){
        return start;
    }
    public String getEnd(){
        return end;
    }
    public Integer getAppointmentId(){
        return appointmentId;
    }
    
    public void setTitle(String _title){
        title = _title;
    }
    public void setCustomer(String _name){
        customerName = _name;
    }
    public void setLocation(String loc){
        location = loc;
    }
    public void setType(String _type){
        type = _type;
    }
    public void setStartTime(String updateTime){
        start = updateTime;
    }
    public void setEndTime(String updateTime){
        end = updateTime;
    }
    public void setAppointmentId(Integer id){
        appointmentId = id;
    }
    
}
