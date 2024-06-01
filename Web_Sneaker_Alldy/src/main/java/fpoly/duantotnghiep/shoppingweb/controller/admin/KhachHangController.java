package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.service.impl.KhachHangServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/khach-hang/")
public class KhachHangController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private KhachHangServiceImpl serviceKH;

    @GetMapping("hien-thi")
    public ResponseEntity<?>hienThiKhachHang(){
        return ResponseEntity.ok(serviceKH.getAll());
    }
}
