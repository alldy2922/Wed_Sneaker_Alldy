package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller("Security-user")
public class SecurityController {
    @Autowired
    private IKhachHangService khachHangService;
    @Autowired
    private IKhachHangRepository khachHangRepository;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private HttpRequest request;

    @GetMapping("dang-ky-tai-khoan") // hiển thị form đăng ký tài khoản
    public String viewRegister(){
        return "/user/authen/FormRegister.html";
    }
    @GetMapping("dang-ky-tai-khoan/error")
    public String viewRegisterError(Model model, Authentication authentication){
        model.addAttribute("mess","Tài khoản tồn tại");
        return "/user/authen/FormRegister.html";
    }
}
