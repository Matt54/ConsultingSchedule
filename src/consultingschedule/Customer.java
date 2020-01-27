package consultingschedule;

public class Customer {
    protected Integer customerId;
    protected String customerName;
    protected Integer addressId;
    
    Customer () {}
    
    public Customer(Integer id,
                    String name,
                    Integer addId
                    ){
        customerId = id;
        customerName = name;
        addressId = addId;

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
    protected boolean active;
    protected LocalDate createDate;
    protected String createdBy;
    protected LocalDateTime lastUpdate;
    protected String lastUpdateBy;
    */
    
    /*
    active = isActive;
    createDate = new java.sql.Date(date.getTime()).toLocalDate();
    createdBy = creater;
    lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
    lastUpdateBy = creater;
    */

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
