package fpoly.duantotnghiep.shoppingweb.controller;

import fpoly.duantotnghiep.shoppingweb.service.INhanVienServe;
import fpoly.duantotnghiep.shoppingweb.service.impl.NhanVienServiceImpl;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/nhan-vien")
public class NhanVienController {
    @Autowired
    HttpServlet req;
    @Autowired
    private INhanVienServe nvSer;

    @GetMapping("")
    public String hienThiNV(){
        return "admin/nhan-vien";
    }
}
