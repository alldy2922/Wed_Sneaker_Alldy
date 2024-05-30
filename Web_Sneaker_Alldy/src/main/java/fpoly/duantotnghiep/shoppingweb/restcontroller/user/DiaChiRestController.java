package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.*;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class
DiaChiRestController {
    @Autowired
    IDiaChiService diaChiService;
    @Autowired
    IKhachHangService khachHangService;

    @PostMapping("/dia-chi-by-id")
    public ResponseEntity<?> diaChiFindID(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        return ResponseEntity.ok(diaChiService.findById(id));
    }

    @GetMapping("/ss")
    public ResponseEntity<?> diaCHiT() {
        return ResponseEntity.ok(khachHangService.findById("VuTrang"));
    }


    @PostMapping("/dia-chi")
    public ResponseEntity<?> saveDiaChi(@Valid @RequestBody DiaChiModel diaChiModel, BindingResult result, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String khachHang = authentication.getName();
            KhachHangModel khachHangModel = new KhachHangModel();
            khachHangModel.setUsername(khachHang);
            diaChiModel.setTaiKhoan(khachHangModel);
            if (result.hasErrors()) {
                return ValidateUtil.getErrors(result);
            }
            diaChiService.addDiaChi(diaChiModel);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vui lòng đăng nhập để truy cập thông tin.");
    }

    @GetMapping("/dia-chi-khach-hang")
    public ResponseEntity<?> listDiaChiByTaiKhoan(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String khachHang = authentication.getName();
            KhachHangDtoResponse kh = khachHangService.findById(khachHang);

            if (kh != null) {
                return ResponseEntity.ok(khachHangService.diaChiByTaiKhoan(khachHang));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Đăng nhập");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vui lòng đăng nhập để truy cập thông tin.");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<KhachHangDtoResponse> getById(@PathVariable("id") String id) {
        if (khachHangService.exsistsByUsername(id) == false) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(khachHangService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody DiaChiDTORequest diaChi,
                                 BindingResult result,
                                 Authentication authentication) throws IOException {
        if (result.hasErrors()) {
            return ValidateUtil.getErrors(result);
        }
        diaChi.setTaiKhoan(authentication.getName());
        System.out.println(diaChi.toString());
        return ResponseEntity.ok(diaChiService.add(diaChi));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DiaChiDTORequest diaChi,
                                    BindingResult result, @PathVariable Long id, Authentication authentication) {
        diaChi.setId(id);
        if (result.hasErrors()) {
            return ValidateUtil.getErrors(result);
        }
        diaChi.setTaiKhoan(authentication.getName());
        return ResponseEntity.ok(diaChiService.update(diaChi));
    }

    @GetMapping("/chi-tiet/{id}")
    public ResponseEntity<DiaChiDTOResponse> chiTiet(@PathVariable("id") Long id) {
        System.out.println(id);
        return ResponseEntity.ok(diaChiService.findById(id));
    }

    @DeleteMapping("/deleteDC/{id}")
    public ResponseEntity<?> deletById(@PathVariable("id") Long id) {
        if (!diaChiService.exsistsById(id)) {
            return ResponseEntity.notFound().build();
        }
        diaChiService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dia-chi/set-mac-dinh/{id}")
    public ResponseEntity<?> setMacDinh(@PathVariable("id") Long id,
                                        Authentication authentication) {
        if (!diaChiService.exsistsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (authentication == null) {
            return ResponseEntity.ok(HttpStatus.UNAUTHORIZED);
        }
        diaChiService.setMacDinh(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dia-chi/get-mac-dinh/{username}")
    public ResponseEntity<?> getMacDinh(@PathVariable("username") String khachHang) {
        return ResponseEntity.ok(diaChiService.getDiaChiMacDinh(khachHang, true));
    }

    @GetMapping("/thanh-toan/dia-chi-mac-dinh")
    public ResponseEntity<?> findDiaChiMacDinhByKhach(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(diaChiService.getDiaChiMacDinh(authentication.getName(), true));
    }
}
