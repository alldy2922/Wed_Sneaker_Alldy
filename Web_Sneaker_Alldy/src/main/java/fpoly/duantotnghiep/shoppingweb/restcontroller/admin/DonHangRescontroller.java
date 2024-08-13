package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.config.security.EmailParameters;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.*;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.DonHangEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamDoi;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangTraRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.DonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.LichSuThaoTacServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@RestController("don-hang-restCtrl-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangRescontroller {
    @Autowired
    private IDonHangService donHangService;
    @Autowired
    private DonHangService IdonHangService;
    @Autowired
    private DonHangEntityManager donHangEntityManager;
    @Autowired
    private IChiTietDonHangService chiTietDonHangService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private VnPayServiceImpl vnPayService;
    @Autowired
    private IDonHangResponsitory donHangResponsitory;
    @Autowired
    private IDonHangTraRepository donHangTraRepository;
    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private LichSuThaoTacServiceImpl lichSuThaoTacService;


    @GetMapping("get-by-trangthai")
    public Page<DonHangDtoResponse> getChuaXacNhan(@RequestParam("trangThai") Integer trangThai,
                                                   @RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer limit,
                                                   @RequestParam(required = false)String sdt,
                                                   @RequestParam(defaultValue = "0")Integer loai) {
        return donHangEntityManager.getDonHangByTrangThai(trangThai, pageNumber , limit, sdt,loai);
    }

    @GetMapping("get-by-trangthai-tra")
    public Page<?>getTrangThaiTra(
            @RequestParam(value = "trangThai", required = false) Integer trangThai,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false)String sdt) {

        return donHangEntityManager.getChiTietSanPhamTra(trangThai, pageNumber , limit,sdt);
    }
    @GetMapping("get-by-trangthai-doi")
    public Page<?>getTrangThaiDoi(
            @RequestParam(value = "trangThai", required = false) Integer trangThai,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false)String sdt) {

        return donHangEntityManager.getChiTietSanPhamDoi(trangThai, pageNumber , limit,sdt);
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangDtoResponse> getByMa(@PathVariable("ma") String ma) {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(ma));
    }

    @GetMapping("update-trang-thai/{ma}")
    public ResponseEntity<?> updatTrangThai(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication) throws MessagingException {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }

        DonHangModel donHangModel = donHangResponsitory.findById(ma).get();
            if(donHangModel.getLoai()==1 && trangThai==4){
                if (donHangModel.getPhuongThucThanhToan() == false) {
                    String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
                    String vnpayUrl = vnPayService.createOrder(donHangModel.getMa(), baseUrl, donHangModel.getTongTien().multiply(BigDecimal.valueOf(100)).intValue()+"");
                    Map<String, String> vnPayUrl = new HashMap<>();
                    vnPayUrl.put("vnPayUrl", vnpayUrl);
                    return ResponseEntity.ok(vnPayUrl);
                }
            }
        donHangService.updateTrangThai(ma, trangThai);
        if(trangThai==1){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Online: "+ ma);
        }
        else if(trangThai==3){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Chuyển Giao Đơn Hàng Online: "+ ma);

        }     else if(trangThai==4){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Hoàn Thành Đơn Hàng Online: "+ ma);
 
        }     else if(trangThai==7){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Hàng Hoàn: "+ ma);

        }   else if(trangThai==8){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Hoàn Tiền Đơn Hàng Hoàn: "+ ma);

        }
        if (trangThai == 6 || trangThai == 7) {
            //Thiết lập tham số
            String email = "alldystoresneaker@gmail.com";
            donHangModel = donHangResponsitory.findById(ma).get();
            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(ma);
            try {
                EmailParameters.setParameters(email, ma, lstSanPham);
                donHangService.sendEmailRefundWithHtml();

                System.out.println("Đã gửi 1");

                return ResponseEntity.ok().build();
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Failed to send email");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Failed to update order status");
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("update-trang-thai-tra/{ma}")
    public ResponseEntity<?> updatTrangThaiTra(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication) throws MessagingException {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }


        donHangService.updateTrangThaiTra(ma, trangThai);
        if(trangThai==2){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Hàng Hoàn: "+ ma);

        }   else if(trangThai==3){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Hoàn Tiền Đơn Hàng Hoàn: "+ ma);

        }
        return ResponseEntity.ok().build();
    }
    @GetMapping("update-trang-thai-doi/{ma}")
    public ResponseEntity<?> updatTrangThaiDoi(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication)
            throws MessagingException {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }


        donHangService.updateTrangThaiDoi(ma, trangThai);
        if (trangThai == 2) {
            lichSuThaoTacService.addActivity(authentication.getName(), "Tài Khoản: " + authentication.getName() + " Đã Xác Nhận Đơn Hàng Đổi: " + ma);
        }else if(trangThai==3){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Chuyển Giao Đơn Hàng Đổi: "+ ma);
        }else if(trangThai==4){
            lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Đổi Sản Phẩm Đơn Hàng Đổi: "+ ma);

        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-trang-thai-tra")
    public ResponseEntity<Integer> updatTrangThaiTraAll(@RequestBody List<String> ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication) throws MessagingException {
        ma.forEach(m -> {
            try {
                donHangService.updateTrangThaiTra(m, trangThai);
                if(trangThai==2){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Hàng Hoàn: "+ ma);

                }   else if(trangThai==3){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Hoàn Tiền Đơn Hàng Hoàn: "+ ma);

                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-trang-thai-doi")
    public ResponseEntity<Integer> updatTrangThaiDoiAll(@RequestBody List<String> ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication) throws MessagingException {
        ma.forEach(m -> {
            try {
                donHangService.updateTrangThaiDoi(m, trangThai);
                if(trangThai==2){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Hàng Đổi: "+ ma);

                } else if(trangThai==3){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Chuyển Giao Đơn Hàng Đổi: "+ ma);
                }  else if(trangThai==4){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Đổi Sản Phẩm Đơn Hàng Đổi: "+ ma);

                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-trang-thai")
    public ResponseEntity<Integer> updatTrangThaiAll(@RequestBody List<String> ma, @RequestParam("trangThai") Integer trangThai,Authentication authentication) throws MessagingException {
        ma.forEach(m -> {
            try {
                donHangService.updateTrangThai(m, trangThai);
                if(trangThai==1){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Online: "+ m);
                }
                else if(trangThai==3){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Chuyển Giao Đơn Hàng Online: "+ m);

                }     else if(trangThai==4){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Hoàn Thành Đơn Hàng Online: "+ m);

                }
                else if(trangThai==7){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Đơn Hàng Hoàn: "+ m);

                }   else if(trangThai==8){
                    lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Hoàn Tiền Đơn Hàng Hoàn: "+ m);

                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @PutMapping("/huy-don-hang")
    public ResponseEntity<Integer> huyDonHang(@RequestBody List<String> ma, @RequestParam("lyDo") String lyDo,Authentication authentication) throws MessagingException {
        donHangService.huyDonHang(ma, lyDo);

        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Xác Nhận Hủy Đơn Hàng Online: "+ ma);


        return ResponseEntity.ok().build();
    }

    @PutMapping("/tra-don-hang")
    public ResponseEntity<Integer> traDonHang(@RequestBody List<String> ma, @RequestParam("lyDoTraHang") String lyDoTraHang,Authentication authentication) throws MessagingException {
        donHangService.huyTraHang(ma, lyDoTraHang);
        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Từ Chối Đơn Hàng Hoàn: "+ ma);
        return ResponseEntity.ok().build();
    }
//hủy trả hàng đã sửa
    @PutMapping("/tra-don-hang-test")
    public ResponseEntity<Integer> huyTraDonHangNew(@RequestBody List<String> ma, @RequestParam("lyDoTraHang") String lyDoTraHang,Authentication authentication) throws MessagingException {
        donHangService.huyTraHangNew(ma, lyDoTraHang);
        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Từ Chối Đơn Hàng Hoàn: "+ ma);
        return ResponseEntity.ok().build();
    }
//hủy đổi hàng
    @PutMapping("/doi-don-hang")
    public ResponseEntity<Integer> huyDoiHang(@RequestBody List<String> ma, @RequestParam("lyDoDoiHang") String lyDoDoiHang,Authentication authentication) throws MessagingException {
        donHangService.huyDoiHang(ma, lyDoDoiHang);
        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Đã Từ Chối Đơn Đổi Hàng: "+ ma);
        return ResponseEntity.ok().build();
    }


//    @GetMapping("update-trang-thai/{ma}")
//    public ResponseEntity<?> updatTrangThai(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
//        if (!donHangService.existsByMa(ma)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        DonHangModel donHangModel = donHangResponsitory.findById(ma).get();
//        if (donHangModel.getLoai() == 1 && trangThai == 4) {
//            if (donHangModel.getPhuongThucThanhToan() == false) {
//                String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
//                String vnpayUrl = vnPayService.createOrder(donHangModel.getMa(), baseUrl, donHangModel.getTongTien().multiply(BigDecimal.valueOf(100)).intValue() + "");
//                Map<String, String> vnPayUrl = new HashMap<>();
//                vnPayUrl.put("vnPayUrl", vnpayUrl);
//                return ResponseEntity.ok(vnPayUrl);
//            }
//        }
//        donHangService.updateTrangThaiTraHang(ma, trangThai);
//
//        if (trangThai == 6 || trangThai == 7) {
//            //Thiết lập tham số
//            String email = "alldystoresneaker@gmail.com";
//            donHangModel = donHangResponsitory.findById(ma).get();
//            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(ma);
//            try {
//                EmailParameters.setParameters(email, ma, lstSanPham);
//                donHangService.sendEmailRefundWithHtml();
//
//                System.out.println("Đã gửi 1");
//
//                return ResponseEntity.ok().build();
//            } catch (MessagingException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(500).body("Failed to send email");
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(500).body("Failed to update order status");
//            }
//
//
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("update-trang-thai-email")
//    public ResponseEntity<?> updatTrangThaiAndSendMail(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
//        try {
//            if (trangThai == 6 || trangThai == 7) {
//                donHangService.updateTrangThaiTraHang(ma, trangThai);
//            }
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gửi Email thất bại");
//        }
//        return ResponseEntity.ok().build();
//    }


    @PutMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateDonHang(@Valid @RequestPart("donHang") DonHangDTORequest request,
                                           BindingResult result,
                                           @RequestPart("chiTietDonHang") List<ChiTietDonHangDTORequest> products,
                                           @RequestParam("lyDoThayDoi") String lyDoThayDoi,
                                           Authentication authentication) {
        if(products.size()<=0){
            result.addError(new FieldError("soLuongSP","soLuongSP","Không có sản phẩm trong đơn hàng"));
        }else{
            if(request.getVoucher() != null && !request.getVoucher().isBlank()){
                VoucherReponse voucherReponse = voucherService.findById(request.getVoucher());
                BigDecimal tongTien = BigDecimal.valueOf(0);
                for (var p: products ) {
                    tongTien = tongTien.add(p.getDonGiaSauGiam());
                }

                if(tongTien.compareTo(BigDecimal.valueOf(voucherReponse.getGiaTriDonHang())) < 0){
                    NumberFormat numberFM = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    result.addError(new FieldError("soLuongSP","soLuongSP","Voucher đã sử dụng chỉ áp dụng cho đơn hàng từ " + numberFM.format(voucherReponse.getGiaTriDonHang()) + " đ" ) );
                }
            }
        }

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        if (!donHangService.existsByMa(request.getMa())) {
            return ResponseEntity.notFound().build();
        }

        request.setNhanVien(authentication.getName());
        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Vừa Cập Nhât Đơn Hàng: "+ request.getMa());


        return ResponseEntity.ok(donHangService.updateDonHang(request, products, lyDoThayDoi));
    }
    @PutMapping("doi")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateDonHangDoi(@Valid @RequestPart("donHang") DonHangDTORequest request,
                                              BindingResult result,
                                              @RequestPart("chiTietDonHang") List<ChiTietDonHangDTORequest> products,
                                              @RequestParam("lyDoThayDoi") String lyDoThayDoi,
                                              Authentication authentication) {
        if(products.size()<=0){
            result.addError(new FieldError("soLuongSP","soLuongSP","Không có sản phẩm trong đơn hàng"));
        }else{
            if(request.getVoucher() != null && !request.getVoucher().isBlank()){
                VoucherReponse voucherReponse = voucherService.findById(request.getVoucher());
                BigDecimal tongTien = BigDecimal.valueOf(0);
                for (var p: products ) {
                    tongTien = tongTien.add(p.getDonGiaSauGiam());
                }

                if(tongTien.compareTo(BigDecimal.valueOf(voucherReponse.getGiaTriDonHang())) < 0){
                    NumberFormat numberFM = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    result.addError(new FieldError("soLuongSP","soLuongSP","Voucher đã sử dụng chỉ áp dụng cho đơn hàng từ " + numberFM.format(voucherReponse.getGiaTriDonHang()) + " đ" ) );
                }
            }
        }

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        if (!donHangService.existsByMa(request.getMa())) {
            return ResponseEntity.notFound().build();
        }

        request.setNhanVien(authentication.getName());
        lichSuThaoTacService.addActivity(authentication.getName(),"Tài Khoản: "+ authentication.getName()+" Vừa Cập Nhât Đơn Hàng: "+ request.getMa());


        return ResponseEntity.ok(donHangService.updateDonHangDoi(request, products, lyDoThayDoi));
    }
    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> themDonHang(@Valid @RequestPart("donHang") DonHangDTORequest request,
                                           BindingResult result,
                                           @RequestPart("chiTietDonHang") List<ChiTietDonHangDTORequest> products,
                                            Authentication authentication) {
        if(products.size()<=0){
            result.addError(new FieldError("soLuongSP","soLuongSP","Không có sản phẩm trong đơn hàng"));
        }

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        String maDH = "";
        while (true){
            if(donHangService.existsByMa(codeDonHang())){
                continue;
            }else {
                maDH = codeDonHang();
                break;
            }
        }
        if(request.getLoai()!=null){
            if(request.getLoai()==1){
                request.setNhanVien(authentication.getName());
            }
        }
        request.setMa(maDH);
        donHangService.themDonHangAdmin(request,products);

        if(request.getLoai()!=null){
            if(request.getLoai()==1 && request.getTrangThai()==4){
                if (request.getPhuongThucThanhToan() == 1) {
                    String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
                    String vnpayUrl = vnPayService.createOrder(maDH, baseUrl, request.getTongTien());
                    Map<String, String> vnPayUrl = new HashMap<>();
                    vnPayUrl.put("vnPayUrl", vnpayUrl);
                    return ResponseEntity.ok(vnPayUrl);
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("get-ctsp-tra")
    public List<DonHangTraDTOReponse> getCtspTra(@RequestParam("ma")String ma) {

        return IdonHangService.getAllByDonHangTra(ma);
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
    @GetMapping("get-ctsp-doi")
    public List<DonHangDoiDTOReponse> getCtspDoi(@RequestParam("ma")String ma) {

        return IdonHangService.getAllByDonHangDoi(ma);
    }

//    @GetMapping("san-pham-doi")
//    public ResponseEntity<?> getQuantityOrderDoi(@RequestParam("trangThai")Integer trangThai){
//
//
//        Map<String,List> result = new HashMap<>();
//        result.put("sanPhamDoi",donHangEntityManager.getChiTietSanPhamDoi(trangThai));
//
//        return ResponseEntity.ok(result);
//    }
    @GetMapping("san-pham-doi")
    public ResponseEntity<?> getQuantityOrderDoi(
            @RequestParam("trangThai") Integer trangThai,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sdt", required = false) String sdt) {

        // Gọi phương thức getChiTietSanPhamDoi với các tham số mới
        Page<ChiTietSanPhamDoi> resultPage = donHangEntityManager.getChiTietSanPhamDoi(trangThai, page, limit, sdt);

        // Trả về kết quả dưới dạng ResponseEntity với thông tin phân trang
        Map<String, Object> response = new HashMap<>();
        response.put("sanPhamDoi", resultPage.getContent());
        response.put("currentPage", resultPage.getNumber());
        response.put("totalItems", resultPage.getTotalElements());
        response.put("totalPages", resultPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
    @GetMapping("get-sum-by-ma")
    public ResponseEntity<?> getSumByMa(@RequestParam("ma") String ma) {
        return ResponseEntity.ok(IdonHangService.getSoLuongSanPhamHoaDon(ma));
    }



}
