package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.NhanXetEntityManager;
import fpoly.duantotnghiep.shoppingweb.service.INhanXetService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("nhanXet-rest-user")
@RequestMapping("nhan-xet")
public class NhanXetRestController {
    @Autowired
    private INhanXetService nhanXetService;
    @Autowired
    private NhanXetEntityManager nhanXetEntityManager;

    @GetMapping()
    public ResponseEntity<?> getBySanPham(@RequestParam String maSP,
                                          @RequestParam(defaultValue = "0")Integer page,
                                          @RequestParam(defaultValue = "5")Integer limit,
                                          @RequestParam(required = false)Float rate){

        if(rate != null){
            return ResponseEntity.ok(nhanXetService.getNhanXetBySanPhamAndRate(page,limit,maSP,rate,true));
        }

        return ResponseEntity.ok(nhanXetService.getNhanXetBySanPhamAndPheDuyet(page,limit,maSP,true));
    }

    @PostMapping()
    public ResponseEntity<?> add(@Valid @RequestBody NhanXetDtoRequest nhanXetDtoRequest,
                                 BindingResult result,
                                 Authentication authentication){

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        nhanXetService.add(nhanXetDtoRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> update(@Valid @RequestBody NhanXetDtoRequest nhanXetDtoRequest,
                                 BindingResult result,
                                 Authentication authentication){

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        nhanXetService.update(nhanXetDtoRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("avg-by-sanpham")
    public ResponseEntity<?> getAvgBySanPham(@RequestParam String maSP){
        return ResponseEntity.ok(nhanXetService.getAvgRatingBySanPhamAndPheDuyet(maSP,true));
    }

    @GetMapping("avg-rates-by-sanpham")
    public ResponseEntity<?> getAvgRatesByMaSP(@RequestParam String maSP){
        return ResponseEntity.ok(nhanXetEntityManager.getAvgRatesByMaSPAndPheDuyet(maSP,true));
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id")String id){
        return ResponseEntity.ok(nhanXetService.getByid(id));
    }

}
