package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.dto.request.DongSanPhamRequest;
import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDongSanPhamService {
    public List<DongSanPhamResponese> findAll();
    public DongSanPhamResponese save(DongSanPhamRequest dongSanPhamDtoRequest);
    public DongSanPhamResponese findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);

    void deleteByThuongHieu(String maTHuongHieu);
}
