/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultingschedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author matthewp
 */
public class Consultant {
    ObservableList<CustomerView> allCustomers;
    ObservableList<AppointmentView> allAppointments;
    
    Consultant(){
       allCustomers = FXCollections.observableArrayList();
       allAppointments = FXCollections.observableArrayList();
    }
    
    public void addCustomer(CustomerView customer){
        allCustomers.add(customer);
    }
    public CustomerView lookupCustomer(String customerName){
        for(CustomerView customer : allCustomers){
            if(customer.getName().equals(customerName)) return customer;
        }
        return null;
    }
    public void updateCustomer(int index, CustomerView selectedCustomer){
        allCustomers.set(index, selectedCustomer);
    }
    
    public boolean deleteCustomer(CustomerView selectedCustomer){
        if(allCustomers.contains(selectedCustomer)){
            allCustomers.remove(selectedCustomer);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<CustomerView> getAllCustomers(){
        return allCustomers;
    }
    
    public void addAppointment(AppointmentView appointment){
        allAppointments.add(appointment);
    }
    public AppointmentView lookupAppointment(String appointmentTitle){
        for(AppointmentView appointment : allAppointments){
            if(appointment.getTitle().equals(appointmentTitle)) return appointment;
        }
        return null;
    }
    public void updateAppointment(int index, AppointmentView selectedAppointment){
        allAppointments.set(index, selectedAppointment);
    }
    
    public boolean deleteAppointment(AppointmentView selectedAppointment){
        if(allAppointments.contains(selectedAppointment)){
            allAppointments.remove(selectedAppointment);
            return true;
        }
        else{
            return false;
        }
    }
    public ObservableList<AppointmentView> getAllAppointments(){
        return allAppointments;
    }
    
}
