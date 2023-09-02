package pojo.Ecom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInResponse {
    private  String token;
    private String userId;
    private String message;
}
