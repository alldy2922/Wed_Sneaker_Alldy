package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IChiTietDonHangRepository extends JpaRepository<ChiTietDonHangModel,String> {
    List<ChiTietDonHangModel> findAllByDonHang(DonHangModel donHangModel);
}
