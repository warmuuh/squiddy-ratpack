package wrm.squiddy.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestDescription {

	@JsonProperty("_id")
    String id;
	
	String description;
}
