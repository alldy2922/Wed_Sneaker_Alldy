package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("don-hang-controller-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangController {

    @GetMapping("ban-hang")
    public String banHang(){
        return "admin/BanHangTaiQuay";
    }

}
