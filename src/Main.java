import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;
import java.io.*;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class Main {
    //Defining the logger object
    static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        //An HttpRequest obj together with its method to GET response from the API

        //Log debug
        log.debug("Sending a GET request to the fleetio API");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles?q[license_plate_eq]=GW-1456-22"))
                .header("Authorization", "Token 1cbfa399842a1baee03b046cb72e49c64c9df401")
                .header("Account-Token", "68c3339b75")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        //Get a Log information if the request is successful
        log.info("Request was successful");

        //response obj to receive the response from the request (Receive as a String)
        //log debug to receive the response as a string
        log.debug("Receive response as a string");
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        //Log information if response was successfully received as a string
        log.info("Response received successfully as a string");

      // Store the body of the Response
        //Log debug to save the body of the response to a variable
        log.debug("Store response in a variable");
        String results = response.body();

        //log info if response was successfully saved
        log.info("Response of the body successfully saved in a variable");

        //Gson package to prettify the results
        //Log debug to structure the data from the fleetio
        log.debug("Structure data to human readable format");
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        JsonElement json = JsonParser.parseString(results);

        String prettyJson = pretty.toJson(json);
        System.out.println(prettyJson);

        //Log information if data is well formatted
        log.info("Successfully structured data");

        //A new string to use the substring method to remove the square bracket from the beginning of the response body since it is come as a List
        //Log debug to remove the [] from the beginning of the data
        log.debug("Remove [] brackets");
        String myString = json.toString().substring(1,(json.toString().length()-1));

        //assign formatted string to new variable
        //log debug to assign formatted string to a new variable and structure it
        log.debug("Assign formatted string to new variable");
       String prettyJsonString = pretty.toJson(myString);
       log.info("Formatted string assign successfully");


        //Instance of an Object Mapper
        //log debug to map the objects from the JSON
        ObjectMapper map = new ObjectMapper();

        // create instance of Vehicles class that whose class attributes are mapped from api response
        log.debug("Read Values from json and map to attributes of the vehicle class");
        Vehicles myCar = map.readValue(myString, Vehicles.class);

        // print object values to verify if the properties mapped successfully
        System.out.println(myCar.getName());
        System.out.println(myCar.getLicense_plate());
        System.out.println(myCar.getColor());
        System.out.println(myCar.getId());

        log.info("Mapping was successful");
    }



    }



