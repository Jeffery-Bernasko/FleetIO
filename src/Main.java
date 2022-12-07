import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //An HttpRequest obj together with its method to GET response from the API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://secure.fleetio.com/api/v1/vehicles?q[license_plate_eq]=GW-1456-22"))
                .header("Authorization", "Token 1cbfa399842a1baee03b046cb72e49c64c9df401")
                .header("Account-Token", "68c3339b75")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();


        //response obj to receive the response from the request (Receive as a String)

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

      // Store the body of the Response
        String results = response.body();

        //Gson package to prettify the results
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        JsonElement json = JsonParser.parseString(results);

        String prettyJson = pretty.toJson(json);
        System.out.println(prettyJson);

        //A new string to use the substring method to remove the square bracket from the begging of the response body since it is come as a List
        String myString = json.toString().substring(1,(json.toString().length()-1));

        //System.out.println(myString);

        //assign formatted string to new variable
       String prettyJsonString = pretty.toJson(myString);


        //Instance of an Object Mapper
        ObjectMapper map = new ObjectMapper();

        // create instance of Vehicles class that whose class attributes are mapped from api response
        Vehicles myCar = map.readValue(myString, Vehicles.class);

        // print object values to verify if the properties mapped successfully
        System.out.println(myCar.getName());
        System.out.println(myCar.getLicense_plate());
        System.out.println(myCar.getColor());
        System.out.println(myCar.getId());
    }



    }



