import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize

public class VehiclesDTO implements Serializable {



    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    @JsonProperty("year")
    private String year;

    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("make")
    private String make;

    @JsonProperty("model")
    private String model;

    @JsonProperty("color")
    private String color;



    // Create DTO for specs later
    @JsonProperty("specs")
    private SpecsDTO Specs;


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public SpecsDTO getSpecs() {
        return Specs;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }


}
