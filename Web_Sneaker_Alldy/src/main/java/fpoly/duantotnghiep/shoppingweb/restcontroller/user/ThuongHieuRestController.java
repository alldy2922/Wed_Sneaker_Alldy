package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("thuong-hieu-user")
@RequestMapping("thuong-hieu")
public class ThuongHieuRestController {
    @Autowired
    private IThuongHieuService service;
    @Autowired
    private IDongSanPhamService dongSanPhamService;
    @GetMapping("/find-all")
    public List<ThuongHieuDtoResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id")String id){
        if(dongSanPhamService.existsById(id)){
            return ResponseEntity.ok(dongSanPhamService.findById(id));
        }else{
            return ResponseEntity.ok(service.findById(id));
        }

    }

    @GetMapping("ban-chay")
    public ResponseEntity<?> getBanChay(){
        return ResponseEntity.ok(service.getThuongHieuBanChay());
    }
}
