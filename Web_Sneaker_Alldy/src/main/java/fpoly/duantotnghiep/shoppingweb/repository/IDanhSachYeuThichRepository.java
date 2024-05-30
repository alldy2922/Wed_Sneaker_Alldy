package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDanhSachYeuThichRepository extends JpaRepository<DanhSachYeuThichModel, String> {
    @Query("""
                    delete from DanhSachYeuThichModel d where d.nguoiSoHuu.username = ?1 and d.sanPham.ma = ?2
            """)
    void deleteDanhSachYeuThichKKK(String nguoiSoHuu, String sanPham);

    boolean existsBySanPhamAndNguoiSoHuu(SanPhamModel modelSP, KhachHangModel modelKhach);

    List<DanhSachYeuThichModel> getByNguoiSoHuu(KhachHangModel khachHangModel);

//    @Query("SELECT km  FROM DanhSachYeuThichModel km where km.sanPham = ?1 and km.nguoiSoHuu = ?2")
    DanhSachYeuThichModel getDanhSachYeuThichModelBySanPhamAndAndNguoiSoHuu(SanPhamModel sanPhamModel, KhachHangModel khachHangModel);

    @Query("""
        SELECT d FROM DanhSachYeuThichModel d where d.nguoiSoHuu.username = ?1
""")
    List<DanhSachYeuThichResponse> SearchDSYTByKhach(String username);


}
