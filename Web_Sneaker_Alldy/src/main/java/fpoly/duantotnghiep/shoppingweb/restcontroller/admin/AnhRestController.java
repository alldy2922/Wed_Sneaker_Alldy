package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;


import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("image")
public class AnhRestController {

    @Autowired
    private INhanVienService nhanVienService;
    @Autowired
    private IKhachHangService khachHangService;

    @GetMapping("/loadImage/{forder}/{imgName}")
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable("forder") String forder,
                                          @PathVariable("imgName") String imgName) throws IOException {

        byte[] ima;
        try {
            ima = Files.readAllBytes(new File("src/main/resources/images/" + forder + "/" + imgName).toPath());
        } catch (Exception e) {
            ima = Files.readAllBytes(new File("src/main/resources/images/" + forder + "/default.png").toPath());
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(ima);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .contentType(MediaType.valueOf(MediaType.IMAGE_GIF_VALUE))
                .contentType(MediaType.valueOf("image/jpg"))
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(byteArrayResource);

    }

    @GetMapping("/loadImageUser/{username}")
    public ResponseEntity<?> getImageUser(@PathVariable("username") String username) throws IOException {
        byte[] ima;
        if (nhanVienService.existsByUsername(username)) {
            NhanVienDtoResponse nhanVienDtoResponse = nhanVienService.findById(username);
            try {
                ima = Files.readAllBytes(new File("src/main/resources/images/user/" + nhanVienDtoResponse.getAnhDaiDien()).toPath());
            } catch (Exception e) {
                ima = Files.readAllBytes(new File("src/main/resources/images/user/default.png").toPath());
            }
        } else {
            ima = Files.readAllBytes(new File("src/main/resources/images/user/default.png").toPath());
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(ima);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .contentType(MediaType.valueOf(MediaType.IMAGE_GIF_VALUE))
                .contentType(MediaType.valueOf("image/jpg"))
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(byteArrayResource);
    }

    @GetMapping("/loadImageKhachHang/{username}")
    public ResponseEntity<?> getImageKhachHang(@PathVariable("username") String username) throws IOException {
        byte[] ima;
        if (khachHangService.exsistsByUsername(username)) {
            KhachHangDtoResponse khachHangDtoResponse = khachHangService.findById(username);
            try {
                ima = Files.readAllBytes(new File("src/main/resources/images/user/" + khachHangDtoResponse.getAnhDaiDien()).toPath());
            } catch (Exception e) {
                ima = Files.readAllBytes(new File("src/main/resources/images/user/default.png").toPath());
            }
        } else {
            ima = Files.readAllBytes(new File("src/main/resources/images/user/default.png").toPath());
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(ima);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .contentType(MediaType.valueOf(MediaType.IMAGE_GIF_VALUE))
                .contentType(MediaType.valueOf("image/jpg"))
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(byteArrayResource);
    }
}
