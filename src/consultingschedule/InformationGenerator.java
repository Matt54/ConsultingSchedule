package consultingschedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class InformationGenerator {
    
    public InformationGenerator(Consultant _consultant)
    {
        consultant = _consultant;
    }
    
    //All customers and appointments for a consultant will be deleted
    public void DeleteAllConsultantCustomers(){
        
        while(consultant.getAllCustomers().size() > 0)
        {
            boolean found = false;
            Customer delete = new Customer();
            for(Customer customer : consultant.getAllCustomers())
            {
                found = true;
                delete = customer;
            }
            if(found) consultant.deleteCustomer(delete);
        }
    }
    
    public void CreateRandomCustomers(int numberOfCustomers, String[][] array){
        
        for (int i = 0; i < numberOfCustomers; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(100);
            String firstName = array[randomInt+2][0];
            
            randomInt = random.nextInt(100);
            String lastName = array[randomInt+2][1].toLowerCase();
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            
            randomInt = random.nextInt(100);
            String address = rDigit() + "" + rDigit() + "" + rDigit() + " " + array[randomInt+2][2];
            
            randomInt = random.nextInt(100);
            String city = array[randomInt+2][3];
            
            String phone = rDigit() + "" + rDigit() + "" + rDigit() + "-" +
                    rDigit() + "" + rDigit() + "" + rDigit() + "-" +
                    rDigit() + "" + rDigit() + "" + rDigit() + "" + rDigit();
            
            String zip = rDigit() + "" + rDigit() + "" + rDigit() + "" +
                    rDigit() + "" + rDigit();
            
            System.out.println("first name = " + firstName + " , last name = " + lastName + " , address = " + address + " , city = " + city);
            
            CreateNewCustomer(firstName,lastName,address,city,phone,zip);
        }
    }
    
    //Used to generate a single digit random number starting at 1 (range 1 - 9)
    public int rDigit(){
        Random random = new Random();
        return (random.nextInt(8)+1);
    }
    
    public void CreateNewCustomer(String firstName, String lastName, String address, String city, String phone, String zip)
    {
        Customer newCustomer = new Customer();
        Address newAddress = new Address();
        City newCity = new City();
        Country newCountry = new Country();
        
        newCustomer.setName(firstName + " " + lastName);
        newAddress.setPhone(phone);
        newAddress.setAddress(address);
        newAddress.setAddress2("");
        newCity.setCityName(city);
        newAddress.setPostalCode(zip);
        newCountry.setCountryName("USA");

        newCountry.addToDB();
        newCity.setCountryId(newCountry.getCountryId());

        newCity.addToDB();
        newAddress.setCityId(newCity.getCityId());

        newAddress.addToDB();
        newCustomer.setAddressId(newAddress.getAddressId());

        newCustomer.addToDB();

        consultant.addCountry(newCountry);
        consultant.addCity(newCity);
        consultant.addAddress(newAddress);
        consultant.addCustomer(newCustomer);
        consultant.AddCustomerToView(newCustomer);
        
    }
    
    public void CreateRandomAppointments(int numberOfAppointments, String[][] array){
        for (int i = 0; i < numberOfAppointments; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(100);
            String title = array[randomInt+2][0];
            randomInt = random.nextInt(100);
            String type = array[randomInt+2][1];
            
            int randomIndex = random.nextInt(consultant.getAllCustomers().size());
            Customer randomCustomer = consultant.getAllCustomers().get(randomIndex);
            
            randomInt = random.nextInt(4);
            String[] location = {"On-Site","Conference Room", "Board Room","Coffee Shop"};
            String myLocation = location[randomInt];
            
            String url = (randomCustomer.getCustomerName()+"@email.com").replaceAll("\\s+","");
            
            randomInt = random.nextInt(11);
            randomInt++;
            int month = randomInt;

            randomInt = random.nextInt(364);
            randomInt++;
            int dayOfYear = randomInt;

            randomInt = random.nextInt(2);
            int[] years = {2020,2021,2022};
            int year = years[randomInt];

            LocalDate localDate = LocalDate.ofYearDay(year, dayOfYear);

            //this prevents us from getting Saturday or Sunday
            int val = localDate.getDayOfWeek().getValue();
            if(val > 5){
                if(localDate.lengthOfMonth() > localDate.getDayOfMonth() + 2)
                {
                    localDate = LocalDate.ofYearDay(year, dayOfYear + 2);
                }
                else{
                    localDate = LocalDate.ofYearDay(year, dayOfYear - 2);
                }
            }

            randomInt = random.nextInt(9);
            randomInt += 8;
            int hour = randomInt;

            LocalTime localTimeStart = LocalTime.of(hour, 0);
            LocalTime localTimeEnd = LocalTime.of(hour+1, 0);

            LocalDateTime startTime = LocalDateTime.of(localDate,localTimeStart);
            LocalDateTime endTime = LocalDateTime.of(localDate,localTimeEnd);
            
            ZonedDateTime startZonedTime = startTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime startUTC = startZonedTime.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime startUTCLDT = startUTC.toLocalDateTime();

            ZonedDateTime endZonedTime = endTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime endUTC = endZonedTime.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime endUTCLDT = endUTC.toLocalDateTime();
            
            
            System.out.println("email = " + url + " location = " + myLocation + "title = " + title + " , type = " + type + " , customer = " + randomCustomer.getCustomerName());
            System.out.println("Year: " + startTime.getYear() + " , Month: " + startUTCLDT.getMonth().toString() + " , Day: " + startUTCLDT.getDayOfWeek().toString() + " , Time: " + startUTCLDT.getHour());
        
            Appointment newAppointment = new Appointment(1,
                                            randomCustomer.getCustomerId(),
                                            1,
                                            title,
                                            "Appointment with " + randomCustomer.getCustomerName(),
                                            myLocation,
                                            randomCustomer.getCustomerName(),
                                            type,
                                            url,
                                            startUTCLDT,
                                            endUTCLDT);
            
            newAppointment.addToDB();
            consultant.addAppointment(newAppointment);
            consultant.AddAppointmentToView(newAppointment);
        }
    }

    //Reads the appointment csv file and returns a two dimensional array containing
    //the appointment titles and types
    public String[][] ReadAppointmentCSVFile(){
        String csvFile = "GenerateAppointments.csv";
        String line = "";
        String cvsSplitBy = ",";

        String[][] array = new String[102][2];
        int counter = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            
            while ((line = br.readLine()) != null) {
                String[] someArray = line.split(cvsSplitBy);
                array[counter + 1][0] = someArray[0];
                array[counter + 1][1] = someArray[1];
                counter++;
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String[][] ReadCustomerCSVFile(){
        
        String csvFile = "GenerateCustomers.csv";
        String line = "";
        String cvsSplitBy = ",";

        String[][] array = new String[102][4];
        int counter = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            
            while ((line = br.readLine()) != null) {
                
                String[] someArray = line.split(cvsSplitBy);
                array[counter + 1][0] = someArray[0];
                array[counter + 1][1] = someArray[1];
                array[counter + 1][2] = someArray[2];
                array[counter + 1][3] = someArray[3];
                counter++;
                //System.out.println("first name = " + array[0] + " , last name = " + array[1] + " , address = " + array[2] + " , city = " + array[3]);
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    Consultant consultant;
}
