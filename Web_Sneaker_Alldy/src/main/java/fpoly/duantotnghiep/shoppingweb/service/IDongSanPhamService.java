package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.dto.request.DongSanPhamRequest;

import java.util.List;

public interface IDongSanPhamService {
    public List<DongSanPhamResponese> findAll();
    public DongSanPhamResponese save(DongSanPhamRequest dongSanPhamDtoRequest);
    public DongSanPhamResponese findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);

    void deleteByThuongHieu(String maTHuongHieu);
}
