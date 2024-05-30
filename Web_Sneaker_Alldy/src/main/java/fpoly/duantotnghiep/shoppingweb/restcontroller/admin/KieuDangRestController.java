package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/kieu-dang")
public class KieuDangRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IKieuDangService service;
    @GetMapping("find-all")
    public List<KieuDangDTOResponse> findAll(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int number){
        Page<KieuDangDTOResponse> page = service.findAll(number-1, 1000);
        List<KieuDangDTOResponse> list = page.getContent();
        return list;
    }
    @GetMapping("get-all")
    public ResponseEntity<Page<KieuDangDTOResponse>> getAll(@RequestParam(defaultValue = "0")Integer page,
                                                                     @RequestParam(defaultValue = "5")Integer limit,
                                                                     @RequestParam(required = false)String keyWord){
        if(keyWord!=null){
            return ResponseEntity.ok(service.search(keyWord,page,limit));
        }
        return ResponseEntity.ok(service.findAll(page,limit));
    }
    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody KieuDangDtoRequest kieudang, BindingResult result){
        if(kieudang.getTen()!=null && !kieudang.getTen().isBlank()){
            if(service.existsById(kieudang.getTen())){
                result.addError(new FieldError("Tên kiểu dáng","Tên kiểu dáng","Tên đã tồn tại"));
                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        return  ResponseEntity.ok(service.save(kieudang));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody KieuDangDtoRequest kieudang, BindingResult result, @PathVariable("id") String id){
            if(result.hasErrors()){
                return ValidateUtil.getErrors(result);
            }
            KieuDangDTOResponse kieuDangDTOResponse = service.findById(id);
            kieudang.setNgayTao(kieuDangDTOResponse.getNgayTao());
            kieudang.setId(id);
            return ResponseEntity.ok(service.save(kieudang));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deletes(@RequestBody List<String> id){

        service.deleteByIds(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        if(!service.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public KieuDangDTOResponse findById(@PathVariable("id") String id){
        return service.findById(id);
    }
}
