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

/**
 *
 * @author macbook
 */
public class Appointment {
    private Integer appointmentId;
    private Integer customerId;
    private Integer userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    Appointment(){}
    
    public Appointment(Integer appId,
                        Integer custId,
                        Integer _userId,
                        String _title,
                        String desc,
                        String loc,
                        String _contact,
                        String _type,
                        String _url,
                        Date _start,
                        Date _end,
                        Date date,
                        String creater,
                        Timestamp time){
        appointmentId = appId;
        customerId = custId;
        userId = _userId;
        title = _title;
        description = desc;
        location = loc;
        contact = _contact;
        type = _type;
        url = _url;
        start = new java.sql.Timestamp(_start.getTime()).toLocalDateTime();
        end = new java.sql.Timestamp(_end.getTime()).toLocalDateTime();
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
        createdBy = creater;
        lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        lastUpdateBy = creater;
    }
    
    public Integer getAppointmentId(){
        return appointmentId;
    }
    public Integer getCustomerId(){
        return customerId;
    }
    public Integer getUserId(){
        return userId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public String getContact(){
        return contact;
    }
    public String getType(){
        return type;
    }
    public String getUrl(){
        return url;
    }
    public LocalDate getDateAdded(){
        return createDate;
    }
    public LocalDateTime getStartTime(){
        return start;
    }
    public LocalDateTime getEndTime(){
        return end;
    }
    public String getCreator(){
        return createdBy;
    }
    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }
    
    public void setAppointId(Integer id){
        appointmentId = id;
    }
    public void setUserId(Integer id){
        userId = id;
    }
    public void setCustomerId(Integer id){
        customerId = id;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public void setDescription(String desc){
        description = desc;
    }
    public void setLocation(String loc){
        location = loc;
    }
    public void setContact(String _contact){
        contact = _contact;
    }
    public void setType(String _type){
        type = _type;
    }
    public void setUrl(String _url){
        url = _url;
    }
    public void setStartTime(Timestamp updateTime){
        lastUpdate = new java.sql.Timestamp(updateTime.getTime()).toLocalDateTime();
    }
    public void setEndTime(Timestamp updateTime){
        lastUpdate = new java.sql.Timestamp(updateTime.getTime()).toLocalDateTime();
    }
    public void setDateAdded(Date date){
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
    }
    public void setCreator(String creator){
        createdBy = creator;
    }
    public void setUpdateTime(Timestamp updateTime){
        lastUpdate = new java.sql.Timestamp(updateTime.getTime()).toLocalDateTime();
    }
    public void setUpdatedBy(String updatedBy){
        lastUpdateBy = updatedBy;
    }
    
}
