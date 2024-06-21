package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/hoa-don")
public class HoaDon {

    @GetMapping("")
    public String hoaDon() {
        return "admin/HoaDon";
    }

}
