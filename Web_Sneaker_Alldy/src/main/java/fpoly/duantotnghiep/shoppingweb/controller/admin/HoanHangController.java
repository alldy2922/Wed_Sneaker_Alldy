package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/hoan-hang")
public class HoanHangController {
    @GetMapping("")
    public String hoanHang() {
        return "admin/LichSuHoanHang";
    }

}
