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
public class Customer {
    private Integer customerId;
    private String customerName;
    private Integer addressId;
    private boolean active;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    Customer () {}
    
    public Customer(Integer id,
                    String name,
                    Integer addId,
                    boolean isActive,
                    Date date,
                    String creater,
                    Timestamp time){
        customerId = id;
        customerName = name;
        addressId = addId;
        active = isActive;
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
        createdBy = creater;
        lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        lastUpdateBy = creater;
    }
    
    public Integer getUserId(){
        return customerId;
    }
    public String getUserName(){
        return customerName;
    }
    public Integer getAddressId(){
        return addressId;
    }
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
    
    public void setUserId(Integer id){
        customerId = id;
    }
    public void setUserName(String name){
        customerName = name;
    }
    public void setAddressId(Integer id){
        addressId = id;
    }
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
}
