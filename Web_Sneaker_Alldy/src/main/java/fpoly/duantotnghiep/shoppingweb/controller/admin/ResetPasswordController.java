package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("${admin.domain}/doi-mat-khau")
public class ResetPasswordController {
    @Autowired
    private INhanVienService nhanVienService;
    @Autowired
    private INhanVienRepository nhanVienRepository;

    @GetMapping
    public String viewFormDoiMatKhau() {

        return "/admin/authen/resetPassword";
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto,
                                           BindingResult result,
                                           Authentication authentication){
        NhanVienModel nhanVienModel = nhanVienRepository.findById(authentication.getName()).get();

        if(resetPasswordDto.getOldPass()!=null){
            if(!resetPasswordDto.getOldPass().equals(nhanVienModel.getPassword())){
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

        nhanVienModel.setPassword(resetPasswordDto.getNewPass());
        nhanVienRepository.save(nhanVienModel);

        return ResponseEntity.ok().build();

    }

}
