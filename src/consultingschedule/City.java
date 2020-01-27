package consultingschedule;

public class City {
    private Integer cityId;
    private String city;
    private Integer countryId;

    public City(){}
    
    public City(Integer id,
                    String name,
                    Integer _countryId){
        cityId = id;
        city = name;
        countryId = _countryId;
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

    public void setCityId(Integer id){
        cityId = id;
    }
    public void setUserName(String name){
        city = name;
    }
    public void setCountryId(Integer id){
        countryId = id;
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
