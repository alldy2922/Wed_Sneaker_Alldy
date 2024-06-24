package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("thanh-toan")
    public String thanhToan() {
        List<GioHangDtoReponse> giohang = gioHangService.laySpTrongGio();
        if (giohang.size() == 0) {
            return "redirect:/gio-hang";
        }
        if(!gioHangService.checkSoLuong()) return "redirect:/gio-hang";
        return "/user/thanhToan";
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

