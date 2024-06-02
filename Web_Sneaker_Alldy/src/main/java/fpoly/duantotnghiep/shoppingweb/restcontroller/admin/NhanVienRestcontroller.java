package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanVienDtoRequest;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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



}
