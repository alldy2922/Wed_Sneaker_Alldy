package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LichSuThaoTacRepository extends JpaRepository<LichSuThaoTacModel,String> {
    List<LichSuThaoTacModel> findAllByOrderByTimeChangeDesc();

    @Query("""
    SELECT l FROM LichSuThaoTacModel l 
    WHERE (:ten IS NULL OR LOWER(l.username) LIKE LOWER(CONCAT('%', :ten, '%')))
    AND (:ngayBatDau IS NULL OR l.timeChange >= :ngayBatDau)
    AND (:ngayKetThuc IS NULL OR l.timeChange <= :ngayKetThuc)
    ORDER BY l.timeChange DESC
""")
    Page<LichSuThaoTacModel> findAllByCriteria(
            @Param("ten") String ten,
            @Param("ngayBatDau") Date ngayBatDau,
            @Param("ngayKetThuc") Date ngayKetThuc,
            Pageable pageable
    );
}
