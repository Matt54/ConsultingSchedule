/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    Consultant(){
        allCountries = FXCollections.observableArrayList();
        allCities = FXCollections.observableArrayList();
        allAddresses = FXCollections.observableArrayList();
        allCustomers = FXCollections.observableArrayList();
        allCustomerViews = FXCollections.observableArrayList();
        allAppointments = FXCollections.observableArrayList();
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
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            
            AppointmentView aptView = new AppointmentView(apt.getTitle(),
                                                            "Matt",
                                                            apt.getLocation(),
                                                            apt.getType(),
                                                            dateFormat.format(Date.from( apt.getStartTime().atZone( ZoneId.systemDefault()).toInstant())), //"MM-dd-yyyy HH:mm"
                                                            dateFormat.format(Date.from( apt.getEndTime().atZone( ZoneId.systemDefault()).toInstant())));
            allAppointmentViews.add(aptView);
        }
        return allAppointmentViews;
    }
    
    public CustomerView lookupCustomerView(String customerName){
        for(CustomerView customerView : allCustomerViews){
            if(customerView.getName().equals(customerName)) return customerView;
        }
        return null;
    }
    
    public ObservableList<CustomerView> getAllCustomerViews(){
        //Here we will convert the appointments to their views
        //ObservableList<CustomerView> allCustomerViews = FXCollections.observableArrayList();
        
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
        return allCustomerViews;
    }
    
}
