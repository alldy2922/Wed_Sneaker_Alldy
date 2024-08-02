package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangTraDTOReponse;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDonHangTraRepository extends JpaRepository<DonHangTraModel,String> {
    @Query("SELECT d FROM DonHangTraModel d WHERE d.trangThai = ?1 ORDER BY d.ngayXacNhan DESC")
    List<DonHangTraModel> getAllByTrangThai(Integer trangThai);
    @Query("SELECT d FROM DonHangTraModel d WHERE d.donHang.ma = ?1 ")
    List<DonHangTraModel> getAllByDonHang(String ma);

}
