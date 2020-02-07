package consultingschedule;

public class InformationGenerator {
    
    public InformationGenerator(){}

    public void DeleteAllConsultantCustomers(Consultant consultant){
        for(Customer customer : consultant.getAllCustomers())
        {
            consultant.deleteCustomer(customer);
        }
    }
}
