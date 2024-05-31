package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKieuDangService {
    public Page<KieuDangDTOResponse> findAll(Integer page, Integer limit);
    public List<KieuDangDTOResponse> getAll();
    public KieuDangDTOResponse save( KieuDangDtoRequest kieuDangDtoRequest);
    public KieuDangDTOResponse findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);
    public void deleteByIds(List<String> s);
    public int update( String id, String ten);
    public Page<KieuDangDTOResponse> search(String keyWord, Integer page, Integer limit);
}
