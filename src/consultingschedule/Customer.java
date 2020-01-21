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
    protected Integer customerId;
    protected String customerName;
    protected Integer addressId;
    /*
    protected boolean active;
    protected LocalDate createDate;
    protected String createdBy;
    protected LocalDateTime lastUpdate;
    protected String lastUpdateBy;
*/
    
    Customer () {}
    
    public Customer(Integer id,
                    String name,
                    Integer addId
            /*,
                    boolean isActive,
                    Date date,
                    String creater,
                    Timestamp time*/
                    ){
        customerId = id;
        customerName = name;
        addressId = addId;
        /*
        active = isActive;
        createDate = new java.sql.Date(date.getTime()).toLocalDate();
        createdBy = creater;
        lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
        lastUpdateBy = creater;
        */
    }
    
    public Integer getCustomerId(){
        return customerId;
    }
    public String getCustomerName(){
        return customerName;
    }
    public Integer getAddressId(){
        return addressId;
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
    
    public void setId(Integer id){
        customerId = id;
    }
    public void setName(String name){
        customerName = name;
    }
    public void setAddressId(Integer id){
        addressId = id;
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
