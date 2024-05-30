package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KhuyenMaiRepository extends JpaRepository<KhuyenMaiModel, String> {
    @Query("SELECT km  FROM KhuyenMaiModel km where km.ngayBatDau = current_date")
    List<KhuyenMaiModel> findAllSanPhamWithKmWhereNgayBatDau();

    @Query("SELECT km  FROM KhuyenMaiModel km where km.ngayKetThuc = current_date")
    List<KhuyenMaiModel> findAllSanPhamWithKmWhereNgayKetThuc();
}
