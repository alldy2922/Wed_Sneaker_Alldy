package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface INhanVienRepository extends JpaRepository<NhanVienModel,String> {
    @Query("""
SELECT n FROM NhanVienModel n WHERE n.username LIKE %?1% OR n.hoVaTen LIKE %?1% OR n.soDienThoai LIKE %?1% 
""")
    Page<NhanVienModel> search(String keyWord, Pageable pageable);

    @Query("""
    SELECT n FROM NhanVienModel n WHERE n.username = ?1 
""")
    Optional<NhanVienModel> findByUsername(String username);
}
