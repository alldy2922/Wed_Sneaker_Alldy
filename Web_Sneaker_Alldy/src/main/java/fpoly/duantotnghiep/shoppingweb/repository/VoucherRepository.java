package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<VoucherModel, String> {

    Page<VoucherModel> findByMaLikeAndAndTrangThaiXoa(String ten, Integer trangThaiXoa, Pageable pageable);

    @Query("SELECT vc FROM VoucherModel vc WHERE vc.trangThai = 0 AND vc.doiTuongSuDung = 0 AND vc.soLuong > 0")
    List<VoucherModel> findVoucherHienThi();

    @Query("SELECT vc  FROM VoucherModel vc where vc.trangThaiXoa = 0")
    Page<VoucherModel> findAllVoucher(Pageable pageable);


    @Query("SELECT vc  FROM VoucherModel vc where vc.ngayKetThuc > current_date ")
    List<VoucherModel> findAllVoucherByNgayKetThuc();


}
