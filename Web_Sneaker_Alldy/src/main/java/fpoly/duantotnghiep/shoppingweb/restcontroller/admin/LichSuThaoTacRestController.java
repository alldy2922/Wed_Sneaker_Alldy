package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.service.impl.LichSuThaoTacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${admin.domain}/lich-su-thao-tac")
public class LichSuThaoTacRestController {
    @Autowired
    private LichSuThaoTacServiceImpl service;


    @GetMapping("getAll")
    public ResponseEntity<?> getAllActivity(){
        return ResponseEntity.ok(service.getRecentActivities());
    }

}
