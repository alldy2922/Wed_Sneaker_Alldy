package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoReponse;
import org.springframework.data.domain.Page;

public interface KhachHangService {
    Page<KhachHangDtoReponse> getAll(Integer page);
}
