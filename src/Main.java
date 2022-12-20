import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;
import java.io.*;
import java.sql.*;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    //Defining the logger object
    static Logger log = Logger.getLogger(Main.class);

    static final String Password  = "P_eter20";
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PeterBe", "PeterBe", Password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, SQLException {



        //An HttpRequest obj together with its method to GET response from the API

        //Log debug to send a GET request to the API
        log.debug("Sending a GET request to the fleetio API");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles"))
                .header("Authorization", "Token 463227032b0367550695cf350481eb81d9df46af")
                .header("Account-Token", " 4cf4569fb1")
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
     //   System.out.println(prettyJson);

        //Log information if data is well formatted
        log.info("Successfully structured data");
        System.out.println(" ");

        //Write results into a json file
        try {
            FileWriter writer = new FileWriter("Results.json");
            writer.write(prettyJson);
            writer.close();
            System.out.println("Successfully wrote to the file");
        } catch (IOException e){
            System.out.println("Error occurred");
            e.printStackTrace();
        }

        //A new string to use the substring method to remove the square bracket from the beginning of the response body since it is come as a List
        //Log debug to remove the [] from the beginning of the data
        log.debug("Remove [] brackets\n");
        String myString = json.toString().substring(1,(json.toString().length()-1));

        //assign formatted string to new variable
        //log debug to assign formatted string to a new variable and structure it
        log.debug("Assign formatted string to new variable\n");
       String prettyJsonString = pretty.toJson(myString);
       log.info("Formatted string assign successfully\n");




        //Instance of an Object Mapper
        //log debug to map the objects from the JSON
        ObjectMapper map = new ObjectMapper();

        //Allow mapping to deserialize json array using java array
        map.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);

        //New array object for vehicles DTO to accept response body and save all entries to an array
        log.debug("Create an array object for vehicles DTO to accept response body and save in an array\n");
        VehiclesDTO[] vehiclesDTO = map.readValue(results,VehiclesDTO[].class);
        log.info("Array object created\n");

        populateVehiclesTables(vehiclesDTO);
        populateSpecsTable(vehiclesDTO);
        System.out.println(" ");
//

        log.info("Mapping was successful");
    }


    // A method to populate the fields of the vehicles table in the database
    public static void populateVehiclesTables(VehiclesDTO[] dtos) throws SQLException {
//
        String query = "INSERT INTO vehicles (id, name, licensePlate,  model, color,year)" + " VALUES (?, ?, ?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(query);

        for (VehiclesDTO dto : dtos) {

            ps.setInt(1, dto.getId());

            ps.setString(2, dto.getName());

            ps.setString(3, dto.getLicensePlate());


            ps.setString(4, dto.getModel());

            ps.setString(5, dto.getColor());

            ps.setString(6, dto.getYear());

            ps.execute();

        }
    }

    //A method to populate the fields of the specs table in the database
    public static void populateSpecsTable(VehiclesDTO[] specdtos) throws SQLException{
        String query = "INSERT INTO SPECS(vehicle_id, body_type, drive_type, created_at, updated_at)" +
                " VALUES" + "(?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        for (VehiclesDTO specdto : specdtos) {
            ps.setInt(1, specdto.getSpecs().getVehicleId());

            ps.setString(2, specdto.getSpecs().getBodyType());

            ps.setString(3, specdto.getSpecs().getDriveType());

            ps.setString(4, specdto.getSpecs().getCreated_at());

            ps.setString(5, specdto.getSpecs().getUpdated_at());

            ps.execute();
        }
    }
    }



