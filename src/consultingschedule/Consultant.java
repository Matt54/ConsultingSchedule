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
    ObservableList<Customer> allCustomers;
    
    Consultant(){
       allCustomers = FXCollections.observableArrayList();
    }
    
    public void addCustomer(Customer customer){
        allCustomers.add(customer);
    }
    
    public Customer lookupCustomer(int customerId){
        for(Customer customer : allCustomers){
            if(customer.getCustomerId() == customerId) return customer;
        }
        return null;
    }
    
    public Customer lookupCustomer(String customerName){
        for(Customer customer : allCustomers){
            if(customer.getCustomerName().equals(customerName)) return customer;
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
    
}
