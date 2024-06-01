package fpoly.duantotnghiep.shoppingweb.controller.user;

import com.example.shoppingweb.service.impl.GioHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckOutController {
    @Autowired
    GioHangServiceImpl gioHangService;


    @GetMapping("/check-out")
    public String checkOut() {
        if (gioHangService.laySpTrongGio() == null) {
            return "redirect:/cart";

        }
        return "/user/Checkout";
    }
}
