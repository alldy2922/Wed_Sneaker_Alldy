package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.service.impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("size-rest-user")
@RequestMapping("size")
public class SizeRestController {

    @Autowired
    private SizeServiceImpl service;

    @GetMapping("get-by-chieu-dai")
    public ResponseEntity<?> getByChieuDai(@RequestParam("chieuDai")float chieuDai){
        return ResponseEntity.ok(service.getByChieuDai(chieuDai));
    }
}
