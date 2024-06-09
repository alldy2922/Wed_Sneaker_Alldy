package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.XuatXuResponse;
import fpoly.duantotnghiep.shoppingweb.service.IXuatXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("xuat-xu-user")
@RequestMapping("xuat-xu")
public class XuatXuRestController {
    @Autowired
    private IXuatXuService service;

    @GetMapping("find-all")
    public List<XuatXuResponse> findAll(){
        return service.findAll();
    }
}
