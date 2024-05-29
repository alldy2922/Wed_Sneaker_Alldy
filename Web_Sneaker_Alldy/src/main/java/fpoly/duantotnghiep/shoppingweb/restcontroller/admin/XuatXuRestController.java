package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.XuatXuResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.XuatXuRequest;
import fpoly.duantotnghiep.shoppingweb.service.IXuatXuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/xuat-xu")
public class XuatXuRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IXuatXuService service;

    @GetMapping("find-all")
    public List<XuatXuResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("chiTiet/{id}")
    public ResponseEntity<XuatXuResponse> chiTiet(@PathVariable("id") String ma){
        return ResponseEntity.ok(service.findById(ma));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody XuatXuRequest xuatxu) throws IOException {
        System.out.println(xuatxu.mapXuatXuToModel().toString());
        return ResponseEntity.ok(service.save(xuatxu));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") String id){
        Boolean exit = service.existsById(id);
        if(exit){
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook","Xóa thành công",""));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bại", "")
        );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody XuatXuRequest xuatxu, @PathVariable("id") String id){
        XuatXuResponse xuatXuOld = service.findById(id);
        xuatxu.setNgayTao(xuatXuOld.getNgayTao());
        service.findById(id);
        XuatXuResponse xuatXuResponse = service.save(xuatxu);
        return ResponseEntity.ok(xuatXuResponse);
    }

}
