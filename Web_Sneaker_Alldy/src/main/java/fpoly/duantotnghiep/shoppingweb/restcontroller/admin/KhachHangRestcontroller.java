package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/khach-hang")
@CrossOrigin(origins = "*")
public class KhachHangRestcontroller {

    @Autowired
    private IKhachHangService taiKhoanService;



    @GetMapping("/voucher")
    public ResponseEntity<List<KhachHangDtoResponse>> getKhachHangVoucher(@RequestParam("dieuKien") int dieuKien) {
        List<KhachHangDtoResponse> khachHangList = taiKhoanService.khachHangVoucher(dieuKien);
        return new ResponseEntity<>(khachHangList, HttpStatus.OK);
    }
    @GetMapping("get-all-khach-hang")
    public ResponseEntity<Page<KhachHangDtoResponse>> getAllKhachHang(@RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "8") Integer limit,
                                                                      @RequestParam(required = false) String keyWord) {
        if (keyWord != null) {
            return ResponseEntity.ok(taiKhoanService.search(keyWord, page, limit));
        }
        return ResponseEntity.ok(taiKhoanService.getAll(page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody KhachHangDTORequest khachHang,
                                 BindingResult result) throws MessagingException {
        if (khachHang.getUsername()!=null && !khachHang.getUsername().isBlank()){
            if (taiKhoanService.exsistsByUsername(khachHang.getUsername())){
                result.addError(new FieldError("soDienThoai", "soDienThoai", "Số điện thoại đã tồn tại"));
            }
        }
        if (result.hasErrors())
            return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(taiKhoanService.add(khachHang));
    }
    @DeleteMapping("{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("username")String username){
        System.out.println(username);
        if(!taiKhoanService.exsistsByUsername(username)){
            return ResponseEntity.notFound().build();
        }
        taiKhoanService.deleteByUsername(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<KhachHangDtoResponse> getById(@PathVariable("id") String id) {
        if (taiKhoanService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taiKhoanService.findById(id));
    }
}
