package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.config.security.Customer;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.*;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.GioHangServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class CheckoutController {
    @Autowired
    IKhachHangService khachHangService;
    @Autowired
    GioHangServiceImpl gioHangService;
    @Autowired
    IChiTietDonHangService chiTietDonHangService;
    @Autowired
    IDonHangService donHangService;
    @Autowired
    IKhachHangRepository khRepository;
    @Autowired
    VoucherRepository vc;
    @Autowired
    IChiTietSanPhamService sanPhamServic;
    @Autowired
    VoucherServiceImpl voucherService;
    @Autowired
    private VnPayServiceImpl vnPayService;

    @GetMapping("/check-out/voucher")
    public ResponseEntity<List<VoucherReponse>> checkOutVoucher() {
        return ResponseEntity.ok(voucherService.voucherEligible());
    }

    @GetMapping("get-khach-hang-thanh-toan")
    public ResponseEntity<KhachHangDtoResponse> getKhachHangThanhToan(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.ok(new KhachHangDtoResponse());
        }
        String userName = authentication.getName();
        return ResponseEntity.ok(khachHangService.findById(userName));
    }

    @GetMapping("/check-out/khach-hang-voucher")
    public ResponseEntity<?> findVoucherByKhachHang(Authentication authen) {
        if (authen == null) {
            return ResponseEntity.ok().build();
        }
        List<VoucherModel> voucherInKhach = khRepository.findById(authen.getName()).get().getVoucher();
        List<VoucherModel> voucherHT = new ArrayList<>();
        voucherInKhach.forEach(x -> {
            if (x.getTrangThaiXoa() == 0 && x.getTrangThai() == 0) {
                voucherHT.add(x);
            }
        });
        return ResponseEntity.ok(voucherHT);
    }

    @GetMapping("thanh-toan/{ma}")
    public Object ThanhToanHoaDon(HttpServletRequest request, @PathVariable("ma") String ma) throws MessagingException {
//        DonHangDtoResponse response = donHangService.checkOut(donHangDTORequest);
        DonHangDtoResponse response = donHangService.findByMa(ma);
        String diachi = response.getDiaChiChiTiet();
        DonHangReponseUser donHangReponseUser = donHangService.findByMaUser(ma);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(ma, baseUrl, (response.getTongTien().intValue() * 100) + "");
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", vnpayUrl);
//            return new ResponseEntity<String>(headers,HttpStatus.FOUND);
        Map<String, String> vnPayUrl = new HashMap<>();
        vnPayUrl.put("vnPayUrl", vnpayUrl);
        int paymentStatus = vnPayService.orderReturn(request, diachi);
        System.out.println(paymentStatus);
        System.out.println(vnpayUrl);
//            if (paymentStatus == 1){
//                donHangService.updateTrangThai1(response.getMa(), 2);
//            } else {
//                donHangService.updateTrangThai1(response.getMa(), 5);
//            }
        return ResponseEntity.ok(vnPayUrl);
    }

    @PostMapping("/check-out")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})//Khi có lỗi sẽ rollback
    public Object addHoaDon(@Valid @RequestBody DonHangDTORequest donHangDTORequest,
                            BindingResult result,
                            Authentication authentication, HttpServletRequest request) throws MessagingException {
//check phương thức thanh toán voucher
        if (donHangDTORequest.getVoucher() != null && !donHangDTORequest.getVoucher().isBlank()) {
            if (voucherService.findById1(donHangDTORequest.getVoucher()).getHinhThucThanhToan() != 2) {
                if (donHangDTORequest.getPhuongThucThanhToan() != voucherService.findById1(donHangDTORequest.getVoucher()).getHinhThucThanhToan()) {
                    result.rejectValue("tienGiam", "erTongTien", "Voucher không thể sử dụng cho hình thức thanh toán này");
                }
            }
        }
        if(!gioHangService.checkSoLuong()){
            result.addError(new FieldError("soLuongTon", "erSoLuong", "Số lượng sản phẩm đã hết"));
        }
        if (result.hasErrors()) {
            return ValidateUtil.getErrors(result);
        }
        if (authentication != null) {
            String khachHang = authentication.getName();
            KhachHangModel khachHangModel = new KhachHangModel();
            khachHangModel.setUsername(khachHang);
            donHangDTORequest.setNguoiSoHuu(khachHangModel);
        }
        if (donHangDTORequest.getPhuongThucThanhToan() == 1) {
            donHangDTORequest.setTrangThai(5);
        }
        donHangDTORequest.setNgayDatHang(new Date());
        donHangDTORequest.setMa(codeDonHang());
        DonHangDtoResponse response = donHangService.checkOut(donHangDTORequest);

        if (authentication != null && (donHangDTORequest.getVoucher() != null && !donHangDTORequest.getVoucher().isBlank())) {
            if (voucherService.findById1(donHangDTORequest.getVoucher()).getDoiTuongSuDung() == 1) {
                voucherService.deleteVoucherKhachHang(authentication.getName(), donHangDTORequest.getVoucher());
            }
        }

//        update cart and soluong voucher
        if (donHangDTORequest.getVoucher() != null && !donHangDTORequest.getVoucher().isBlank()) {
            VoucherModel voucherUpdateSL = voucherService.findById1(donHangDTORequest.getVoucher());
            if (voucherUpdateSL.getDoiTuongSuDung() == 0) {
                int soLuong = voucherService.findById(donHangDTORequest.getVoucher()).getSoLuong() - 1;
                Integer soLuongKiemTra = voucherService.upddateSoLuong(soLuong, donHangDTORequest.getVoucher());
                if (soLuongKiemTra == 0) {
                    voucherService.updateTrangThai(1, donHangDTORequest.getVoucher());
                }
            }
        }

        if (authentication != null && authentication.isAuthenticated()) {
            //        save chi tiết đơn hàng
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            if( gioHangService.laySpTrongGio().size()>=1){
                gioHangService.laySpTrongGio().stream().forEach(c -> {
                    ChiTietDonHangDTORequest donHangCT = new ChiTietDonHangDTORequest(response.getMa(), c.getId(), c.getSoLuong(), c.getDonGia(), c.getDonGiaSauGiam());
                    chiTietDonHangService.save(donHangCT);
                    ChiTietSanPhamDtoResponse chtsp = sanPhamServic.finById(c.getId());
                    Long sl = chtsp.getSoLuong() - c.getSoLuong();
                    sanPhamServic.updateSL(chtsp.getId(), sl);
                    gioHangService.removeAllProdcutInCart();
                });
            }else if( gioHangService.getCartFromDatabase(khachHang).size()>=1){
                gioHangService.getCartFromDatabase(khachHang).stream().forEach(c -> {
                    ChiTietDonHangDTORequest donHangCT = new ChiTietDonHangDTORequest(response.getMa(), c.getId(), c.getSoLuong(), c.getDonGia(), c.getDonGiaSauGiam());
                    chiTietDonHangService.save(donHangCT);
                    ChiTietSanPhamDtoResponse chtsp = sanPhamServic.finById(c.getId());
                    Long sl = chtsp.getSoLuong() - c.getSoLuong();
                    sanPhamServic.updateSL(chtsp.getId(), sl);
                    gioHangService.removeAllProductFromCart(khachHang);
                    //tets
                });

            }


        }else {

            gioHangService.laySpTrongGio().stream().forEach(c -> {
                ChiTietDonHangDTORequest donHangCT = new ChiTietDonHangDTORequest(response.getMa(), c.getId(), c.getSoLuong(), c.getDonGia(), c.getDonGiaSauGiam());
                chiTietDonHangService.save(donHangCT);
                ChiTietSanPhamDtoResponse chtsp = sanPhamServic.finById(c.getId());
                Long sl = chtsp.getSoLuong() - c.getSoLuong();
                sanPhamServic.updateSL(chtsp.getId(), sl);
                gioHangService.removeAllProdcutInCart();
            });
        }



        //Thanh Toán Online
        if (donHangDTORequest.getPhuongThucThanhToan() == 1) {
//            DonHangReponseUser donHangReponseUser = donHangService.findByMaUser(donHangDTORequest.getMa());
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(response.getMa(), baseUrl, donHangDTORequest.getTongTien());
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", vnpayUrl);
//            return new ResponseEntity<String>(headers,HttpStatus.FOUND);
            Map<String, String> vnPayUrl = new HashMap<>();
            vnPayUrl.put("vnPayUrl", vnpayUrl);
            int paymentStatus = vnPayService.orderReturn(request, donHangDTORequest.getDiaChiChiTiet());

//            if (paymentStatus == 1){
//                donHangService.updateTrangThai1(response.getMa(), 2);
//            } else {
//                donHangService.updateTrangThai1(response.getMa(), 5);
//            }
            return ResponseEntity.ok(vnPayUrl);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/using-voucher")
    public ResponseEntity<?> giaGiam(@RequestBody Map<String, Object> request, BindingResult result) {
        VoucherReponse voucherResponse = voucherService.findById(request.get("voucher").toString());
        Double tongThanhToan = Double.parseDouble(request.get("tongThanhToan").toString());
        Double giaGiam = null;
        if (voucherResponse != null) {
            giaGiam = voucherResponse.getMucGiam();
            if (voucherResponse.getLoaiMucGiam().equals("PHAN TRAM")) {
                giaGiam = tongThanhToan * (voucherResponse.getMucGiam()) / 100;
                if (giaGiam > voucherResponse.getMucGiamToiDa()) {
                    giaGiam = voucherResponse.getMucGiamToiDa();
                }
            }
        }
        return ResponseEntity.ok(giaGiam);
    }


    private String codeDonHang() {
        final String ALLOWED_CHARACTERS = "asdfghjklqwertyuiopzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final int CODE_LENGTH = 8;

        StringBuilder code = new StringBuilder();

        Random random = new Random();
        int maxIndex = ALLOWED_CHARACTERS.length();

        // Sinh ngẫu nhiên các ký tự cho mã
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(maxIndex);
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

}
