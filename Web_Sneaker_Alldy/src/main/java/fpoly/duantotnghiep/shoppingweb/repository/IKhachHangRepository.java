package fpoly.duantotnghiep.shoppingweb.repository;


import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKhachHangRepository extends JpaRepository<KhachHangModel, String> {

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IKhachHangRepository extends JpaRepository<KhachHangModel, String> {
    @Query("SELECT  kh.danhSachDiaChi FROM KhachHangModel kh where kh.username = :taiKhoan")
    List<DiaChiModel> findAllDiaChiByTaiKhoan(@Param("taiKhoan") String taiKhoan);


    List<KhachHangModel> findByUsernameIn(List<String> ma);

    @Query("""
                SELECT k FROM KhachHangModel k WHERE k.hoVaTen LIKE %?1% OR k.soDienThoai LIKE %?1%
            """)
    Page<KhachHangModel> search(String keyWord, Pageable pageable);

    @Query("SELECT dh.nguoiSoHuu FROM DonHangModel dh WHERE dh.trangThai = 4 GROUP BY dh.nguoiSoHuu having COUNT(dh) > 3")
    List<KhachHangModel> findKhachMuaNhieu();

    @Query("SELECT dh.nguoiSoHuu FROM DonHangModel dh WHERE dh.trangThai = 4 GROUP BY dh.nguoiSoHuu having COUNT(dh) = 1")
    List<KhachHangModel> findKhachMuaLanDau();

    @Query("SELECT dh.nguoiSoHuu FROM DonHangModel dh WHERE dh.trangThai = 4 GROUP BY dh.nguoiSoHuu having COUNT(dh) = 0")
    List<KhachHangModel> findKhachMoiMua();
           
    @Query("""
    SELECT k FROM KhachHangModel k WHERE k.username = ?1
""")
    Optional<KhachHangModel> getByUsername(String username);

}
