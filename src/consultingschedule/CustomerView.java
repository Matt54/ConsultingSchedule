package consultingschedule;

public class CustomerView {
    protected String name;
    protected String phone;
    protected String address;
    protected String city;
    protected String zip;
    protected String country;
    
    public CustomerView(String _name,
                    String _phone,
                    String _address,
                    String _city,
                    String _zip,
                    String _country){
        name = _name;
        phone = _phone;
        address = _address;
        city = _city;
        zip = _zip;
        country = _country;
    }
    
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public String getAddress(){
        return address;
    }
    public String getCity(){
        return city;
    }
    public String getZip(){
        return zip;
    }
    public String getCountry(){
        return country;
    }
    
    public void setName(String input){
        name = input;
    }
    public void setPhone(String input){
        phone = input;
    }
    public void setAddress(String input){
        address = input;
    }
    public void setCity(String input){
        city = input;
    }
    public void setZip(String input){
        zip = input;
    }
    public void setCountry(String input){
        country = input;
    }
    
}
