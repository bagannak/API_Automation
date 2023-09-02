package pojo.Ecom;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateOrderDetails {
    private List<String> orders;
    private  List<String> productOrderId;
    private String message;
}
