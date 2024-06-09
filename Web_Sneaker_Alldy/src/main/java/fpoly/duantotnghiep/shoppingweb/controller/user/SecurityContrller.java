package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.Token;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.TokenServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.RandomUtil;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller("Security-user")
public class SecurityContrller {
    @Autowired
    private IKhachHangService khachHangService;
    @Autowired
    private IKhachHangRepository khachHangRepository;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("dang-nhap")
    public String viewLogin(){
        return "/user/authen/LoginForm.html";
    }
    @GetMapping("dang-ky-nguoi-dung")
    public String viewDangKy(){
        return "/user/authen/DangKyForm.html";
    }
    @GetMapping("dang-ky-nguoi-dung/error")
    public String viewDangKyError(Model model, Authentication authentication){
        model.addAttribute("mess","Tài khoản tồn tại");
        return "/user/authen/DangKyForm.html";
    }
    @GetMapping("dang-nhap/error")
    public String viewLoginError(Model model, Authentication authentication){
        model.addAttribute("mess","Tài khoản hoặc mật khẩu không chính xác!");
        return "/user/authen/LoginForm.html";
    }
    @GetMapping("/quen-mat-khau")//Hiển thị form quên mật khẩu
    public String viewForgotPass() {
        return "/user/authen/QuenMK.html";
    }
    @PostMapping("/quen-mat-khau")
    @ResponseBody
    public ResponseEntity<?> forgotPass(@RequestBody String username) throws MessagingException { //Tạo và gửi token
        HashMap<String, String> map = new HashMap<>();
        System.out.println(username);
        if (username == null || username.isBlank()) {
            map.put("er", "Vui lòng nhập username");
            return ResponseEntity.status(400).body(map);
        } else if (!khachHangRepository.existsById(username)) {
            map.put("er", "Username không tồn tại");
            return ResponseEntity.status(400).body(map);
        }
        Token token = new Token(RandomUtil.randomPassword(), new Date());
        tokenService.saveToken(username, token);

        KhachHangDtoResponse khachHangDtoResponse = khachHangService.findById(username);

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Context context = new Context();
//        context.setVariable("username",nhanVienDtoResponse.getUsername());
//        context.setVariable("token",token.getToken());
//        EmailUtil.sendEmailWithHtml(nhanVienDtoResponse.getEmail(), "Đặt Lại Mật Khẩu","/admin/authen/ConfirmResetPassword",context);
//        map.put("success","Thành công! Vui lòng kiểm tra mail của tài khoản để đặt lại mật khẩu");

        EmailUtil.sendEmail(khachHangDtoResponse.getEmail(), "Tìm lại mật khẩu!", "Mã xác nhận của bạn là: " + token.getToken());

        return ResponseEntity.ok().build();

    }

    @GetMapping("/quen-mat-khau/xac-nhan")
    public String formXacNhan() { //Hiển thị form xác nhận
        Cookie[] cookies = request.getCookies();
        for (var c : cookies) {
            if (c.getName().equals("username")) {
                System.out.println(c.getValue());;
            }
        }
        return "user/authen/NhapMaToken";
    }


    @PutMapping("/quen-mat-khau/xac-nhan")
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

    @GetMapping("/dat-lai-mat-khau/thanh-cong")
    public String success() { //Đặt lại mật khẩu thành công
        return "user/authen/Success";
    }

    @GetMapping("/dat-lai-mat-khau")
    public String formDatLaiMK() { //hiển thị form đặt lại mật khẩu khi nhập thành công token
        return "user/authen/datLaiMK";
    }

    @PutMapping("/dat-lai-mat-khau")
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
        KhachHangModel khachHangModel = khachHangRepository.findById(username).get();
        khachHangModel.setPassword(resetPasswordDto.getNewPass());
        khachHangRepository.save(khachHangModel);
        tokenService.removeToken();
        if(cookies!=null) // xóa cookie
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0);
            }

        return ResponseEntity.ok().build();


    }
}
