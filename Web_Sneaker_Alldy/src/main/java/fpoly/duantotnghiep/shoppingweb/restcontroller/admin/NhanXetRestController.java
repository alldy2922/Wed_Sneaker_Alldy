package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

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


}
