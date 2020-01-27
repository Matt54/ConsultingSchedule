package consultingschedule;

public class Address {
    private Integer addressId;
    private String address;
    private String address2;
    private Integer cityId;
    private String postalCode;
    private String phone;
    
    public Address(Integer id,
                    String add,
                    String add2,
                    Integer _cityId,
                    String code,
                    String _phone){
        addressId = id;
        address = add;
        address2 = add2;
        cityId = _cityId;
        postalCode = code;
        phone = _phone;
    }
    
    public Integer getAddressId(){
        return addressId;
    }
    public String getAddress(){
        return address + address2;
    }
    /*
    public String getAddress2(){
        return address2;
    }
    */
    
    public Integer getCityId(){
        return cityId;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public String getPhone(){
        return phone;
    }

    public void setAddressId(Integer id){
        addressId = id;
    }
    public void setAddress(String addr){
        address = addr;
    }
    public void setAddress2(String addr){
        address2 = addr;
    }
    public void setCityId(Integer id){
        cityId = id;
    }
    public void setPostalCode(String code){
        postalCode = code;
    }
    public void setPhone(String _phone){
        phone = _phone;
    }
    
    /*
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    */
    
    /*
    createDate = new java.sql.Date(date.getTime()).toLocalDate();
    createdBy = creater;
    lastUpdate = new java.sql.Timestamp(time.getTime()).toLocalDateTime();
    lastUpdateBy = creater;
    */
    
    /*
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
