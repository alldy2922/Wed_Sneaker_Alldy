package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import org.springframework.data.domain.Page;

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
