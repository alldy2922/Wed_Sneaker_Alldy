package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/nhan-vien")
public class NhanVienController {
    @GetMapping("")
    public String getNhanVienView(){
        return "admin/NhanVien";
    }
    @GetMapping("thong-tin-ca-nhan")
    public String getUserDetail(){
        return "admin/thongTinUser";
    }
}
