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
    
    Consultant(){
       allCustomers = FXCollections.observableArrayList();
    }
    
    public void addCustomer(CustomerView customer){
        allCustomers.add(customer);
    }
    
    /*
    public Customer lookupCustomer(int customerId){
        for(Customer customer : allCustomers){
            if(customer.getCustomerId() == customerId) return customer;
        }
        return null;
    }
    */
    
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
    
}
