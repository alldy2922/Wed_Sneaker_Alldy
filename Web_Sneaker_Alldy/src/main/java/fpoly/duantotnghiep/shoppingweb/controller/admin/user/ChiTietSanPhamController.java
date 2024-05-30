package fpoly.duantotnghiep.shoppingweb.controller.admin.user;

import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("chi-tiet-san-pham-user-ctrl")
@RequestMapping("chi-tiet-san-pham/{maSP}")
public class ChiTietSanPhamController {
    @Autowired
    private ISanPhamService sanPhamService;
    @GetMapping
    public String viewChiTietSanPham(@PathVariable("maSP")String maSP){
        if(!sanPhamService.existsByIdUser(maSP)){
            return "admin/authen/notFound";
        }
        return "user/SanPhamChiTiet";
    }
}
