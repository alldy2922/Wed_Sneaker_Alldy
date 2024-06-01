package fpoly.duantotnghiep.shoppingweb.controller;

import fpoly.duantotnghiep.shoppingweb.service.impl.VaiTroServeImpl;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vai-tro")
public class VaiTroController {
    @Autowired
    HttpServlet req;
    @Autowired
    private VaiTroServeImpl vaiTroServe;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> hienThiVT(){
        return ResponseEntity.ok(vaiTroServe.findAll());
    }
}
