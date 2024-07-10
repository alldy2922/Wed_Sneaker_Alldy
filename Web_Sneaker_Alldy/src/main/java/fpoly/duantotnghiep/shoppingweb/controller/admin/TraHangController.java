package fpoly.duantotnghiep.shoppingweb.controller.admin;


import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.entitymanager.DonHangEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (donHangModel.getLoai() == 1 && trangThai == 7) {
            if (donHangModel.getPhuongThucThanhToan() == false) {
                String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
                String vnpayUrl = vnPayService.createOrder(donHangModel.getMa(), baseUrl, donHangModel.getTongTien().multiply(BigDecimal.valueOf(100)).intValue() + "");
                Map<String, String> vnPayUrl = new HashMap<>();
                vnPayUrl.put("vnPayUrl", vnpayUrl);
                return ResponseEntity.ok(vnPayUrl);
            }
        }

        donHangService.updateTrangThaiTraHang(ma, trangThai);
        String email = "heinikens0408@gmail.com";
        String subject = "Thông báo hoàn tiền";
        String templeHtml = "testNhacNho.html";
        Context context = new Context();
        context.setVariable("orderId", ma);
        List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(ma);
        donHangService.sendEmailRefundWithHtml(email,subject,templeHtml,context, 10, TimeUnit.SECONDS,lstSanPham);

        return ResponseEntity.ok().build();
    }



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
}
