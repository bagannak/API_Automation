package pojo.Ecom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private List<String> orders;
    private  List<String> productOrderId;
    private String message;
}
