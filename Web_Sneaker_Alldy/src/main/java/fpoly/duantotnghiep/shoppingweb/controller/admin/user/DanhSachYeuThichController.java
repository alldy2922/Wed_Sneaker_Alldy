package fpoly.duantotnghiep.shoppingweb.controller.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/danh-sach-yeu-thich")
public class DanhSachYeuThichController {
    @GetMapping("danh-sach-yeu-thich-cua-toi")
    public String ThongTin(){return "/user/DanhSachYeuThich";}
}
