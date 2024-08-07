package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("${admin.domain}/doi-hang")
public class DoiHangController {
    @GetMapping()
    public String donHang() {
        return "admin/doiHang";
    }
}
