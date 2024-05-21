package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KhachHangService {
    List<KhachHangModel> getAll();
}
