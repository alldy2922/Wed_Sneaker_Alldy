package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ThuongHieuDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IThuongHieuService  {
    public List<ThuongHieuDtoResponse> findAll();
    public ThuongHieuDtoResponse save( ThuongHieuDtoRequest thuongHieuDtoRequest);
    public ThuongHieuDtoResponse findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);
    public void deleteByIds(List<String> s);

    List<ThuongHieuDtoResponse> getThuongHieuBanChay();
}
