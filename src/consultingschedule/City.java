package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public void setCityName(String name){
        city = name;
    }
    public void setCountryId(Integer id){
        countryId = id;
    }

    public boolean addToDB()
    {
        Connection connection = connectToDB();
        try {
            int success = executeCitySQLStatement(this,
                    connection,
                    "INSERT INTO city (city,countryId,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?)",
                    false);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean updateDB(){
        Connection connection = connectToDB();
        try {
            int success = executeCitySQLStatement(this,
                    connection,
                    "UPDATE city SET city=?, countryId=?, createDate=?, createdBy=?, lastUpdate=?, lastUpdateBy=? WHERE cityId=?",
                    true);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeCitySQLStatement(City myCity, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        //statement.setInt(1, myCountry.getCountryId());
        statement.setString(1, myCity.getCityName());
        statement.setInt(2, myCity.getCountryId());
        statement.setDate(3, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(4, "Admin");
        statement.setTimestamp(5, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(6, "Admin" );
        if(isUpdate) statement.setInt(7, myCity.getCityId());
        int success = statement.executeUpdate();
        
        if(!isUpdate)
        {
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) myCity.setCityId(rs.getInt(1));
        }
        
        return success;
    }
    
    public boolean deleteCityFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            int success = stmt.executeUpdate("DELETE FROM city WHERE cityId=" + this.getCityId());
          if(success == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}