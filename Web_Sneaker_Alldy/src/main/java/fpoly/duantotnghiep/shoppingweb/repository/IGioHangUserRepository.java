package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.GioHangModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGioHangUserRepository extends JpaRepository<GioHangModel,String> {
    List<GioHangModel> findByKhachHang(KhachHangModel khachHang);
    void deleteAllByKhachHang(KhachHangModel khachHang);
    GioHangModel findByKhachHangAndChiTietSanPham(KhachHangModel khachHang, ChiTietSanPhamModel chiTietSanPham);
}
