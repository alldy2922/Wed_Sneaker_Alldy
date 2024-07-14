package fpoly.duantotnghiep.shoppingweb.controller.admin;


import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.entitymanager.DonHangEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("${admin.domain}/tra-hang")
public class TraHangController {
    @Autowired
    private IDonHangService donHangService;
    @Autowired
    private IChiTietDonHangService chiTietDonHangService;
    @Autowired
    private DonHangEntityManager donHangEntityManager;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IDonHangResponsitory donHangResponsitory;
    @Autowired
    private VnPayServiceImpl vnPayService;

    @GetMapping()
    public String donHang() {
        return "admin/traHang";
    }

    @GetMapping("update-trang-thai-tra-hang/{ma}")
    public ResponseEntity<?> updateTrangThaiTraHang(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }

        DonHangModel donHangModel = donHangResponsitory.findById(ma).get();
        if (donHangModel.getLoai() == 1 && trangThai == 4) {
            if (donHangModel.getPhuongThucThanhToan() == false) {
                String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
                String vnpayUrl = vnPayService.createOrder(donHangModel.getMa(), baseUrl, donHangModel.getTongTien().multiply(BigDecimal.valueOf(100)).intValue() + "");
                Map<String, String> vnPayUrl = new HashMap<>();
                vnPayUrl.put("vnPayUrl", vnpayUrl);
                return ResponseEntity.ok(vnPayUrl);
            }
        }
        donHangService.updateTrangThaiTraHang(ma, trangThai);
        return ResponseEntity.ok().build();
    }


//    @PutMapping("update-trang-thai-tra-hang/{ma}")
//    public ResponseEntity<?> updatTrangThaiAndSendMail(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
//        try {
//            String email = "heinikens0408@gmail.com";
//            String subject = "Thông báo hoàn tiền" + ma;
//            String templeHtml = "email/testNhacNho.html";
//            Context context = new Context();
//            context.setVariable("orderId", ma);
//            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(ma);
//            System.out.println("Đã có thông báo hoàn hàng2");
//            donHangService.sendEmailRefundWithHtml(email);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        donHangService.updateTrangThai(ma, trangThai);
//        return ResponseEntity.ok().build();
//    }


    @PutMapping("update-trang-thai-tra-hang")
    public ResponseEntity<Integer> updatTrangThaiTraHangAll(@RequestBody List<String> ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
        ma.forEach(m -> {
            try {
                donHangService.updateTrangThaiTraHang(m, trangThai);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-by-trangthai")
    public Page<DonHangDtoResponse> getChuaXacNhan(@RequestParam("trangThai") Integer trangThai,
                                                   @RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer limit,
                                                   @RequestParam(required = false)String sdt,
                                                   @RequestParam(defaultValue = "0")Integer loai) {
        return donHangEntityManager.getDonHangByTrangThai(trangThai, pageNumber , limit, sdt,loai);
    }


    @GetMapping("/{ma}")
    public ResponseEntity<DonHangDtoResponse> getByMa(@PathVariable("ma") String ma) {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(ma));
    }
}
