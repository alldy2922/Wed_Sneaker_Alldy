package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("user")
public class KhachHangController {
    @GetMapping("thong-tin-ca-nhan")
    public String viewThongTinUser(){
        return "/user/ThongTinKhachHang";
    }
    @GetMapping("thong-tin")
    public String viewLogin(){
        return "/user/ThongTinKhachHang.html";
    }
}
