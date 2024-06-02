package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.List;
public interface IDiaChiService {

    DiaChiModel findByIdModel(Long id);
    void addDiaChi(DiaChiModel diaChiModel);
    Page<DiaChiDTOResponse> getAll(Integer page, Integer limit);
    DiaChiDTOResponse findById(Long id);
    Boolean exsistsById(Long id);
    DiaChiDTOResponse add(DiaChiDTORequest diaChi);
    DiaChiDTOResponse update(DiaChiDTORequest diaChi);
    void deleteById(Long id);

    void setMacDinh(String nguoiSoHuu, Long idDiaChi);

    DiaChiDTOResponse getDiaChiMacDinh(String nguoiSoHuu, Boolean macDinh);
}
