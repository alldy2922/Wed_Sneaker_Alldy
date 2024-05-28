package fpoly.duantotnghiep.shoppingweb.service.security;

import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IThuongHieuSevice extends JpaRepository<ThuongHieuModel, String> {
    @Query(value = """
    SELECT t.id FROM shopping_web.ThuongHieu t left join dongsanpham d on d.ThuongHieu = t.id
    										left join sanpham s on s.DongSP = d.id
    										left join chitietsanpham ct on ct.SanPham = s.Ma
                                            left join chitietdonhang dh on dh.ChiTietSanPham = ct.id
    group by id
    order By  SUM(ct.SoLuong) desc
""",nativeQuery = true)
    List<String> getAllOrderByBanChay();
}
