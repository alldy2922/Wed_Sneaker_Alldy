package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanVienDtoRequest;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${admin.domain}/nhan-vien")
public class NhanVienRestcontroller {

    @Autowired
    private INhanVienService nhanVienService;


    @GetMapping("get-all")
    public ResponseEntity<Page<NhanVienDtoResponse>> getAllKhachHang(@RequestParam(defaultValue = "0")Integer page,
                                                                     @RequestParam(defaultValue = "8")Integer limit,
                                                                     @RequestParam(required = false)String keyWord){
        if(keyWord!=null){
            return ResponseEntity.ok(nhanVienService.search(keyWord,page,limit));
        }
        return ResponseEntity.ok(nhanVienService.getAll(page,limit));
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<NhanVienDtoResponse> getById(@PathVariable("id")String id){
        if(nhanVienService.existsByUsername(id)==false){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nhanVienService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody NhanVienDtoRequest nhanVien,
                                 BindingResult result) throws MessagingException {
        if(nhanVien.getUsername()!=null && !nhanVien.getUsername().isBlank()){
            if(nhanVienService.existsByUsername(nhanVien.getUsername())){
                result.addError(new FieldError("username","username","Username đã tồn tại"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(nhanVienService.add(nhanVien));
    }
    @PutMapping(value = "thong-tin-ca-nhan")
    public ResponseEntity<?> updateUer(@Valid @RequestPart("nhanVien") NhanVienDtoRequest nhanVien,
                                 BindingResult result,
                                    @RequestPart(value = "img",required = false) MultipartFile img) throws IOException {
        if(nhanVien.getUsername()!=null && !nhanVien.getUsername().isBlank()){
            if(!nhanVienService.existsByUsername(nhanVien.getUsername())){
                result.addError(new FieldError("username","username","Username Không tồn tại"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(nhanVienService.update(nhanVien,img));
    }
    @PutMapping("update")
    public ResponseEntity<?> updateNhanVien(@Valid @RequestBody NhanVienDtoRequest nhanVien,
                                            BindingResult result){
        if(nhanVien.getUsername()!=null && !nhanVien.getUsername().isBlank()){
            if(!nhanVienService.existsByUsername(nhanVien.getUsername())){
                result.addError(new FieldError("username","username","Username Không tồn tại"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(nhanVienService.update(nhanVien));
    }

    @GetMapping("getUser")
    public ResponseEntity<?> getUserAdmin(Authentication authentication){
        String username = authentication.getName();
        return ResponseEntity.ok(nhanVienService.findById(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deletByUsername(@PathVariable("username")String username){
        if(!nhanVienService.existsByUsername(username)){
            return ResponseEntity.notFound().build();
        }

        nhanVienService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

}
