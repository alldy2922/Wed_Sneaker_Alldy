package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("don-hang-controller-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangController {
    @GetMapping()
    public String donHang(){
        return "admin/donHang";
    }
    @GetMapping("ban-hang")
    public String banHang(){
        return "admin/BanHangTaiQuay";
    }

    @GetMapping("quan-ly-don-hang")
    public String quanLyDonHang(){
        return "admin/quan-ly-don-hang";
    }

    @GetMapping("ban-hang-tai-quay")
    public String bhTaijQuaayf(){
        return "admin/ban-hang-tai-quay";
    }
}
