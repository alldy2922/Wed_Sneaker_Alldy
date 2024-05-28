package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;
import java.util.List;

@SessionScope
public interface IChiTietSanPhamService extends JpaRepository<ChiTietSanPhamModel, String> {
    List<ChiTietSanPhamModel> getAllBySanPhamMaAndTrangThaiOrderBySizeMa(String maSP, Boolean trangThai);

    @Transactional
    @Modifying
    @Query("""
            UPDATE ChiTietSanPhamModel s SET s.soLuong = ?1 WHERE s.id = ?2
            """)
    int updateSoLuong(Long soLuong, String id);

    List<ChiTietSanPhamDtoResponse> fillAllChiTietSP();

    ChiTietSanPhamDtoResponse finById(String id);

    List<ChiTietSanPhamDtoResponse> getAllBySanPhamMa(String maSP);

    ChiTietSanPhamDtoResponse updateSoLuong(ChiTietSanPhamDtoRequest request);

    Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size);

    @Transactional
    @Modifying
    @Query("""
            UPDATE ChiTietSanPhamModel s SET s.trangThai = ?1 WHERE s.id = ?2
            """)
    int updateTrangThai(Boolean trangThai, String id);

    ChiTietSanPhamModel getBySanPhamMaAndSizeMa(String maSP, Float size);

    ChiTietSanPhamDtoResponse save(ChiTietSanPhamDtoRequest entity);

    ChiTietSanPhamDtoResponse update(ChiTietSanPhamDtoRequest entity);

    void updateSL(String maCTSP, Long soLuong);

    void delete(String id);

    List<ChiTietSanPhamDtoResponse> saveAll(List<Float> sizes, ChiTietSanPhamDtoRequest model);

    Boolean checkSoLuongSP(String id, Long soLuong);

    @Query("""
    SELECT c FROM ChiTietSanPhamModel c 
    WHERE c.id not in (SELECT s.chiTietSanPham.id from ChiTietDonHangModel s WHERE s.donHang.ma = ?1)
""")
    List<ChiTietSanPhamModel> getChiTietSanPhamNotInDonHang(String maDonHang);

    @Query("""
    SELECT c FROM ChiTietSanPhamModel c 
    WHERE c.sanPham.ten LIKE concat('%',?1,'%') or c.sanPham.ma LIKE concat('%',?1,'%') 
""")
    List<ChiTietSanPhamModel> getBySanPhamIdOrNameContais(String keyWord);

    @Query("""
    SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c 
    WHERE c.donHang.ngayDatHang in (?1,?2)
""")
    Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate);

    List<ChiTietSanPhamModel> getAllBySize(SizeModel sizeModel);

    List<ChiTietSanPhamModel> getAllBySanPham(SanPhamModel sanPhamModel);

    void deleteBySize(float size);

    void deleteBySanPham(String maSanPham);
}
