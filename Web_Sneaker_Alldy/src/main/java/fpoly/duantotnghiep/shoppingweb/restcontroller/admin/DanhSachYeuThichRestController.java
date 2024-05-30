package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DanhSachYeuThichRequest;
import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDanhSachYeuThichRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDanhSachYeuThichService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/danh-sach-yeu-thich")
public class DanhSachYeuThichRestController {
    @Autowired
    private IDanhSachYeuThichService service;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IDanhSachYeuThichRepository repository;


    @GetMapping("find-all")
    public List<DanhSachYeuThichResponse> findAll(Authentication authentication) {
        String username = authentication.getName();
        return service.getByNguoiSoHuu(username);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody DanhSachYeuThichRequest danhSachYeuThichRequest,
                                 Authentication authentication) throws IOException {
        if(authentication==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();

        danhSachYeuThichRequest.setNguoiSoHuu(username);

        return ResponseEntity.ok(service.save(danhSachYeuThichRequest));
    }

    @DeleteMapping("delete/{ma}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("ma") String ma, Authentication authentication) {
//        if (authentication == null) {
//            return ResponseEntity.status(HttpStatus.FOUND).body(
//                    new ResponseObject("found", "Xóa thất bại", "")
//            );
//        }

        if(authentication==null){
//            HttpSession session = request.getSession();
//            session.setAttribute("url","/");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SanPhamModel sanpham = new SanPhamModel();
        KhachHangModel khachHangModel = new KhachHangModel();
        String khachhang = authentication.getName();
        sanpham.setMa(ma);
        khachHangModel.setUsername(khachhang);
        DanhSachYeuThichModel dsyt = repository.getDanhSachYeuThichModelBySanPhamAndAndNguoiSoHuu(sanpham, khachHangModel);
        service.deleteById(dsyt.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook", "Xóa thành công", ""));
    }

    @DeleteMapping("delete2/{sanpham}")
    public ResponseEntity<ResponseObject> delete2(@PathVariable("sanpham") String sanpham, Authentication authentication) {
        service.deleteDanhSachYeuThich("khach1", sanpham);
        return ResponseEntity.ok().build();
    }

    @GetMapping("check/{SanPham}")
    public Boolean checkSanPhamInDanhSachYeuThich(@PathVariable("SanPham") String sanphamMa, Authentication authentication) {

        if (authentication == null) {
            return false;
        }

        SanPhamModel sanpham = new SanPhamModel();
        KhachHangModel khachHangModel = new KhachHangModel();
        sanpham.setMa(sanphamMa);
        String khachhang = authentication.getName();
        khachHangModel.setUsername(khachhang);

        return service.exitByKhachHangAndSanPham(sanpham, khachHangModel);
    }

    @GetMapping("get-ma-san-pham-in-dsyt")
    public List<String> getMaSanPhamInDSYT(Authentication authentication) {
        if (authentication == null) {
            return new ArrayList<String>();
        }
        return service.getByNguoiSoHuu(authentication.getName()).stream().map(n -> n.getSanPham()).collect(Collectors.toList());
    }


}
