package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.request.LichSuThaoTachRequest;
import fpoly.duantotnghiep.shoppingweb.service.impl.LichSuThaoTacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${admin.domain}/lich-su-thao-tac")
public class LichSuThaoTacRestcontroller {

    @Autowired
    private LichSuThaoTacServiceImpl service;


    @GetMapping("getAll")
    public  ResponseEntity<?> getAllActivity(){
        return ResponseEntity.ok(service.getRecentActivities());
    }
}
