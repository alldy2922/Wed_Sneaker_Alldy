package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/nhan-xet")
public class NhanXetController {
    @Autowired
    private ISanPhamService sanPhamService;
    @GetMapping("{maSP}")
    public String show(@PathVariable("maSP")String maSP){
        if(!sanPhamService.existsByIdAdmin(maSP)){
            return "admin/authen/notFound";
        }
        return "admin/nhanXet";
    }
}
