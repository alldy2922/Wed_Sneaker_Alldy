package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DongSanPhamRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/dong-san-pham")
public class DongSanPhamRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IDongSanPhamService service;

    @GetMapping("find-all")
    public List<DongSanPhamResponese> findall(){
        return service.findAll();
    }

    @GetMapping("chiTiet/{id}")
    public ResponseEntity<DongSanPhamResponese> chiTiet(@PathVariable("id") String id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody DongSanPhamRequest dongSanPhamRequest, BindingResult result) throws IOException {
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        System.out.println(dongSanPhamRequest.maptomodel().toString());
        return ResponseEntity.ok(service.save(dongSanPhamRequest));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody DongSanPhamRequest dong, @PathVariable("id") String id){
        DongSanPhamResponese dongSanPhamOld = service.findById(id);
        dong.setNgayTao(dongSanPhamOld.getNgayTao());
        DongSanPhamResponese dongSanPhamResponese = service.save(dong);
        return ResponseEntity.ok(dongSanPhamResponese);
    }


    @DeleteMapping("delete/{ma}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("ma") String id){
        Boolean exit = service.existsById(id);
        if(exit){
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook","Xóa thành công",""));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bại", "")
        );
    }
}
