package pojo.Ecom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProductResponse {
    private String productId;
    private String message;

}
