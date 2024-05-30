package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.repository.IThongBaoNhanRepository;
import fpoly.duantotnghiep.shoppingweb.service.IThongBaoNhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${admin.domain}/thong-bao")
public class ThongBaoRestController {
    @Autowired
    private IThongBaoNhanService thongBaoNhanService;
    @GetMapping("get-all/{idNguoiNhan}")
    public ResponseEntity<?> getAllThongBaoNhanByNguoiNhan(@PathVariable("idNguoiNhan")String idNguoiNhan){
        return ResponseEntity.ok(thongBaoNhanService.getAllByNguoiNhanId(idNguoiNhan));
    }
}
