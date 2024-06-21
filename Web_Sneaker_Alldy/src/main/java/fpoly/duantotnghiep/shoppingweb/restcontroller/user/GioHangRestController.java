package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;
    @GetMapping("/find-all")
    public ResponseEntity<List<GioHangDtoReponse>> getCartContents() {
        List<GioHangDtoReponse> cartContents = service.laySpTrongGio();
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    }

    @PostMapping("add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam(value = "idCTSP",required = false)String idCTSP,
                                             @RequestParam("sl")Integer sl){
        Map<String,String> er = new HashMap<>();
        Integer soLuongCheck = sl;

        if(idCTSP==null || idCTSP.length()==0){
            er.put("eSize","Vui lòng chọn size");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
        System.out.println(idCTSP);
        if(service.checkSanPhamTrongGio(idCTSP)){
            soLuongCheck += service.getSoLuong(idCTSP);
        }
        if(sl <= 0){
            er.put("eSize","Số lượng không hợp lệ!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
        if(!chiTietSanPhamService.checkSoLuongSP(idCTSP, Long.valueOf(soLuongCheck))){
            er.put("eSize","Số lượng không hợp lệ!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
        service.addOrUpdateToCart(idCTSP,sl);
        return ResponseEntity.ok(service.laySpTrongGio());
    }
    @PostMapping("mua-ngay")
    public ResponseEntity<?> muaNgay(@RequestParam(value = "idCTSP",required = false)String idCTSP,
                                       @RequestParam("sl")Integer sl){
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
        if (service.laySpTrongGio().size()>=1){
            service.removeAllProdcutInCart();
        }
        service.addOrUpdateToCart(idCTSP,sl);
        return ResponseEntity.ok(service.laySpTrongGio());
    }
    @PutMapping("update-sl/{idCTSP}/{sl}")
    public ResponseEntity<?> updateSL(@PathVariable("idCTSP")String idCTSP,@PathVariable("sl")Integer sl){
        Map<String,String> er = new HashMap<>();
        if(!chiTietSanPhamService.checkSoLuongSP(idCTSP, Long.valueOf(sl))){
            er.put("sl","Số lượng trong kho không đủ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
        service.updateSoLuong(idCTSP,sl);
        return ResponseEntity.ok(service.laySpTrongGio());
    }
    @DeleteMapping("/remove/{key}")
    public  List<GioHangDtoReponse>removeProductInCart(@PathVariable("key")String idCTSP){
        service.removeProductInCart(idCTSP);
        return service.laySpTrongGio();
    }
    @DeleteMapping("/removeAll")
    public  ResponseEntity<List<GioHangDtoReponse>> removeProductInCart(){
        service.removeAllProdcutInCart();
        return ResponseEntity.ok(service.laySpTrongGio());
    }


}
