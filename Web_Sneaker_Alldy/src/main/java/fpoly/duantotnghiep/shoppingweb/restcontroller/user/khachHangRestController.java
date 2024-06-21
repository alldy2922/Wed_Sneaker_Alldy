package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.VoucherService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/khach-hang")
public class khachHangRestController {

    @Autowired
    private IKhachHangService khachHangService;
    @Autowired

    private VoucherService vc;
//
//    @GetMapping("/get-all")
//    public ResponseEntity<Page<KhachHangDtoResponse>> getAllKhachHang(@RequestParam(defaultValue = "0")Integer page,
//                                                                      @RequestParam(defaultValue = "8")Integer limit){
//        return ResponseEntity.ok(khachHangService.getAll(page,limit));
//
//    }

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody KhachHangDTORequest khachHang,
                                 BindingResult result) throws MessagingException {
        if (khachHang.getUsername() != null && !khachHang.getUsername().isBlank()) {
            if (khachHangService.exsistsByUsername(khachHang.getUsername())) {
                result.addError(new FieldError("username", "username", "Username đã tồn tại"));
                if (!result.hasErrors())
                    return ValidateUtil.getErrors(result);
            }
        }
//        if(resetPasswordDto.getNewPass()!=null&&resetPasswordDto.getVerifyNewPass()!=null){
//            if(!resetPasswordDto.checkVerifyPassword()){
//                result.addError(new FieldError("verifyNewPass","verifyNewPass","Nhập lại mật khẩu không chính xác"));
//                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
//            }
//        }
        if (result.hasErrors())
            return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.add(khachHang));
    }

    @GetMapping("getUser")
    public ResponseEntity<?> getUserKhachHang(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(khachHangService.findById(username));
    }

    @GetMapping("bd")
    public ResponseEntity<?> getUserKhachHang11(){
        return ResponseEntity.ok(vc.findById("EYOQIG6S"));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestBody KhachHangDTORequest khachHang,
                                             BindingResult result) {
        if (khachHang.getUsername() != null && !khachHang.getUsername().isBlank()) {
            if (!khachHangService.exsistsByUsername(khachHang.getUsername())) {
                result.addError(new FieldError("username", "username", "Username Không tồn tại"));
                if (!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if (result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.update(khachHang));
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestPart("ThongTinKhachHang") KhachHangDTORequest
                                                     khachHang,
                                             BindingResult result,
                                             @RequestPart(value = "img", required = false) MultipartFile img) throws IOException {
        if (khachHang.getUsername() != null && !khachHang.getUsername().isBlank()) {
            if (!khachHangService.exsistsByUsername(khachHang.getUsername())) {
                result.addError(new FieldError("username", "username", "Username Không tồn tại"));
                if (!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }

        if (result.hasErrors()) return ValidateUtil.getErrors(result);

        return ResponseEntity.ok(khachHangService.update(khachHang, img));
    }


}
