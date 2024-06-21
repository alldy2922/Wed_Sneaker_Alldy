package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("${admin.domain}/don-hang")
public class DonHangController {
    @GetMapping()
    public String donHang() {
        return "admin/donHang";
    }

    @GetMapping("ban-hang")
    public String banHang() {
        return "admin/BanHangTaiQuay";
    }

}
