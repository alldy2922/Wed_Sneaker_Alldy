package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


public interface INhanXetRepository extends JpaRepository<NhanXetModel,String> {
    @Query("""
    SELECT n FROM NhanXetModel n 
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma = ?1 AND n.pheDuyet = ?2
    ORDER BY n.thoiGian DESC 
""")
    Page<NhanXetModel> getBySanPhamMaAndPheDuyet(String maSanPham, Boolean pheDuyet, Pageable pageable);

    @Query("""
    SELECT n FROM NhanXetModel n 
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma = ?1 AND n.rating = ?2 AND n.pheDuyet = ?3
    ORDER BY n.thoiGian DESC 
""")
    Page<NhanXetModel> getBySanPhamMaAndRateAndPheDuyet(String maSanPham, Float rate, Boolean pheDuyet, Pageable pageable);

    @Query("""
    SELECT AVG(n.rating) FROM NhanXetModel n
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma  = ?1 AND n.pheDuyet = ?2
""")
    Float getAvgRatingBySanPhamAndPheDuyet(String maSP, Boolean pheDuyet);

    @Query("""
    SELECT n FROM NhanXetModel n 
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma = ?1 
    ORDER BY n.thoiGian DESC 
""")
    Page<NhanXetModel> getBySanPhamMa(String maSanPham, Pageable pageable);

    @Query("""
    SELECT n FROM NhanXetModel n 
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma = ?1 AND n.rating = ?2 
    ORDER BY n.thoiGian DESC 
""")
    Page<NhanXetModel> getBySanPhamMaAndRate(String maSanPham, Float rate, Pageable pageable);

    @Query("""
    SELECT AVG(n.rating) FROM NhanXetModel n
    WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma  = ?1 
""")
    Float getAvgRatingBySanPham(String maSP);

    @Transactional
    @Modifying
    @Query("""
    update NhanXetModel n SET n.pheDuyet = ?1 WHERE n.id = ?2
""")
    Integer pheDuyetNhanXet(Boolean pheDuyet, String id);
}
