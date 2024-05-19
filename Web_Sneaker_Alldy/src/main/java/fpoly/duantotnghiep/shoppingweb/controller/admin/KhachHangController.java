package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/khach-hang")
public class KhachHangController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private KhachHangService serviceKH;

    @GetMapping("")
    public String hienThiKhachHang(){
        return "";
    }
}
