package fpoly.duantotnghiep.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("user")
public class KhachHangController {
    @GetMapping("thong-tin-user")
    public String viewThongTinUser(){
        return "/user/ThongTinKhachHang";
    }
}
