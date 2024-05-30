package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.ThuongHieuDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${admin.domain}/thuong-hieu")
public class ThuongHieuRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IThuongHieuService service;
    @Autowired
    private IDongSanPhamService dongSanPhamService;
    @GetMapping("/find-all")
    public List<ThuongHieuDtoResponse> findAll(){
        return service.findAll();
    }
    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody ThuongHieuDtoRequest thuonghieu ){
        return ResponseEntity.ok(service.save(thuonghieu));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ThuongHieuDtoRequest thuonghieu, @PathVariable("id") String id){
//        service.findById(id);
//        ThuongHieuDtoResponse model = service.save(thuonghieu);
//        return ResponseEntity.ok(model);
        boolean exitst = service.existsById(id);
        if (exitst) {
            ThuongHieuDtoResponse thuongHieuDtoResponse = service.findById(id);
            thuonghieu.setNgayTao(thuongHieuDtoResponse.getNgayTao());
            thuonghieu.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Oke", "Sửa thành công", service.save(thuonghieu))
            );
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("Found", "Không tìm thấy", "")
        );
    }
    @DeleteMapping("deletes")
    public ResponseEntity<?> deletes(@RequestBody List<String> id){
        id.forEach(idTH -> dongSanPhamService.deleteByThuongHieu(idTH));
        service.deleteByIds(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ThuongHieuDtoResponse findById(@PathVariable("id") String id){
        return service.findById(id);
    }

}
