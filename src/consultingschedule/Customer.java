package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    public boolean addToDB()
    {
        Connection connection = connectToDB();
        try {
            int success = executeCustomerSQLStatement(this,
                    connection,
                    "INSERT INTO customer (customerName,addressId,active,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?)",
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
            int success = executeCustomerSQLStatement(this,
                    connection,
                    "UPDATE customer SET customerName=?, addressId=?, active=?,createDate=?,createdBy=?,lastUpdate=?, lastUpdateBy=? WHERE customerId=?",
                    true);
          if(success == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public int executeCustomerSQLStatement(Customer myCustomer, Connection connection, String sqlStatement, boolean isUpdate) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        //statement.setInt(1, myCountry.getCountryId());
        statement.setString(1, myCustomer.getCustomerName());
        statement.setInt(2, myCustomer.getAddressId());
        statement.setBoolean(3, true);
        statement.setDate(4, java.sql.Date.valueOf( LocalDate.now() ) );
        statement.setString(5, "Admin");
        statement.setTimestamp(6, java.sql.Timestamp.valueOf( LocalDateTime.now() ) );
        statement.setString(7, "Admin" );
        if(isUpdate) statement.setInt(8, myCustomer.getCustomerId());
        int success = statement.executeUpdate();
        
        if(!isUpdate)
        {
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) myCustomer.setId(rs.getInt(1));
        }

        return success;
    }
    
    public boolean deleteCustomerFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            int success = stmt.executeUpdate("DELETE FROM customer WHERE customerId=" + this.getCustomerId());
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
    

