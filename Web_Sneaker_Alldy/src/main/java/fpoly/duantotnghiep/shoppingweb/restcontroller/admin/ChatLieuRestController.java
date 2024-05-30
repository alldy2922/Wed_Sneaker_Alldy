package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChatLieuDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChatLieuDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IChatLieuService;
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
@RequestMapping("${admin.domain}/chat-lieu")
public class ChatLieuRestController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IChatLieuService service;

    @GetMapping("find-all")
    public List<ChatLieuDTOResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@Valid @RequestBody ChatLieuDTORequest chatLieu, BindingResult result) throws IOException {
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        return ResponseEntity.ok(service.save(chatLieu));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ChatLieuDTORequest chatLieu, @PathVariable String id) {
        ChatLieuDTOResponse chatLieuDTOResponse = service.findById(id);
        chatLieu.setNgayTao(chatLieuDTOResponse.getNgayTao());
        chatLieu.setId(id);
        return ResponseEntity.ok(service.save(chatLieu));
    }
    @GetMapping("/chi-tiet/{id}")
    public ResponseEntity<ChatLieuDTOResponse> chiTiet(@PathVariable("id") String id){
        System.out.println(id);
        return ResponseEntity.ok(service.findById(id));
    }
}
