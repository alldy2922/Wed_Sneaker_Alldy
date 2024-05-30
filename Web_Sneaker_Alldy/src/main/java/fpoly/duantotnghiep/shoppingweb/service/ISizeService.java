package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISizeService  {
    List<SizeDTOResponse> findAll();

    List<SizeDTOResponse> getByChieuDai(Float chieuDai);

    SizeDTOResponse save(SizeDTORequest sizeDTORequest);

    SizeDTOResponse findById(Float s);

    boolean existsById(Float s);

    void deleteById(Float s);
}
