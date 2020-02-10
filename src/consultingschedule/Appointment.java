package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
                        LocalDateTime _start, 
                        LocalDateTime _end 
                        ){
                        
        appointmentId = appId;
        customerId = custId;
        userId = _userId;
        title = _title;
        description = desc;
        location = loc;
        contact = _contact;
        type = _type;
        url = _url;
        start = _start;
        end = _end;
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
    public String getContact(){
        return contact;
    }
    public String getUrl(){
        return url;
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
    public String getType(){
        return type;
    }
    public LocalDateTime getStartTime(){
        return start;
    }
    public LocalDateTime getEndTime(){
        return end;
    }

    public void setAppointmentId(Integer id){
        appointmentId = id;
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
    
    public void setType(String _type){
        type = _type;
    }
    public void setStartTime(LocalDateTime updateTime){
        start = updateTime;
    }
    public void setEndTime(LocalDateTime updateTime){
        end = updateTime;
    }
    public void setUserId(Integer id){
        userId = id;
    }
    public void setContact(String _contact){
        contact = _contact;
    }
    public void setUrl(String _url){
        url = _url;
    }
    
    public boolean addToDB()
    {
        Connection connection = connectToDB();
        try {
            int success = executeAppointmentSQLStatement(this,
                    connection,
                    "INSERT INTO appointment (customerId,userId,title,description,location,"
                            + "contact,type,url,start,end,"
                            + "createDate,createdBy,lastUpdate,lastUpdateBy)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    false);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean updateDB(){
        Connection connection = connectToDB();
        try {
            int success = executeAppointmentSQLStatement(this,
                    connection,
                    "UPDATE appointment SET customerId=?, userId=?, title=?, description=?, location=?,"
                            + " contact=?, type=?, url=?, start=?, end=?,"
                            + " createDate=?, createdBy=?, lastUpdate=?, lastUpdateBy=? WHERE appointmentId=?",
                    true);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeAppointmentSQLStatement(Appointment myAppointment, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, myAppointment.getCustomerId());
        statement.setInt(2, myAppointment.getUserId());
        statement.setString(3, myAppointment.getTitle());
        statement.setString(4, myAppointment.getDescription());
        statement.setString(5, myAppointment.getLocation());
        statement.setString(6, myAppointment.getContact());
        statement.setString(7, myAppointment.getType());
        statement.setString(8, myAppointment.getUrl());
        statement.setTimestamp(9, java.sql.Timestamp.valueOf( myAppointment.getStartTime() ) );
        statement.setTimestamp(10, java.sql.Timestamp.valueOf( myAppointment.getEndTime() ) );
        statement.setDate(11, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(12, "Admin");
        statement.setTimestamp(13, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(14, "Admin" );
        if(isUpdate) statement.setInt(15, myAppointment.getAppointmentId());
        int success = statement.executeUpdate();
        
        if(!isUpdate)
        {
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) myAppointment.setAppointmentId(rs.getInt(1));
        }
        
        return success;
    }
    
    public boolean deleteAppointmentFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            int success = stmt.executeUpdate("DELETE FROM appointment WHERE appointmentId=" + this.getAppointmentId());
          if(success == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}