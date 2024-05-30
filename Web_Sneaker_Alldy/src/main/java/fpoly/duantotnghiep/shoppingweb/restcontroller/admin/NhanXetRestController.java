package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.entitymanager.NhanXetEntityManager;
import fpoly.duantotnghiep.shoppingweb.service.INhanXetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("nhanXet-rest-admin")
@RequestMapping("${admin.domain}/nhan-xet")
public class NhanXetRestController {
    @Autowired
    private INhanXetService nhanXetService;
    @Autowired
    private NhanXetEntityManager nhanXetEntityManager;

    @GetMapping()
    public ResponseEntity<?> getBySanPham(@RequestParam String maSP,
                                          @RequestParam(defaultValue = "0")Integer page,
                                          @RequestParam(defaultValue = "5")Integer limit,
                                          @RequestParam(required = false)Float rate,
                                          @RequestParam(required = false)Boolean pheDuyet){

        if(rate != null && pheDuyet == null){
            return ResponseEntity.ok(nhanXetService.getAllNhanXetBySanPhamAndRate(page,limit,maSP,rate));
        }
        if(pheDuyet != null && rate == null){
            return ResponseEntity.ok(nhanXetService.getNhanXetBySanPhamAndPheDuyet(page,limit,maSP,pheDuyet));
        }
        if(pheDuyet != null && rate != null){
            return ResponseEntity.ok(nhanXetService.getNhanXetBySanPhamAndRate(page,limit,maSP,rate,pheDuyet));
        }

        return ResponseEntity.ok(nhanXetService.getNhanXetBySanPham(page,limit,maSP));
    }

    @GetMapping("avg-by-sanpham")
    public ResponseEntity<?> getAvgBySanPham(@RequestParam String maSP,
                                             @RequestParam(required = false)Boolean pheDuyet){
        if(pheDuyet != null){
            return ResponseEntity.ok(nhanXetService.getAvgRatingBySanPhamAndPheDuyet(maSP,pheDuyet));
        }
        return ResponseEntity.ok(nhanXetService.getAvgRatingBySanPham(maSP));
    }

    @GetMapping("avg-rates-by-sanpham")
    public ResponseEntity<?> getAvgRatesByMaSP(@RequestParam String maSP,
                                               @RequestParam(required = false)Boolean pheDuyet){
        if(pheDuyet!=null){
            return ResponseEntity.ok(nhanXetEntityManager.getAvgRatesByMaSPAndPheDuyet(maSP,pheDuyet));
        }
        return ResponseEntity.ok(nhanXetEntityManager.getAvgRatesByMaSP(maSP));
    }

    @PutMapping("phe-duyet/{id}")
    public ResponseEntity<?> pheDuyet(@PathVariable("id")String id,
                                      @RequestBody Boolean pheDuyet){
        if(!nhanXetService.existsById(id)){
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(nhanXetService.pheDuyetNhanXet(pheDuyet,id));
    }
}
