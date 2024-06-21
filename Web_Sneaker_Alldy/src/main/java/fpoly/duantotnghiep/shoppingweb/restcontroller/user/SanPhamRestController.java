package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("san-pham-user")
@RequestMapping("/san-pham")
public class SanPhamRestController {

    @Autowired
    private ISanPhamService sanPhamService;

    private Page<SanPhamDtoResponse> page = null;

    @GetMapping("get-all")
    public ResponseEntity<Page<SanPhamDtoResponse>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "8") Integer limit) {

        return ResponseEntity.ok(sanPhamService.paginationInUser(pageNumber, limit));
    }

    @GetMapping("san-pham-tuong-tu/{ma}")
    public ResponseEntity<List<SanPhamDtoResponse>> getSpTuongTu(@PathVariable("ma")String ma){
        return ResponseEntity.ok(sanPhamService.getSanPhamTuongTu(ma));
    }


    @PostMapping("filter")
    public ResponseEntity<Page<SanPhamDtoResponse>> filter(@RequestBody SanPhamDtoFilter sanPhamDtoFilter,
                                                           @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "8") Integer limit) {
        return ResponseEntity.ok(sanPhamService.filterInUser(sanPhamDtoFilter,pageNumber,limit));
    }

    @GetMapping("{ma}")
    public ResponseEntity<?> detail(@PathVariable("ma")String ma){
        if(!sanPhamService.existsById(ma)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sanPhamService.findByMa(ma));
    }

    @GetMapping("ban-chay")
    public ResponseEntity<?> getBanChay(@RequestParam(defaultValue = "4")Integer limit){
        return ResponseEntity.ok(sanPhamService.getBanChay(limit));
    }

    @GetMapping("khuyen-mai")
    public ResponseEntity<?> getKhuyenMai(@RequestParam(defaultValue = "4")Integer limit){
        return ResponseEntity.ok(sanPhamService.getKhuyenMai(limit));
    }

    @GetMapping("sp-khuyen-mai")
    public ResponseEntity<?> getKhuyenMaiPhanTrang(@RequestParam(defaultValue = "8")Integer limit,
                                                   @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber){
        return ResponseEntity.ok(sanPhamService.getKhuyenMai(limit,pageNumber));
    }


    @GetMapping("san-pham-moi")
    public ResponseEntity<?> getSanPhamMoi(@RequestParam(defaultValue = "4")Integer limit){
        return ResponseEntity.ok(sanPhamService.getSanPhamMoi(limit));
    }


}
