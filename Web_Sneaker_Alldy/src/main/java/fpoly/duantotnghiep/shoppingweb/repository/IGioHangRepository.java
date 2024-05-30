package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
public interface IGioHangRepository extends JpaRepository<ChiTietSanPhamModel, String> {
}
