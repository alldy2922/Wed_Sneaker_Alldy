package fpoly.duantotnghiep.shoppingweb.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {
    @GetMapping("/productD")
    private String show() {
        return "/user/productDetail";
    }
}
