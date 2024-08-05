package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.LichSuThaoTacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.service.impl.LichSuThaoTacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("${admin.domain}/lich-su-thao-tac")
public class LichSuThaoTacRestController {
    @Autowired
    private LichSuThaoTacServiceImpl service;


    @GetMapping("getAll")
    public  ResponseEntity<?> getAllActivity(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        return ResponseEntity.ok(service.pagination(pageNumber,limit));
    }
    @GetMapping("locDate")
    public  ResponseEntity<?> getAllDate(@RequestParam(value = "ngayBatDau", defaultValue = "0")Date ngayBatDau,
                                         @RequestParam(value = "ngayKetThuc", defaultValue="0") Date ngayKetThuc){
        return ResponseEntity.ok(service.getAllHistoryWithDateStartAndDateEnd(ngayBatDau,ngayKetThuc));
    }
    @GetMapping("locName")
    public  ResponseEntity<?> getAllByName(@RequestParam(value = "name", defaultValue = "0") String name){
        return ResponseEntity.ok(service.getAllHistoryByName(name));
    }
    @GetMapping("filter")
    public ResponseEntity<?> filter(
            @RequestParam(value = "ten", required = false) String ten,
            @RequestParam(value = "ngayBatDau", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayBatDau,
            @RequestParam(value = "ngayKetThuc", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date ngayKetThuc,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<LichSuThaoTacDTOResponse> response = service.filter(ten, ngayBatDau, ngayKetThuc, page, size);
        return ResponseEntity.ok(response);
    }
}
