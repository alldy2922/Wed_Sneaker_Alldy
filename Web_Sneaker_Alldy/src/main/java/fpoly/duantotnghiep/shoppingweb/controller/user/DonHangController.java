package com.example.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("don-hang-ctrl-user")
@RequestMapping("don-hang")
public class DonHangController {
    @GetMapping("chi-tiet-don-hang/{maDH}")
    public String viewChiTietDonHang(){
        return "user/ChiTietDonHang";
    }
}
