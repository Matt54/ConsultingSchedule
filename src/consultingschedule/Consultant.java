package consultingschedule;

import static consultingschedule.ConsultingSchedule.UTCtoLDT;
import static consultingschedule.ConsultingSchedule.connectToDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Consultant {
    ObservableList<Country> allCountries;
    ObservableList<City> allCities;
    ObservableList<Address> allAddresses;
    ObservableList<Customer> allCustomers;
    ObservableList<CustomerView> allCustomerViews;
    ObservableList<Appointment> allAppointments;
    ObservableList<AppointmentView> allAppointmentViews;
    ObservableList<ReportTypeView> allReportTypeViews;
    ObservableList<User> allUsers;
    int userId;
    
    Consultant(){
        allCountries = FXCollections.observableArrayList();
        allCities = FXCollections.observableArrayList();
        allAddresses = FXCollections.observableArrayList();
        allCustomers = FXCollections.observableArrayList();
        allCustomerViews = FXCollections.observableArrayList();
        allAppointments = FXCollections.observableArrayList();
        allAppointmentViews = FXCollections.observableArrayList();
        allReportTypeViews = FXCollections.observableArrayList();
        allUsers = FXCollections.observableArrayList();
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
        PopulateUsersFromDB();
    }
    
    public void PopulateUsersFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            while(rs.next())
            {
                AddUser( ExtractUserData(rs) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private User ExtractUserData(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId") );
        user.setUserName(rs.getString("userName") );
        user.setPassword(rs.getString("password") );
        return user;
    }
    
    public void AddUser(User user){
        allUsers.add(user);
    }
    
    public void PopulateCustomersFromDB() {
        Connection connection = connectToDB();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            while(rs.next())
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
            while(rs.next())
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
            while(rs.next())
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
            while(rs.next())
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
            while(rs.next())
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
    
    public Country lookupCountry(String countryName){
        for(Country country : allCountries){
            if(country.getCountryName().equals(countryName)) return country;
        }
        return null;
    }
    
    public void updateCountry(int index, Country selectedCountry){
        allCountries.set(index, selectedCountry);
    }
    
    public boolean deleteCountry(Country selectedCountry){
        if(allCountries.contains(selectedCountry)){
            selectedCountry.deleteCountryFromDB();
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
    
    public void SweepForUnusedCountries(){
        Country deleteThisCountry = null;
        for(Country country : allCountries)
        {
            boolean found = false;
            for(City city : allCities){
                if(country.getCountryId() == city.getCountryId()) found = true;
            }
            if(!found) deleteThisCountry = country;
        }
        if(deleteThisCountry != null) deleteCountry(deleteThisCountry);
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
    
    public City lookupCity(String cityName){
        for(City city : allCities){
            if(city.getCityName().equals(cityName)) return city;
        }
        return null;
    }
    
    public void updateCity(int index, City selectedCity){
        allCities.set(index, selectedCity);
    }
    
    public boolean deleteCity(City selectedCity){
        if(allCities.contains(selectedCity)){
            selectedCity.deleteCityFromDB();
            allCities.remove(selectedCity);
            SweepForUnusedCountries();
            return true;
        }
        else{
            return false;
        }
    }
    
    public ObservableList<City> getAllCities(){
        return allCities;
    }
    
    public void SweepForUnusedCities(){
        City deleteThisCity = null;
        for(City city : allCities)
        {
            boolean found = false;
            for(Address address : allAddresses){
                if(Objects.equals(city.getCityId(), address.getCityId())) found = true;
            }
            if(!found) deleteThisCity = city;
        }
        if(deleteThisCity != null) deleteCity(deleteThisCity);
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
    
    public Address lookupAddress(String addressString){
        for(Address address : allAddresses){
            if(address.getAddress1().equals(addressString)) return address;
        }
        return null;
    }
    
    public void updateAddress(int index, Address selectedAddress){
        allAddresses.set(index, selectedAddress);
    }
    
    public boolean deleteAddress(Address selectedAddress){
        if(allAddresses.contains(selectedAddress)){
            selectedAddress.deleteAddressFromDB();
            allAddresses.remove(selectedAddress);
            SweepForUnusedCities();
            return true;
        }
        else{
            return false;
        }
    }
    
    public ObservableList<Address> getAllAddresses(){
        return allAddresses;
    }
    
    public void SweepForUnusedAddresses(){
        Address deleteThisAddress = null;
        for(Address address : allAddresses)
        {
            boolean found = false;
            for(Customer customer : allCustomers){
                if(Objects.equals(address.getAddressId(), customer.getAddressId())) found = true;
            }
            if(!found) deleteThisAddress = address;
        }
        if(deleteThisAddress != null) deleteAddress(deleteThisAddress);
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
            deleteAppointmentsForCustomerId(selectedCustomer.getCustomerId());
            selectedCustomer.deleteCustomerFromDB();
            allCustomers.remove(selectedCustomer);
            deleteCustomerView(selectedCustomer.getCustomerName());
            SweepForUnusedAddresses(); 
            return true;
        }
        else{
            return false;
        }
    }
    
    public void deleteCustomerView(String name){
        CustomerView deleteThisCustomerView = null;
        for(CustomerView cv : allCustomerViews){
            if(cv.getName().equals(name)) deleteThisCustomerView = cv;
        }
        if(deleteThisCustomerView != null) allCustomerViews.remove(deleteThisCustomerView);
    }
    
    public void deleteCustomerView(int id){
        CustomerView deleteThisCustomerView = null;
        for(CustomerView cv : allCustomerViews){
            if(cv.getCustomerId().equals(id)) deleteThisCustomerView = cv;
        }
        if(deleteThisCustomerView != null) allCustomerViews.remove(deleteThisCustomerView);
    }
    
    public void deleteCustomerView(CustomerView cv){
        if(allCustomerViews.contains(cv)) allCustomerViews.remove(cv);
    }
    
    public ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }
    
    //need another method to search for any appointment with constomer id to delete
    //that other method will run over and over until it loops without finding any appointments to delete
    public boolean searchAndDestoryAppointment(int id)
    {
        Appointment deleteThisAppointment = null;
        for(Appointment appointment : allAppointments){
            if(appointment.getCustomerId() == id) deleteThisAppointment = appointment;
        }
        if(deleteThisAppointment != null)
        {
            deleteAppointment(deleteThisAppointment);
            return true;
        }
        return false;
    }
    
    public void deleteAppointmentsForCustomerId(int id){
        int numAppointmentsRemoved = 0;
        while( searchAndDestoryAppointment(id) ) numAppointmentsRemoved++;
        
        if(numAppointmentsRemoved > 0)
        {
            Alert appointmentInformation = new Alert(Alert.AlertType.INFORMATION);
            appointmentInformation.setTitle("Removed Appointments");
            appointmentInformation.setHeaderText("Appointments associated with the customer were removed.");
            appointmentInformation.setContentText(numAppointmentsRemoved + " appointment(s) have been deleted.");
            appointmentInformation.showAndWait();
        }
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
    
    public Appointment lookupAppointment(Integer appointmentId){
        for(Appointment appointment : allAppointments){
            if(appointment.getAppointmentId().equals(appointmentId)) return appointment;
        }
        return null;
    }
    
    public void AddAppointmentToView(Appointment apt){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        LocalDateTime startLdt = UTCtoLDT(apt.getStartTime());
        LocalDateTime endLdt = UTCtoLDT(apt.getEndTime());

        AppointmentView aptView = new AppointmentView(apt.getTitle(),
                                                        lookupCustomer(apt.getCustomerId()).getCustomerName(),
                                                        apt.getLocation(),
                                                        apt.getType(),
                                                        dateFormat.format(Date.from( startLdt.atZone( ZoneId.systemDefault()).toInstant())), //"MM-dd-yyyy HH:mm"
                                                        dateFormat.format(Date.from( endLdt.atZone( ZoneId.systemDefault()).toInstant())));
        allAppointmentViews.add(aptView);
    }
    
    public void updateAppointment(int appointmentId, Appointment selectedAppointment){
        
        for(Appointment appointment : allAppointments){
            if(appointment.getAppointmentId().equals(appointmentId)) appointment = selectedAppointment;
        }
        
    }
    
    public boolean deleteAppointment(Appointment selectedAppointment){
        if(allAppointments.contains(selectedAppointment)){
            selectedAppointment.deleteAppointmentFromDB();
            allAppointments.remove(selectedAppointment);
            deleteAppointmentView(selectedAppointment.getAppointmentId());
            return true;
        }
        else{
            return false;
        }
    }
    
    public void deleteAppointmentView(Integer id){
        AppointmentView deleteThisAppointmentView = null;
        for(AppointmentView av : allAppointmentViews){
            if(av.getAppointmentId().equals(id)) deleteThisAppointmentView = av;
        }
        if(deleteThisAppointmentView != null) allAppointmentViews.remove(deleteThisAppointmentView);
    }
    
    public ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }
    
    public ObservableList<AppointmentView> getAllAppointmentViews(){
        //Here we will convert the appointments to their views
        //ObservableList<AppointmentView> allAppointmentViews = FXCollections.observableArrayList();
        
        for (Appointment apt : getAllAppointments()) 
        { 
            if(userId == apt.getUserId())
            {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

                LocalDateTime startLdt = UTCtoLDT(apt.getStartTime());
                LocalDateTime endLdt = UTCtoLDT(apt.getEndTime());
                

                AppointmentView aptView = new AppointmentView(apt.getTitle(),
                                                                lookupCustomer(apt.getCustomerId()).getCustomerName(),
                                                                apt.getLocation(),
                                                                apt.getType(),
                                                                dateFormat.format(Date.from( startLdt.atZone( ZoneId.systemDefault()).toInstant())), //"MM-dd-yyyy HH:mm"
                                                                dateFormat.format(Date.from( endLdt.atZone( ZoneId.systemDefault()).toInstant())));
                aptView.setAppointmentId(apt.getAppointmentId());
                allAppointmentViews.add(aptView);
            }
        }
        return allAppointmentViews;
    }
    
    public AppointmentView lookupAppointmentView(String appointmentTitle){
        for(AppointmentView appointmentView : allAppointmentViews){
            if(appointmentView.getTitle().equals(appointmentTitle)) return appointmentView;
        }
        return null;
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
                                                            lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCityName(),
                                                            lookupAddress( customer.getAddressId() ).getPostalCode(),
                                                            lookupCountry(lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCountryId()).getCountryName());
        customerView.setCustomerId(customer.getCustomerId());
        allCustomerViews.add(customerView);
    }
    
    public void AddCustomerToView(Customer customer){
        CustomerView customerView = new CustomerView(customer.getCustomerName(),
                                                            lookupAddress( customer.getAddressId() ).getPhone(),
                                                            lookupAddress( customer.getAddressId() ).getAddress(),
                                                            lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCityName(),
                                                            lookupAddress( customer.getAddressId() ).getPostalCode(),
                                                            lookupCountry(lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCountryId()).getCountryName());
        customerView.setCustomerId(customer.getCustomerId());
        allCustomerViews.add(customerView);
    }

    public ObservableList<CustomerView> getAllCustomerViews(){
        //Here we will convert the appointments to their views
        //ObservableList<CustomerView> allCustomerViews = FXCollections.observableArrayList();
        
        for (Customer customer : getAllCustomers()) 
        { 
            CustomerView customerView = new CustomerView(customer.getCustomerName(),
                                                            lookupAddress( customer.getAddressId() ).getPhone(),
                                                            lookupAddress( customer.getAddressId() ).getAddress(),
                                                            lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCityName(),
                                                            lookupAddress( customer.getAddressId() ).getPostalCode(),
                                                            lookupCountry(lookupCity(lookupAddress( customer.getAddressId() ).getCityId()).getCountryId()).getCountryName());
            customerView.setCustomerId(customer.getCustomerId());
            allCustomerViews.add(customerView);
        }
        
        return allCustomerViews;
    }
    
    public void populateReportTypeViews(){

        
        List<String> appointmentTypes = new ArrayList<>();
        
        //Determine how many appointment types there are
        for (Appointment appointment : getAllAppointments()) 
        { 
            if(!appointmentTypes.contains(appointment.getType())) appointmentTypes.add(appointment.getType());
        }

        //Determine how many dates there are for each appointment and count the appointments at each date
        for(String type : appointmentTypes)
        {
            List<String> dates = new ArrayList<>();
            List<Integer> numberOfTypeAtDates = new ArrayList<>();
            
            for (Appointment appointment : getAllAppointments()) 
            { 
                if(appointment.getType().equals(type))
                {
                    String month = appointment.getStartTime().getMonth().toString();
                    String year = Integer.toString(appointment.getStartTime().getYear());
                    String date = month + " , " + year;
                    if(!dates.contains(date)) 
                    {
                        dates.add(date);
                        numberOfTypeAtDates.add(1);
                    }
                    else
                    {
                        numberOfTypeAtDates.set(dates.indexOf(date), 
                        numberOfTypeAtDates.get(dates.indexOf(date)) + 1);
                    }
                }
            }
            
            for (int i = 0; i < dates.size(); i++) {
                ReportTypeView reportTypeView = new ReportTypeView(type,dates.get(i),Integer.toString(numberOfTypeAtDates.get(i)));
                allReportTypeViews.add(reportTypeView);
            }
        }
    }
    
    public ObservableList<ReportTypeView> getAllReportTypeViews(){
        return allReportTypeViews;
    }
    
    public String GetConsultantReport(){
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm a");
        String consultantReport = "Consultant Schedule Report";

        for(User user : allUsers)
        {
            consultantReport += System.getProperty("line.separator");
            consultantReport += System.getProperty("line.separator");
            consultantReport += "Upcoming Appointments for " + user.getUserName() + ":";
            consultantReport += System.getProperty("line.separator");

            Collections.sort(allAppointments, (Appointment m1, Appointment m2) -> m1.getStartTime().compareTo(m2.getStartTime())); // 1 line llamda expression vs 4 lines with anonymous inner class creation
            
            for (Appointment apt : getAllAppointments()) 
            { 
                if( user.getUserId().equals(apt.getUserId()) )
                {
                    
                    LocalDateTime startLdt = UTCtoLDT(apt.getStartTime());
                    
                    consultantReport += System.getProperty("line.separator");
                    consultantReport += dateFormat.format(Date.from( startLdt.atZone( ZoneId.systemDefault()).toInstant())) + ": " 
                            + System.getProperty("line.separator") + apt.getTitle() + " (" + apt.getType() + ") "
                            + " with " + lookupCustomer(apt.getCustomerId()).getCustomerName()
                            + " at " + apt.getLocation();  
                    consultantReport += System.getProperty("line.separator");
                }
            }
        }
        
        return consultantReport;
    }
    
    public String GetHoursReport(){
        String hoursReport = "Number of Hours Scheduled by Consultant";
        
        int hoursTotal = 0;
        int minutesTotal = 0;
        
        for(User user : allUsers)
        {
            for (Appointment apt : getAllAppointments()) 
            { 
                int hours = apt.getEndTime().getHour() - apt.getStartTime().getHour();
                int minutes = apt.getEndTime().getMinute() - apt.getStartTime().getMinute();
                if(minutes < 0)
                {
                    hours -= 1;
                    minutes = 60 - minutes;
                }
                hoursTotal += hours;
                minutesTotal += minutes;
                if(minutes > 60)
                {
                    hoursTotal++;
                    minutesTotal = 0;
                }
            }
            hoursReport += System.getProperty("line.separator");
            hoursReport += System.getProperty("line.separator");
            hoursReport += user.getUserName() + " total time: "
                    + hoursTotal + " hours " + minutesTotal + " minutes.";
            
        }
        return hoursReport;
    }
    
}
