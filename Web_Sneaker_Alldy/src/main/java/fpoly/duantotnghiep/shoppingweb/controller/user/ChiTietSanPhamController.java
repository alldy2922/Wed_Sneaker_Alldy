package com.example.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("chi-tiet-san-pham-user-ctrl")
@RequestMapping("chi-tiet-san-pham/{maSP}")
public class ChiTietSanPhamController {
    @GetMapping
    public String viewChiTietSanPham(){
        return "user/SanPhamChiTiet";
    }
}
