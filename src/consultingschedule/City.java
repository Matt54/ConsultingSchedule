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
public class City {
    private Integer cityId;
    private String city;
    private Integer countryId;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    public City(){}
    
    public City(Integer id,
                    String name,
                    Integer _countryId,
                    Date date,
                    String creater,
                    Timestamp time){
        cityId = id;
        city = name;
        countryId = _countryId;
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
        createdBy = creater;
        lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        lastUpdateBy = creater;
    }
    
    public Integer getCityId(){
        return cityId;
    }
    public String getCityName(){
        return city;
    }
    public Integer getCountryId(){
        return countryId;
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
    
    public void setCityId(Integer id){
        cityId = id;
    }
    public void setUserName(String name){
        city = name;
    }
    public void setCountryId(Integer id){
        countryId = id;
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
