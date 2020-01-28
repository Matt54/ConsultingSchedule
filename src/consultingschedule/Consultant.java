/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author matthewp
 */
public class Consultant {
    ObservableList<Country> allCountries;
    ObservableList<City> allCities;
    ObservableList<Address> allAddresses;
    ObservableList<Customer> allCustomers;
    ObservableList<CustomerView> allCustomerViews;
    ObservableList<Appointment> allAppointments;
    
    int userId;
    
    Consultant(){
        allCountries = FXCollections.observableArrayList();
        allCities = FXCollections.observableArrayList();
        allAddresses = FXCollections.observableArrayList();
        allCustomers = FXCollections.observableArrayList();
        allCustomerViews = FXCollections.observableArrayList();
        allAppointments = FXCollections.observableArrayList();
    }
    
    public void setUserId(Integer id){
        userId = id;
    }
    
    public void PopulateFromDB(int id)
    {
        PopulateCountriesFromDB();
        PopulateCitiesFromDB();
        PopulateAddressesFromDB();
        PopulateCustomersFromDB();
        PopulateAppointmentsFromDB();
    }
    
    public void PopulateCustomersFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            if(rs.next())
            {
                addCustomer( extractCustomerData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private Customer extractCustomerData(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customerId") );
        customer.setName(rs.getString("customerName") );
        customer.setAddressId(rs.getInt("addressId") );
        return customer;
    }
    
    public void PopulateAddressesFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address");
            if(rs.next())
            {
                addAddress( extractAddressData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private Address extractAddressData(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getInt("addressId") );
        address.setAddress(rs.getString("address") );
        address.setAddress2(rs.getString("address2") );
        address.setCityId(rs.getInt("cityId") );
        address.setPostalCode(rs.getString("postalCode") );
        address.setPhone(rs.getString("phone") );
        return address;
    }
    
    public void PopulateCitiesFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city");
            if(rs.next())
            {
                addCity( extractCityData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private City extractCityData(ResultSet rs) throws SQLException {
        City city = new City();
        city.setCityId(rs.getInt("cityId") );
        city.setCityName(rs.getString("city") );
        city.setCountryId(rs.getInt("countryId") );
        return city;
    }
    
    public void PopulateCountriesFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM country");
            if(rs.next())
            {
                addCountry( extractCountryData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private Country extractCountryData(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setCountryId(rs.getInt("countryId") );
        country.setCountryName(rs.getString("country") );
        return country;
    }
    
    public void PopulateAppointmentsFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointment");
            if(rs.next())
            {
                addAppointment( extractAppointmentData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private Appointment extractAppointmentData(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        
        appointment.setAppointmentId(rs.getInt("appointmentId") );
        appointment.setCustomerId(rs.getInt("customerId") );
        appointment.setUserId(rs.getInt("userId") );
        appointment.setTitle(rs.getString("title") );
        appointment.setDescription(rs.getString("description") );
        appointment.setLocation(rs.getString("location") );
        appointment.setContact(rs.getString("contact") );
        appointment.setType(rs.getString("type") );
        appointment.setUrl(rs.getString("url") );
        appointment.setStartTime( new java.sql.Timestamp(rs.getTimestamp("start").getTime()).toLocalDateTime() );
        appointment.setEndTime( new java.sql.Timestamp(rs.getTimestamp("end").getTime()).toLocalDateTime() );

        return appointment;
    }
    
    
    public void addCountry(Country country){
        allCountries.add(country);
    }
    public Country lookupCountry(Integer countryId){
        for(Country country : allCountries){
            if(country.getCountryId().equals(countryId)) return country;
        }
        return null;
    }
    public void updateCountry(int index, Country selectedCountry){
        allCountries.set(index, selectedCountry);
    }
    public boolean deleteCountry(Country selectedCountry){
        if(allCountries.contains(selectedCountry)){
            allCountries.remove(selectedCountry);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<Country> getAllCountries(){
        return allCountries;
    }
    
    public void addCity(City city){
        allCities.add(city);
    }
    public City lookupCity(Integer cityId){
        for(City city : allCities){
            if(city.getCityId().equals(cityId)) return city;
        }
        return null;
    }
    public void updateCity(int index, City selectedCity){
        allCities.set(index, selectedCity);
    }
    public boolean deleteCity(City selectedCity){
        if(allCities.contains(selectedCity)){
            allCities.remove(selectedCity);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<City> getAllCities(){
        return allCities;
    }

    public void addAddress(Address address){
        allAddresses.add(address);
    }
    public Address lookupAddress(Integer addressId){
        for(Address address : allAddresses){
            if(address.getAddressId().equals(addressId)) return address;
        }
        return null;
    }
    public void updateAddress(int index, Address selectedAddress){
        allAddresses.set(index, selectedAddress);
    }
    public boolean deleteAddress(Address selectedAddress){
        if(allAddresses.contains(selectedAddress)){
            allAddresses.remove(selectedAddress);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<Address> getAllAddresses(){
        return allAddresses;
    }
    
    public void addCustomer(Customer customer){
        allCustomers.add(customer);
    }
    public Customer lookupCustomer(String customerName){
        for(Customer customer : allCustomers){
            if(customer.getCustomerName().equals(customerName)) return customer;
        }
        return null;
    }
    public Customer lookupCustomer(Integer customerId){
        for(Customer customer : allCustomers){
            if(customer.getCustomerId().equals(customerId)) return customer;
        }
        return null;
    }
    public void updateCustomer(int index, Customer selectedCustomer){
        allCustomers.set(index, selectedCustomer);
    }
    public boolean deleteCustomer(Customer selectedCustomer){
        if(allCustomers.contains(selectedCustomer)){
            allCustomers.remove(selectedCustomer);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }
    
    public void addAppointment(Appointment appointment){
        allAppointments.add(appointment);
    }
    public Appointment lookupAppointment(String appointmentTitle){
        for(Appointment appointment : allAppointments){
            if(appointment.getTitle().equals(appointmentTitle)) return appointment;
        }
        return null;
    }
    public void updateAppointment(int index, Appointment selectedAppointment){
        allAppointments.set(index, selectedAppointment);
    }
    
    public boolean deleteAppointment(Appointment selectedAppointment){
        if(allAppointments.contains(selectedAppointment)){
            allAppointments.remove(selectedAppointment);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }
    
    public ObservableList<AppointmentView> getAllAppointmentViews(){
        //Here we will convert the appointments to their views
        ObservableList<AppointmentView> allAppointmentViews = FXCollections.observableArrayList();
        
        for (Appointment apt : getAllAppointments()) 
        { 
            if(userId == apt.getUserId())
            {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

                AppointmentView aptView = new AppointmentView(apt.getTitle(),
                                                                lookupCustomer(apt.getCustomerId()).getCustomerName(),
                                                                apt.getLocation(),
                                                                apt.getType(),
                                                                dateFormat.format(Date.from( apt.getStartTime().atZone( ZoneId.systemDefault()).toInstant())), //"MM-dd-yyyy HH:mm"
                                                                dateFormat.format(Date.from( apt.getEndTime().atZone( ZoneId.systemDefault()).toInstant())));
                allAppointmentViews.add(aptView);
                PopulateCustomerView(apt.getCustomerId());
            }
        }
        return allAppointmentViews;
    }
    
    public CustomerView lookupCustomerView(String customerName){
        for(CustomerView customerView : allCustomerViews){
            if(customerView.getName().equals(customerName)) return customerView;
        }
        return null;
    }
    
    public void PopulateCustomerView(int customerId){
        Customer customer = lookupCustomer(customerId);
        
        CustomerView customerView = new CustomerView(customer.getCustomerName(),
                                                            lookupAddress( customer.getAddressId() ).getPhone(),
                                                            lookupAddress( customer.getAddressId() ).getAddress(),
                                                            lookupAddress( customer.getAddressId() ).getPostalCode(),
                                                            lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCityName(),
                                                            lookupCountry(lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCountryId()).getCountryName());
        allCustomerViews.add(customerView);
    }
            
    
    public ObservableList<CustomerView> getAllCustomerViews(){
        //Here we will convert the appointments to their views
        //ObservableList<CustomerView> allCustomerViews = FXCollections.observableArrayList();
        /*
        for (Customer customer : getAllCustomers()) 
        { 
            CustomerView customerView = new CustomerView(customer.getCustomerName(),
                                                            lookupAddress( customer.getAddressId() ).getPhone(),
                                                            lookupAddress( customer.getAddressId() ).getAddress(),
                                                            lookupAddress( customer.getAddressId() ).getPostalCode(),
                                                            lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCityName(),
                                                            lookupCountry(lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCountryId()).getCountryName());
            allCustomerViews.add(customerView);
        }
        */
        return allCustomerViews;
    }
    
}
