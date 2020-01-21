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
 * @author matthewp
 */

public class User {
    private Integer userId;
    private String userName;
    private String password;
    //private boolean active;
    //private LocalDate createDate;
    //private String createdBy;
    //private LocalDateTime lastUpdate;
    //private String lastUpdateBy;
    
    public User(){}
    
    public User(Integer id,
                String name,
                String pw//,
                //boolean isActive,
                //Date date,
                //String creater,
                //Timestamp time
                ){
        userId = id;
        userName = name;
        password = pw;
        //active = isActive;
        //createDate = new java.sql.Date(date.getTime()).toLocalDate();
        //createdBy = creater;
        //lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        //lastUpdateBy = creater;
    }
    
    public Integer getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    /*
    public boolean getStatus(){
        return active;
    }
    public LocalDate getDateAdded(){
        return createDate;
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
    */
    
    public void setUserId(Integer id){
        userId = id;
    }
    public void setUserName(String name){
        userName = name;
    }
    public void setPassword(String pw){
        password = pw;
    }
    /*
    public void setStatus(boolean isActive){
        active = isActive;
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
    */
    
}
