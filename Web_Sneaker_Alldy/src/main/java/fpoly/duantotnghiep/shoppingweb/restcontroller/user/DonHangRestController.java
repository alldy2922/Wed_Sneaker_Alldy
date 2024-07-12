package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("don-hang-restCtrl-user")
@RequestMapping("don-hang")
public class DonHangRestController {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("get-by-trangThai-khachHang")
    public ResponseEntity<List<DonHangReponseUser>> getByKhachHangAndTrangThai(@RequestParam(name = "trangThai",defaultValue = "2") Integer trangThai,
                                                                               Authentication authentication) {
        if (authentication != null) {
            String khachHang = authentication.getName();
            return ResponseEntity.ok(donHangService.getAllByKhachHangAndTrangThai(khachHang, trangThai));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("huy-don-hang-user")
    public ResponseEntity<?> huyDonHangUser(@RequestBody String lyDoHuy, @RequestParam String ma) throws MessagingException {

        donHangService.huyDonHangUser(ma, lyDoHuy);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("tra-don-hang-user")
    public ResponseEntity<?> traDonHangUser(@RequestBody String lyDoTraHang,
                                            @RequestParam String ma,
                                            @RequestParam("phuongThucNhanTien") Boolean phuongThucNhanTien,
                                            @RequestParam("ghiChu") String ghiChu) throws MessagingException {

        donHangService.traDonHangUser(ma, lyDoTraHang, phuongThucNhanTien, ghiChu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangReponseUser> getByMa(@PathVariable("ma") String ma) {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMaUser(ma));
    }


}