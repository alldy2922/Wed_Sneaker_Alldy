package fpoly.duantotnghiep.shoppingweb.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gio-hang")
public class CartController {
    @GetMapping()
    private String show(){
        return "/user/GioHang";
    }
}
