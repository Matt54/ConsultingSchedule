package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public void setCountryName(String name){
        country = name;
    }
    
    public boolean addToDB()
    {
        Connection connection = connectToDB();
        try {
            int success = executeCountrySQLStatement(this,
                    connection,
                    "INSERT INTO country (country,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES ( ?, ?, ?, ?, ?)",
                    false);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeCountrySQLStatement(Country myCountry, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        //statement.setInt(1, myCountry.getCountryId());
        statement.setString(1, myCountry.getCountryName());
        statement.setDate(2, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(3, "Admin");
        statement.setTimestamp(4, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(5, "Admin" );
        if(isUpdate) statement.setInt(7, myCountry.getCountryId());
        int success = statement.executeUpdate();
        return success;
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
