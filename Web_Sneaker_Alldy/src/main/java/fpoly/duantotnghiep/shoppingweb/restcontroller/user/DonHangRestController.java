package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangTraDTOReponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangTraDTORequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.DonHangEntityManager;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.DonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller("don-hang-restCtrl-user")
@RequestMapping("don-hang")
public class DonHangRestController {
    @Autowired
    private IDonHangService donHangService;

    @Autowired
    private DonHangService idonHangService;

    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private DonHangEntityManager donHangEntityManager;



    @GetMapping("get-by-trangThai-khachHang")
    public ResponseEntity<List<DonHangReponseUser>> getByKhachHangAndTrangThai(@RequestParam(name = "trangThai",defaultValue = "2") Integer trangThai,
                                                                               Authentication authentication) {
        if (authentication != null) {
            String khachHang = authentication.getName();
            return ResponseEntity.ok(donHangService.getAllByKhachHangAndTrangThai(khachHang, trangThai));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("get-by-trangthai-khachhang-tra")
    public  ResponseEntity<List<DonHangReponseUser>>getTrangThaiTra(
          @RequestParam(name = "trangThai",defaultValue = "0") Integer trangThai,
          Authentication authentication) {

        if (authentication != null) {
            String khachHang = authentication.getName();
            return ResponseEntity.ok(idonHangService.getAllByKhachHangAndTrangThaiTra(khachHang, trangThai));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("get-ctsp-tra/{ma}")
    public ResponseEntity<List<DonHangTraDTOReponse>> getCtspTra(@PathVariable("ma") String ma,Authentication authentication) {

        if (authentication != null) {
            String khachHang = authentication.getName();
            return ResponseEntity.ok(idonHangService.getCTSPTra(khachHang, ma));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
//   @GetMapping("get-ctsp-user-tra")
//    public ResponseEntity<?> getCtspTra(@RequestParam("idSP") String id,@RequestParam("trangThai")Integer trangThai) {
//        return ResponseEntity.ok(donHangService.getAllByCTSP(id,trangThai));
//    }

    @PutMapping("huy-don-hang-user")
    public ResponseEntity<?> huyDonHangUser(@RequestBody String lyDoHuy, @RequestParam String ma) throws MessagingException {
        donHangService.huyDonHangUser(ma, lyDoHuy);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("tra-don-hang-user")
    public ResponseEntity<?> traDonHangUser(@RequestBody String lyDoTraHang, @RequestParam String ma,
                                            @RequestParam("phuongThucNhanTien") Boolean phuongThucNhanTien,
                                            @RequestParam("ghiChu") String ghiChu) throws MessagingException {
        System.out.println("LyDoTraHang: " + lyDoTraHang);
        donHangService.traDonHangUser(ma, lyDoTraHang, phuongThucNhanTien, ghiChu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("tra-mot-phan")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> traMotPhan(@Valid @RequestPart("donHang") DonHangDTORequest request,
                                        BindingResult result,
                                        @RequestPart("chiTietDonHang") List<ChiTietDonHangDTORequest> products,
                                        @RequestParam("lyDoTraHang") String lyDoTraHang,
                                        @RequestParam("phuongThucNhanTien") Boolean phuongThucNhanTien,
                                        @RequestParam("ghiChu") String ghiChu) {
        //sản phẩm trống -> thông báo lỗi
        System.out.println("data"+products);
        System.out.println("data1" + request.getLoai());
        if(products.size()<=0){
            result.addError(new FieldError("soLuongSP","soLuongSP","Không có sản phẩm trong đơn trả"));
        }
        // xu ly loi neu co
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        //khong tim duoc ma dontra -> HTTP 404
        if (!donHangService.existsByMa(request.getMa())) {
            return ResponseEntity.notFound().build();
        }
        //khong co loi -> HTTP 200
        return ResponseEntity.ok(donHangService.traMotPhan(request, products, lyDoTraHang, phuongThucNhanTien, ghiChu));
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangReponseUser> getByMa(@PathVariable("ma") String ma) {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMaUser(ma));
    }

    @GetMapping("tra/{ma}")
    public ResponseEntity<DonHangTraDTOReponse> getByMaTra(@PathVariable("ma") String ma) {
        System.out.println("ma" + ma);
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMaTra(ma));
    }


}
