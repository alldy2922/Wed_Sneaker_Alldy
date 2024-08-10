package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DonHangDoiModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangDoiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDonHangDoiRepository extends JpaRepository<DonHangDoiModel,String> {
    @Query("SELECT d FROM DonHangDoiModel d WHERE d.trangThai = ?1 ORDER BY d.ngayXacNhan DESC")
    List<DonHangDoiModel> getAllByTrangThai(Integer trangThai);
    @Query("SELECT d FROM DonHangDoiModel d WHERE d.donHang.ma = ?1 ")
    List<DonHangDoiModel> getAllByDonHang(String ma);

    @Query("SELECT d FROM DonHangDoiModel d WHERE d.donHang.ma = ?1")
    List<DonHangDoiModel> findByMaDonHang(String maDonHang);

    @Query("SELECT d FROM DonHangDoiModel d WHERE d.donHang.ma = ?1")
    DonHangDoiModel findByMaDonHangN(String maDonHang);

    @Query("SELECT t FROM DonHangModel dh JOIN DonHangDoiModel t on t.donHang.ma = dh.ma where dh.nguoiSoHuu.username = ?1 and t.donHang.ma = ?2 AND dh.loai = 0 ORDER BY dh.ngayDatHang DESC")
    List<DonHangDoiModel> findAllByKhachHangAndMaDonHang(String nguoiSoHuu, String ma);



}
