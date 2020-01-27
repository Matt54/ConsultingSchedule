package consultingschedule;

public class Country {
    private Integer countryId;
    private String country;

    public Country(){}
    
    public Country(Integer id,
                    String name){
        countryId = id;
        country = name;
    }
    
    public Integer getCountryId(){
        return countryId;
    }
    public String getCountryName(){
        return country;
    }
    
    public void setCountryId(Integer id){
        countryId = id;
    }
    public void setUserName(String name){
        country = name;
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
