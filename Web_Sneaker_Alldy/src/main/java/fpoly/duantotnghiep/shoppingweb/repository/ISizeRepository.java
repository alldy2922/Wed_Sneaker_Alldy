package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISizeRepository extends JpaRepository<SizeModel, Float> {
    @Query("""
            SELECT s FROM SizeModel s 
            WHERE s NOT IN (SELECT c.size FROM ChiTietSanPhamModel c 
                                        WHERE c.sanPham.ma = ?1)
            """)
    List<SizeModel> getAllNotInSanPham(String maSP);
}
