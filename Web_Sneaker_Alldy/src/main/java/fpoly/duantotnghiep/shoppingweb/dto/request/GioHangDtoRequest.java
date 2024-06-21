package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.GioHangModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class GioHangDtoRequest {
    private String id;
    private String chiTietSanPham;
    private String khachHang;
    private Integer soLuong;

//    public GioHangDtoRequest(GioHangModel model) {
//        id = model.getId();
//        chiTietSanPham = model.getChiTietSanPham() == null ? null : model.getChiTietSanPham().getId();
//        khachHang = model.getKhachHang() == null ? null : model.getKhachHang().getUsername();
//        soLuong = model.getSoLuong();
//
//    }

//    public GioHangModel maptoModel() {
//        GioHangModel model = new GioHangModel();
//        model.setId(id);
//        if (chiTietSanPham != null && !chiTietSanPham.isBlank()) model.setChiTietSanPham(new ChiTietSanPhamModel(chiTietSanPham));
////        if(khachHang != null && !khachHang.isBlank()) model.setKhachHang(new KhachHangModel(khachHang));
//        model.setSoLuong(soLuong);
//        return model;
//    }
}
