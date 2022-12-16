import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecsDTO {

    @JsonProperty("vehicle_id")
    private int vehicleId;

    @JsonProperty("body_type")
    private String bodyType;

    @JsonProperty("drive_type")
    private String driveType;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("updated_at")
    private String updated_at;

    public int getVehicleId() {
        return vehicleId;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}