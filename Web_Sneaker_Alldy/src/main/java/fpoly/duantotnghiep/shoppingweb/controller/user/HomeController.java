package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.config.security.Customer;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller("san-pham-user-ctrl")
public class HomeController {
    @Autowired
    IGioHangService gioHangService;
    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("trang-chu")
    public String trangChu() {
        return "/user/trangChu";
    }

    @GetMapping("san-pham")
    public String sanPham() {
        return "/user/sanPhamUser";
    }

    @GetMapping("san-pham/thuong-hieu/{id}")
    public String sanPhamByThuongHieu() {
        return "/user/filterProduct";
    }

//    @GetMapping("gio-hang")
//    public String gioHang() {
//        return "/user/GioHang";
//    }

//    @GetMapping("thanh-toan")
//    public String thanhToan() {
//        List<GioHangDtoReponse> giohang = gioHangService.laySpTrongGio();
//        if (giohang.size() == 0) {
//            return "redirect:/gio-hang";
//        }
//        if(!gioHangService.checkSoLuong()) return "redirect:/gio-hang";
//        return "/user/thanhToan";
//    }
@GetMapping("thanh-toan")
public String thanhToanUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
        // User is not logged in
        List<GioHangDtoReponse> giohang = gioHangService.laySpTrongGio();
        if (giohang.size() == 0) {
            return "redirect:/gio-hang";
        }
        if (!gioHangService.checkSoLuong()) {
            return "redirect:/gio-hang";
        }
        return "/user/thanhToan";
    } else {
        // User is logged in
        Customer customer = (Customer) authentication.getPrincipal();
        KhachHangModel khachHang = customer.getKhachHangModel();
        List<GioHangDtoReponse> giohang = gioHangService.getCartFromDatabase(khachHang);
        List<GioHangDtoReponse> giohangM = gioHangService.laySpTrongGio();

        if (giohang.size() == 0) {
            return "redirect:/gio-hang";
        }
        if (giohangM.size() == 0 || !gioHangService.checkSoLuong()) {
            return "/user/thanhToanUserLogin";
        }
        if (gioHangService.checkSoLuong()) {
            return "/user/thanhToan";
        }
        if (giohangM.size() >= 1) {
            return "/user/thanhToan";
        }else if(giohang.size()>=1){
            return "/user/thanhToanUserLogin";
        }
        return "/user/thanhToanUserLogin"; // Ensure there's a return statement here
    }
}

    @GetMapping("lich-su-mua-hang1")
    public String licSu() {
        return "/user/hoaDonNguoiDung";
    }

    @GetMapping("danh-sach-yeu-thich")
    public String danhSachYeuThich() {
        return "/user/dsyt";
    }

    @GetMapping("lien-he")
    public String lienHe(){
        return "/user/lienhe";
    }
    @GetMapping("san-pham-khuyen-mai")
    public String sanPhamKhuyenMai(){
        return "/user/sanPhamKhuyenMai";
    }

    @GetMapping("1")
    public String danhSa() {
        return "/user/trangChu1";
    }
}

