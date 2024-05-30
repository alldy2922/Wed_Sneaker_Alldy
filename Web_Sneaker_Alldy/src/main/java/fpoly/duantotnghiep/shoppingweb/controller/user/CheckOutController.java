package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.impl.GioHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    GioHangServiceImpl gioHangService;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;


    @GetMapping("/check-out")
    public String checkOut() {
        if (gioHangService.laySpTrongGio() == null) {
            return "redirect:/cart";
        }



        return "/user/Checkout";
    }
}
