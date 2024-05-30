package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import fpoly.duantotnghiep.shoppingweb.repository.sizerepo;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("${admin.domain}/san-pham/{maSP}")
public class ChiTietSanPhamRestController {

    @Autowired
    private sizerepo sizerepo;
    @Autowired
    private IChiTietSanPhamService sanPhamService;

    @Autowired
    private IChiTietDonHangService chiTietDonHangService;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("get-all")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> getAll(@PathVariable("maSP")String maSP){

        return ResponseEntity.ok(chiTietSanPhamService.getAllBySanPhamMa(maSP));
    }

    @GetMapping("test")
    public List<SizeModel> test(@PathVariable("maSP")String maSP){
        return sizerepo.getAllNotInSanPham(maSP);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody ChiTietSanPhamDtoRequest model,
                                 BindingResult result,
                                 @PathVariable("maSP")String maSP,
                                 @RequestParam("sizes")List<Float> size){
        if(size.size()==0){
            result.addError(new FieldError("eSize","eSize","Vui lòng chọn size"));
            if(!result.hasErrors()) ValidateUtil.getErrors(result);
        }
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        model.setSanPham(maSP);
        return ResponseEntity.ok(chiTietSanPhamService.saveAll(size,model));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!chiTietSanPhamService.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        chiTietSanPhamService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody ChiTietSanPhamDtoRequest model,
                                    BindingResult result){

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        return ResponseEntity.ok(chiTietSanPhamService.update(model));
    }

    @GetMapping("kiem-tra-so-luong/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> kiemTraSoLuong(@PathVariable("id") String id,
                                            @RequestParam("soLuong")Long soLuong,
                                            @RequestParam(value = "idCTDH",required = false)String idCTDH){
        if(soLuong<=0){
            return ResponseEntity.badRequest().build();
        }

        if(idCTDH.length()>0){
            ChiTietDonHangDtoResponse chiTietDonHangDtoResponse = chiTietDonHangService.findById(idCTDH);
            soLuong -= chiTietDonHangDtoResponse.getSoLuong();
            System.out.println("asad");
        }
        System.out.println("so luong"+soLuong);


        if(!chiTietSanPhamService.checkSoLuongSP(id,soLuong)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-not-in-don-hang/{maDonHang}")
    public ResponseEntity<?> getNotInDonHang(@PathVariable("maDonHang") String maDonHang){
        return ResponseEntity.ok(chiTietSanPhamService.getChiTietSanPhamNotInDonHang(maDonHang));
    }

    @GetMapping("get-all-ctsp")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> getAllCTS(@RequestParam(required = false) String keyWord){
        System.out.println(keyWord);
        if(keyWord != null){
            if(keyWord.trim().length()>0) return ResponseEntity.ok(chiTietSanPhamService.getBySanPhamIdOrNameContais(keyWord));
        }
        return ResponseEntity.ok(chiTietSanPhamService.fillAllChiTietSP());
    }

    @GetMapping("getSoLuong/{id}")
    public ResponseEntity<?> getSoLuong(@PathVariable("id")String id){
        Map<String,Long> result = new HashMap<>();
        Long soLuong = chiTietSanPhamService.finById(id).getSoLuong();
        result.put("soLuong",soLuong);
        return ResponseEntity.ok(result);
    }
}
