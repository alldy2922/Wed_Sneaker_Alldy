package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DanhSachYeuThichRequest;
import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;

import java.util.List;

public interface IDanhSachYeuThichService {
    public List<DanhSachYeuThichResponse> findAll(String u);
    DanhSachYeuThichResponse save(DanhSachYeuThichRequest danhSachYeuThichRequest);
    DanhSachYeuThichResponse findById(String s);
    boolean existsById(String s);
    void deleteById(String s);
    void deleteDanhSachYeuThich(String nguoiSoHuu,String sanPham);
    Boolean exitByKhachHangAndSanPham(SanPhamModel modelSP, KhachHangModel modelKH);
    List<DanhSachYeuThichResponse> getByNguoiSoHuu(String maNguoiSoHuu);
}
