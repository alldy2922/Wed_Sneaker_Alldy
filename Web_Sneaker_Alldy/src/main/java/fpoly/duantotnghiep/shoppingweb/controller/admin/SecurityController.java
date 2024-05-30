package fpoly.duantotnghiep.shoppingweb.controller.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.model.Token;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.service.impl.TokenServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.RandomUtil;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class SecurityController {
    @Autowired
    private INhanVienService nhanVienService;
    @Autowired
    private INhanVienRepository nhanVienRepository;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/security/unauthoried")
    public String unauthoried(){
        return "admin/authen/403";
    }

    @GetMapping("/admin/logout") //Đăng xuất
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/admin/trang-chu"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("${admin.domain}/login")//Hiển thị form đăng nhập
    public String viewLogin() {
        return "/admin/authen/LoginForm.html";
    }

    @GetMapping("${admin.domain}/login/error")
    public String viewLoginError(Model model, Authentication authentication) {
        model.addAttribute("mess", "Tài khoản hoặc mật khẩu không chính xác!");
        return "/admin/authen/LoginForm.html";
    }

    @GetMapping("${admin.domain}/quen-mat-khau")//Hiển thị form quên mật khẩu
    public String viewForgotPass() {
        return "/admin/authen/ForgotPass.html";
    }

    @PostMapping("${admin.domain}/quen-mat-khau")
    @ResponseBody
    public ResponseEntity<?> forgotPass(@RequestBody String username) throws MessagingException { //Tạo và gửi token
        HashMap<String, String> map = new HashMap<>();
        System.out.println(username);
        if (username == null || username.isBlank()) {
            map.put("er", "Vui lòng nhập username");
            return ResponseEntity.status(400).body(map);
        } else if (!nhanVienService.existsByUsername(username)) {
            map.put("er", "Username không tồn tại");
            return ResponseEntity.status(400).body(map);
        }
        Token token = new Token(RandomUtil.randomPassword(), new Date());
        tokenService.saveToken(username, token);

        NhanVienDtoResponse nhanVienDtoResponse = nhanVienService.findById(username);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Context context = new Context();
//        context.setVariable("username",nhanVienDtoResponse.getUsername());
//        context.setVariable("token",token.getToken());
//        EmailUtil.sendEmailWithHtml(nhanVienDtoResponse.getEmail(), "Đặt Lại Mật Khẩu","/admin/authen/ConfirmResetPassword",context);
//        map.put("success","Thành công! Vui lòng kiểm tra mail của tài khoản để đặt lại mật khẩu");

        EmailUtil.sendEmail(nhanVienDtoResponse.getEmail(), "Tìm lại mật khẩu!", "Mã xác nhận của bạn là: " + token.getToken());

        return ResponseEntity.ok().build();

    }

    @GetMapping("${admin.domain}/quen-mat-khau/xac-nhan")
    public String formXacNhan() { //Hiển thị form xác nhận
        Cookie[] cookies = request.getCookies();
        for (var c : cookies) {
            if (c.getName().equals("username")) {
                System.out.println(c.getValue());;
            }
        }
        return "admin/authen/NhapMaToken";
    }


    @PutMapping("${admin.domain}/quen-mat-khau/xac-nhan")
    public ResponseEntity<?> viewDatLaiMK(//kiểm tra token
            @RequestBody String tokenValue) {

        String username = "";
        Cookie[] cookies = request.getCookies();
        for (var c : cookies) {
            if (c.getName().equals("username")) {
                username = c.getValue();
            }
        }
        Token token = tokenService.getToken(username);


        if (tokenValue == null || tokenValue.isBlank()) {
            Map<String, String> er = new HashMap<>();
            er.put("er", "Vui lòng nhập mã xác nhận");
            return ResponseEntity.badRequest().body(er);
        }

        if (!token.getToken().equals(tokenValue)) {
            Map<String, String> er = new HashMap<>();
            er.put("er", "Mã xác nhận không chính xác");
            return ResponseEntity.badRequest().body(er);
        }

        if (token.checkTimeAfter30Seconds()) {
            Map<String, String> er = new HashMap<>();
            er.put("er", "Mã xác nhận hết hiệu lực");
            return ResponseEntity.badRequest().body(er);
        } else {
            return ResponseEntity.ok().build();
        }

    }

    @GetMapping("${admin.domain}/dat-lai-mat-khau/thanh-cong")
    public String success() { //Đặt lại mật khẩu thành công
        return "admin/authen/Success";
    }

    @GetMapping("${admin.domain}/dat-lai-mat-khau")
    public String formDatLaiMK() { //hiển thị form đặt lại mật khẩu khi nhập thành công token
        return "admin/authen/datLaiMK";
    }

    @PutMapping("${admin.domain}/dat-lai-mat-khau")
    @ResponseBody
    public ResponseEntity<?> datLaiMK(@Valid @RequestBody ResetPasswordDto resetPasswordDto,
                                      BindingResult result) { // api đặt lại mật khẩu

        if (resetPasswordDto.getNewPass() != null) {
            if (resetPasswordDto.getVerifyNewPass() == null || resetPasswordDto.getVerifyNewPass().isBlank()) {
                result.addError(new FieldError("verifyNewPass", "verifyNewPass", "Vui lòng nhập lại mật khẩu"));
            }
        }
        if (resetPasswordDto.getNewPass() != null && resetPasswordDto.getVerifyNewPass() != null && !resetPasswordDto.getVerifyNewPass().isBlank()) {
            if (!resetPasswordDto.checkVerifyPassword()) {
                result.addError(new FieldError("verifyNewPass", "verifyNewPass", "Nhập lại mật khẩu không chính xác"));
            }
        }
        if (result.hasErrors()) {
            return ValidateUtil.getErrors(result);
        }
        String username = "";
        Cookie[] cookies = request.getCookies();
        for (var c : cookies) {
            if (c.getName().equals("username")) {
                username = c.getValue();
            }
        }
        NhanVienModel nhanVienModel = nhanVienRepository.findById(username).get();
        nhanVienModel.setPassword(resetPasswordDto.getNewPass());
        nhanVienRepository.save(nhanVienModel);
        tokenService.removeToken();
        if(cookies!=null) // xóa cookie
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0);
            }

        return ResponseEntity.ok().build();


    }
}
