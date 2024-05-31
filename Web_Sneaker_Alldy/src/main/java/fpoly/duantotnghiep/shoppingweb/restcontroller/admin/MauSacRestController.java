package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IMauSacService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("${admin.domain}/mau-sac")
public class MauSacRestController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IMauSacService service;

    @GetMapping("find-all")
    public List<MauSacDTOResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("view-alll")
    public String viewAdd(@ModelAttribute("mauSac") MauSacDTORequest mauSac){
        request.setAttribute("method","add");
        return "test";
    }
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody MauSacDTORequest mauSac) throws IOException {
        System.out.println(mauSac.mapToModel().toString());
        return ResponseEntity.ok(service.save(mauSac));
    }

    @GetMapping("chiTiet/{ma}")
    public ResponseEntity<MauSacDTOResponse> chiTiet(@PathVariable("ma") String ma){
        return ResponseEntity.ok(service.findById(ma));
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

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody MauSacDTORequest mausac, @PathVariable("id") String id){

        MauSacDTOResponse mauSacOld = service.findById(id);
        mausac.setNgayTao(mauSacOld.getNgayTao());
        service.findById(id);
        MauSacDTOResponse mauSacDTOResponse = service.save(mausac);
        return ResponseEntity.ok(mauSacDTOResponse);
    }

}
