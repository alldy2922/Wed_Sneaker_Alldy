package fpoly.duantotnghiep.shoppingweb.model;

import lombok.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SessionScope
public class Cart {
    private Map<String,Integer> productInCart = new HashMap<>();
}