package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/mau-sac")
public class MauSacController {

    @GetMapping("")
    public String show() {
        return "/admin/mauSac";
    }
}

