package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/khach-hang")
public class KhachHangController {

    @GetMapping("")
    public String getAllTaiKhoanNguoiDung() {
        return "admin/KhachHang";
    }
}
