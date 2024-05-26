package fpoly.duantotnghiep.shoppingweb.controller;

import fpoly.duantotnghiep.shoppingweb.service.impl.DiaChiServeImpl;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dia-chi")
public class DiaChiController {
    @Autowired
    HttpServlet req;
    @Autowired
    private DiaChiServeImpl diaChiServe;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> hienThiDiaChi(){
        return ResponseEntity.ok(diaChiServe.findAll());
    }
}
