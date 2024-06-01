package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GioHangDtoReponse {
    private String id;
    private String anh;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;
    private String tenSanPham;
    private String maSanPham;
    private Float size;
    private Integer soLuong;
    private Long soLuongSanPham;

    public GioHangDtoReponse(ChiTietSanPhamModel model, Integer sl) {
        id = model.getId();
        tenSanPham = model.getSanPham() == null ? "" : model.getSanPham().getTen();
        maSanPham = model.getSanPham() == null ? "" : model.getSanPham().getMa();
        soLuongSanPham = model.getSoLuong();
        donGia = model.getSanPham().getGiaBan();
        donGiaSauGiam = model.getSanPham().getDonGiaSauGiam();
        size = model.getSize().getMa();
        anh = model.getSanPham().getImages().size() == 0 ? "default.png" : model.getSanPham().getImages().get(0).getTen();
//        soLuong = model.getSoLuong();
        soLuong = sl;
    }

}
