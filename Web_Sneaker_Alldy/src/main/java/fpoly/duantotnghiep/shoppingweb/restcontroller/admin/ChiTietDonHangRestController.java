package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ctdh-rest-admin")
@RequestMapping("${admin.domain}/chi-tiet-don-hang")
public class ChiTietDonHangRestController {
    @Autowired
    private IChiTietDonHangService chiTietDonHangService;

    @GetMapping("/{maDonHang}")
    public List<ChiTietDonHangDtoResponse> getByDonHang(@PathVariable("maDonHang") String maDonHang) {
        return chiTietDonHangService.getByDonHang(maDonHang);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")String id){
        return ResponseEntity.ok(chiTietDonHangService.findById(id));
    }
}
