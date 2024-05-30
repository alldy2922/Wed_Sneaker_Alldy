package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IDiaChiRepository extends JpaRepository<DiaChiModel, Long> {
    List<DiaChiModel> getAllByTaiKhoan(KhachHangModel khachHangModel);
    DiaChiModel findByTaiKhoanAndMacDinh(KhachHangModel khachHangModel, Boolean macDinh);
}
