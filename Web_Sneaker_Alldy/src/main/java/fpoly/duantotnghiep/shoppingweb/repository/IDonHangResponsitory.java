package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IDonHangResponsitory extends JpaRepository<DonHangModel, String> {
    @Query("""
            SELECT d FROM DonHangModel d WHERE d.trangThai = ?1 ORDER BY d.ngayDatHang DESC 
            """)
    Page<DonHangModel> getAllByTrangThai(Integer trangThai, Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            update DonHangModel d set d.trangThai = ?1 WHERE d.ma=?2
            """)
    Integer updateTrangThaiDonHang(int trangThai, String maDonHang);

    @Query("""
                SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c 
                WHERE c.donHang.ngayDatHang between ?1 and ?2 AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5
            """)
    Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate);

    @Query("""
                SELECT COUNT(d) FROM DonHangModel d 
                WHERE d.ngayDatHang between ?1 and ?2
            """)
    Long getQuantityOrdersWithDate(Date firstDate, Date lastDate);

    @Query("""
                SELECT SUM(c.donGiaSauGiam*c.soLuong) - SUM(c.donHang.tienGiam) FROM ChiTietDonHangModel c 
                WHERE c.donHang.ngayDatHang between ?1 and ?2 AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5
            """)
    BigDecimal getTotalPriceInOrdersWithDate(Date firstDate, Date lastDate);

    @Query("SELECT dh FROM DonHangModel dh where dh.nguoiSoHuu.username = ?1 and dh.trangThai = ?2 AND dh.loai = 0 ORDER BY dh.ngayDatHang DESC")
    List<DonHangModel> findAllByKhachHangAndTrangThai(String nguoiSoHuu, Integer trangThai);

    @Query("SELECT dh FROM DonHangModel dh WHERE dh.ngayDatHang <= :cutoffTime and dh.trangThai = 5 ")
    List<DonHangModel> findDonHangWithOlderStock(@Param("cutoffTime") Date cutoffTime);

    @Query("""
                SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c 
                WHERE c.donHang.ngayDatHang between ?1 and ?2 AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5 AND c.donHang.loai = ?3
            """)
    Long getTotalQauntityInOrdersWithDateAndLoai(Date firstDate, Date lastDate,Integer loai);

}
