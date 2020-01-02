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
public class Country {
    private Integer countryId;
    private String country;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    public Country(){}
    
    public Country(Integer id,
                    String name,
                    Date date,
                    String creater,
                    Timestamp time){
        countryId = id;
        country = name;
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
        createdBy = creater;
        lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        lastUpdateBy = creater;
    }
    
    public Integer getCountryId(){
        return countryId;
    }
    public String getCountryName(){
        return country;
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
    
    public void setCountryId(Integer id){
        countryId = id;
    }
    public void setUserName(String name){
        country = name;
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
