package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${admin.domain}/san-pham")
public class SanPhamRestController {

    @Autowired
    private ISanPhamService sanPhamService;

    private Page<SanPhamDtoResponse> page = null;

    @GetMapping("get-all")
    public ResponseEntity<Page<SanPhamDtoResponse>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "8") Integer limit) {

        return ResponseEntity.ok(sanPhamService.pagination(pageNumber, limit));
    }

    @DeleteMapping("delete/{id}")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})//Khi có lỗi sẽ rollback
    public ResponseEntity<?> delete(@PathVariable("id") String ma) throws IOException {

        if (!sanPhamService.existsById(ma)) {//Kiểm tra xem mã tồn tại ko
            return ResponseEntity.status(404).body("Mã sản phẩm không hợp lệ");
        }

        String tenSanPham = sanPhamService.findByMa(ma).getMa() + " - " + sanPhamService.findByMa(ma).getTen();

        sanPhamService.deleteById(ma);

        //Tạo và gửi thông báo
//        ThongBaoModel thongBao = new ThongBaoModel(null,null, ThongBaoType.Delete.name(),"Xóa sản phẩm: "+tenSanPham,new Date(),null);
//        SocketUtil.sendNotification(thongBao);

        return ResponseEntity.ok().build();
    }

    @PutMapping("update-TrangThai-HienThi/{id}")
    public ResponseEntity<?> updateTrangThaiHienThi(@PathVariable("id") String ma, @RequestBody Boolean trangThai) {
        if (!sanPhamService.existsById(ma)) {//Kiểm tra xem mã tồn tại ko
            return ResponseEntity.status(404).body("Mã sản phẩm không hợp lệ");
        }
        return ResponseEntity.ok(sanPhamService.updateTrangThaiHIenThi(trangThai, ma));
    }

    @PostMapping("filter")
    public ResponseEntity<Page<SanPhamDtoResponse>> filter(@RequestBody SanPhamDtoFilter sanPhamDtoFilter,
                                                           @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "8") Integer limit) {
        return ResponseEntity.ok(sanPhamService.filter(sanPhamDtoFilter, pageNumber, limit));
    }

}
