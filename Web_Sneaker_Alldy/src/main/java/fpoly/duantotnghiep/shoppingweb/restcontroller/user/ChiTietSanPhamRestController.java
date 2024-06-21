package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("chi-tiet-san-pham-user-restCtrl")
@RequestMapping("chi-tiet-san-pham/{maSP}")
public class ChiTietSanPhamRestController {

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("get-all")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> getAll(@PathVariable("maSP")String maSP){

        return ResponseEntity.ok(chiTietSanPhamService.getAllBySanPhamMa(maSP));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> getSoLuong(@PathVariable("id")String id){
        if(!chiTietSanPhamService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(chiTietSanPhamService.finById(id).getSoLuong());
    }
    @GetMapping("get-all-id")
    public ResponseEntity<?> getCtspById(@RequestParam("id") String id){
        if(!chiTietSanPhamService.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(chiTietSanPhamService.finById(id));
    }
}
