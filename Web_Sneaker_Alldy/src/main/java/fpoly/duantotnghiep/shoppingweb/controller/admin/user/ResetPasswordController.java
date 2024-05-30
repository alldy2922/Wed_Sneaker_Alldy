package fpoly.duantotnghiep.shoppingweb.controller.admin.user;

import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@Controller("User-resetPassword")
@RequestMapping("/doi-mat-khau")
public class ResetPasswordController {
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @GetMapping
    public String ViewDoiMatKhau(){
        return "/user/authen/resetPassword";
    }
    @PutMapping()
    @ResponseBody
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto,
                                           BindingResult result,
                                           Authentication authentication){
        KhachHangModel khachHangModel = khachHangRepository.findById(authentication.getName()).get();
        if(resetPasswordDto.getOldPass()!=null){
            if(!resetPasswordDto.getOldPass().equals(khachHangModel.getPassword())){
                result.addError(new FieldError("oldPass","oldPass","Mật khẩu không chính xác"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(resetPasswordDto.getNewPass()!=null&&resetPasswordDto.getVerifyNewPass()!=null){
            if(!resetPasswordDto.checkVerifyPassword()){
                result.addError(new FieldError("verifyNewPass","verifyNewPass","Nhập lại mật khẩu không chính xác"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        khachHangModel.setPassword(resetPasswordDto.getNewPass());
        khachHangRepository.save(khachHangModel);
        return ResponseEntity.ok().build();
    }


}
