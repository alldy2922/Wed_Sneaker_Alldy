package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanVienDtoRequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface INhanVienService {

    Page<NhanVienDtoResponse> getAll(Integer page, Integer limit);

    Page<NhanVienDtoResponse> search(String keyWord, Integer page, Integer limit);

    NhanVienDtoResponse findById(String username);

    Boolean existsByUsername(String username);


    NhanVienDtoResponse add(NhanVienDtoRequest nhanVien) throws MessagingException;

    NhanVienDtoResponse update(NhanVienDtoRequest nhanVien) ;

    NhanVienDtoResponse update(NhanVienDtoRequest nhanVien, MultipartFile img) throws IOException;

    void deleteByUsername(String username);
}
