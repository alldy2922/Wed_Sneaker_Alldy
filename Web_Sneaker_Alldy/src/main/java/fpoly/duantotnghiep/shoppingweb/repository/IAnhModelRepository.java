package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.AnhModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnhModelRepository  extends JpaRepository<AnhModel,Long> {

    void deleteBySanPham(SanPhamModel sanPhamModel);

    List<AnhModel> findAllBySanPham(SanPhamModel sanPhamModel);
}
