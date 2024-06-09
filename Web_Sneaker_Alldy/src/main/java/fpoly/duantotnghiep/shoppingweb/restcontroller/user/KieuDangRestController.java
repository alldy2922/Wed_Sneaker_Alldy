package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("kieu-dang-user")
@RequestMapping("kieu-dang")
public class KieuDangRestController {
    @Autowired
    private IKieuDangService service;

    @GetMapping("find-all")
    public List<KieuDangDTOResponse> findAll() {
        return service.getAll();
    }
}
