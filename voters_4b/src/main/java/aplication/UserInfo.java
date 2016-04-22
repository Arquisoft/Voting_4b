package aplication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
	
	//private static final Logger log = LoggerFactory.getLogger(UserInfo.class);

    private final String email;
    private final String password;
    
    @JsonCreator
	public UserInfo(@JsonProperty("email") String email, @JsonProperty("password")String password) {
    	//log.info("Creating user " + name + ". age: " + password);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}