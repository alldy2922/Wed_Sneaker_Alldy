package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.config.security.Customer;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class GioHangRestController {
    @Autowired
    private IGioHangService service;
    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

@GetMapping("/find-all")
public ResponseEntity<List<GioHangDtoReponse>> getCartContents(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
        // Chưa đăng nhập, lấy giỏ hàng từ session
        List<GioHangDtoReponse> cartContents = service.laySpTrongGio();
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    } else {
        // Đã đăng nhập, lấy giỏ hàng từ database
        Customer customer = (Customer) authentication.getPrincipal();
        KhachHangModel khachHang = customer.getKhachHangModel();
        List<GioHangDtoReponse> cartContents1 = service.laySpTrongGio();
        List<GioHangDtoReponse> cartContents = service.getCartFromDatabase(khachHang);
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    }
}
    @PostMapping("add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam(value = "idCTSP", required = false) String idCTSP,
                                       @RequestParam("sl") Integer sl, Authentication authentication) {
        Map<String, String> er = new HashMap<>();
        Integer soLuongCheck = sl;

        if (idCTSP == null || idCTSP.length() == 0) {
            er.put("eSize", "Vui lòng chọn size");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        if (service.checkSanPhamTrongGio(idCTSP)) {
            soLuongCheck += service.getSoLuong(idCTSP);
        }

        if (sl <= 0) {
            er.put("eSize", "Số lượng không hợp lệ!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        if (!chiTietSanPhamService.checkSoLuongSP(idCTSP, Long.valueOf(soLuongCheck))) {
            er.put("eSize", "Số lượng không hợp lệ!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            service.addProductToCart(service.getSessionCart(), idCTSP, sl);

            return ResponseEntity.ok(service.laySpTrongGio());
        } else {
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            service.addProductToCart(khachHang, idCTSP, sl);
            service.addOrUpdateToCart(idCTSP,sl);
            return ResponseEntity.ok(service.getCartFromDatabase(khachHang));
        }
    }
    @PostMapping("mua-ngay")
    public ResponseEntity<?> muaNgay(@RequestParam(value = "idCTSP",required = false)String idCTSP,
                                       @RequestParam("sl")Integer sl, Authentication authentication){
        Map<String,String> er = new HashMap<>();
        Integer soLuongCheck = sl;

        if(idCTSP==null || idCTSP.length()==0){
            er.put("eSize","Vui lòng chọn size");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
        if(service.checkSanPhamTrongGio(idCTSP)){
            soLuongCheck += service.getSoLuong(idCTSP);
        }
        if(!chiTietSanPhamService.checkSoLuongSP(idCTSP, Long.valueOf(soLuongCheck))){
            er.put("eSize","Số lượng không hợp lệ!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            // Nếu chưa đăng nhập, cập nhật trong session
            if (service.laySpTrongGio().size()>=1){
                service.removeAllProdcutInCart();
            }
            service.addOrUpdateToCart(idCTSP,sl);
            return ResponseEntity.ok(service.laySpTrongGio());
        } else {
            if (service.laySpTrongGio().size()>=1){
                service.removeAllProdcutInCart();
            }
            // Nếu đã đăng nhập, cập nhật trong cơ sở dữ liệu
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            service.addProductToCart(khachHang,idCTSP,sl);
            service.addOrUpdateToCart(idCTSP,sl);
            return ResponseEntity.ok(service.laySpTrongGio());
        }
    }
    @PutMapping("update-sl/{idCTSP}/{sl}")
    public ResponseEntity<?> updateSL(@PathVariable("idCTSP") String idCTSP, @PathVariable("sl") Integer sl, Authentication authentication) {
        Map<String, String> er = new HashMap<>();
        if (!chiTietSanPhamService.checkSoLuongSP(idCTSP, Long.valueOf(sl))) {
            er.put("sl", "Số lượng trong kho không đủ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            // Nếu chưa đăng nhập, cập nhật trong session
            service.updateSoLuong(idCTSP, sl);
            return ResponseEntity.ok(service.laySpTrongGio());
        } else {
            // Nếu đã đăng nhập, cập nhật trong cơ sở dữ liệu
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            service.updateProductQuantityInDatabase(khachHang,idCTSP,sl);
            return ResponseEntity.ok(service.getCartFromDatabase(khachHang));
        }
    }
    @DeleteMapping("/remove/{key}")
    public ResponseEntity<?> removeProductInCart(@PathVariable("key") String idCTSP, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Người dùng đã đăng nhập
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            service.removeProductFromCart(khachHang, idCTSP);
            return ResponseEntity.ok(service.getCartFromDatabase(khachHang));
        } else {
            // Người dùng chưa đăng nhập
            service.removeProductInCart(idCTSP);
            return ResponseEntity.ok(service.laySpTrongGio());
        }
    }
    @DeleteMapping("removeAll")
    public ResponseEntity<?> removeAllProducts(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Người dùng đã đăng nhập
            Customer customer = (Customer) authentication.getPrincipal();
            KhachHangModel khachHang = customer.getKhachHangModel();
            service.removeAllProductFromCart(khachHang);
            return ResponseEntity.ok(service.getCartFromDatabase(khachHang));
        } else {
            // Người dùng chưa đăng nhập
            service.removeAllProdcutInCart();
            return ResponseEntity.ok(service.laySpTrongGio());
        }
    }
}
