package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/thong-ke")
public class ThongKeController {
    @RequestMapping("view-tong-quat")
    public String viewThongKeTongQuat(){
        return "admin/thongke/tongQuat";
    }
}
