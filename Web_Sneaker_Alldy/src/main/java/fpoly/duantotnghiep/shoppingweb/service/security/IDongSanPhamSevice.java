package fpoly.duantotnghiep.shoppingweb.service.security;

import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDongSanPhamSevice extends JpaRepository<DongSanPhamModel,String> {
    void deleteByThuongHieu(ThuongHieuModel thuongHieuModel);
}
