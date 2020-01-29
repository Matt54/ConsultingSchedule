package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Address {
    private Integer addressId;
    private String address;
    private String address2;
    private Integer cityId;
    private String postalCode;
    private String phone;
    
    public Address(){}
    
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
    public String getAddress1(){
        return address;
    }
    public String getAddress2(){
        return address2;
    }
    
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
    
    public boolean addToDB()
    {
        Connection connection = connectToDB();
        try {
            int success = executeCountrySQLStatement(this,
                    connection,
                    "INSERT INTO address (address,address2,cityId,postalCode,phone,"
                            + "createDate,createdBy,lastUpdate,lastUpdateBy) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
            int success = executeCountrySQLStatement(this,
                    connection,
                    "UPDATE user SET address=?, address2=?, cityId=?, postalCode=?, phone=?, "
                            + "createDate=?,createdBy=?,lastUpdate=?, lastUpdateBy=? WHERE addressId=?",
                    true);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeCountrySQLStatement(Address myAddress, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, myAddress.getAddress1());
        statement.setString(2, myAddress.getAddress2());
        statement.setInt(3, myAddress.getCityId());
        statement.setString(4, myAddress.getPostalCode());
        statement.setString(5, myAddress.getPhone());
        statement.setDate(6, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(7, "Admin");
        statement.setTimestamp(8, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(9, "Admin" );
        if(isUpdate) statement.setInt(10, myAddress.getAddressId());
        int success = statement.executeUpdate();
        
        if(!isUpdate)
        {
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) myAddress.setAddressId(rs.getInt(1));
        }
        
        return success;
    }
    
    public boolean deleteAddressFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            int success = stmt.executeUpdate("DELETE FROM address WHERE addressId=" + this.getAddressId());
          if(success == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
    
    /*
    public String getAddress2(){
        return address2;
    }
    */
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

