package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ISanPhamRepository extends JpaRepository<SanPhamModel, String> {

    @Transactional
    @Modifying
    @Query("""
            update SanPhamModel s SET s.hienThi = ?1 where s.ma = ?2
            """)
    Integer updateTrangThaiHIenThi(Boolean trangThai, String ma);

    @Query("""
    SELECT s FROM SanPhamModel s 
    WHERE s.ten LIKE concat('%',?1,'%')
""")
    List<SanPhamModel> findByTenStartsWith(String ten);

    @Query("""
            update SanPhamModel s SET s.giaBan = ?1 where s.ma = ?2
            """)
    Integer updateGiaBan(BigDecimal giaBan, String ma);

    List<SanPhamModel> findByMaIn(List<String> ma);

    @Query("SELECT km.sanPham  FROM KhuyenMaiModel km where km.ngayKetThuc >= current_date")
    List<SanPhamModel> findAllSanPhamWithKhuyenMai();


    @Query("SELECT km.sanPham  FROM KhuyenMaiModel km where km.ngayBatDau > current_date")
    List<SanPhamModel> findAllSanPhamWithKmWhereNgayBatDau();

    @Query("""
                SELECT s FROM SanPhamModel s JOIN ChiTietSanPhamModel c ON s.ma = c.sanPham.ma
                JOIN ChiTietDonHangModel ctdh on ctdh.chiTietSanPham.id = c.id
                WHERE s.hienThi = true and s.trangThai = true 
                GROUP BY s
                ORDER BY SUM(ctdh.soLuong) DESC 
            """)
    Page<SanPhamModel> getBanChay(Pageable pageable);

    @Query("""
                SELECT s FROM SanPhamModel s WHERE s.mucGiam IS NOT null and s.hienThi = true and s.trangThai = true
                ORDER BY s.giaNiemYet desc 
            """)
    Page<SanPhamModel> getKhuyenMai(Pageable pageable);

    @Query("""
                SELECT s FROM SanPhamModel s WHERE  s.hienThi = true and s.trangThai = true
                ORDER BY s.ngayTao desc 
            """)
    Page<SanPhamModel> getSanPhamMoi(Pageable pageable);


}
