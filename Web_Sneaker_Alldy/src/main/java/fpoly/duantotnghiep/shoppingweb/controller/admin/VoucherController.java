package fpoly.duantotnghiep.shoppingweb.controller.admin;


import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/voucher/")
public class VoucherController {
    @Autowired
    private VoucherServiceImpl voucherService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> hienThiAll(){
        return ResponseEntity.ok(voucherService.findAll());
    }
}
