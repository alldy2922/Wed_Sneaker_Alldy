package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.ISizeService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/size")
@CrossOrigin(origins = "*")
public class SizeRestontroller {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private ISizeService service;

    @GetMapping("/find-all")
    public List<SizeDTOResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody SizeDTORequest size, BindingResult result) throws IOException {
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        return ResponseEntity.ok(service.save(size));
    }

    @GetMapping("/chi-tiet/{ma}")
    public ResponseEntity<SizeDTOResponse> chiTiet(@PathVariable("ma") Float ma){
        System.out.println(ma);
        return ResponseEntity.ok(service.findById(ma));
    }

    @DeleteMapping("/delete/{ma}")
    public ResponseEntity<?> delete(@PathVariable Float ma) {
        chiTietSanPhamService.deleteBySize(ma);
        service.deleteById(ma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<?> update(@Valid @RequestBody SizeDTORequest size,BindingResult result, @PathVariable Float ma) {
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        SizeDTOResponse sizeDTOResponse = service.findById(ma);
        size.setNgayTao(sizeDTOResponse.getNgayTao());
        size.setMa(ma);
        return ResponseEntity.ok(service.save(size));
    }
}
