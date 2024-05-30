package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDongSanPhamRepository extends JpaRepository<DongSanPhamModel,String> {
    void deleteByThuongHieu(ThuongHieuModel thuongHieuModel);
}
