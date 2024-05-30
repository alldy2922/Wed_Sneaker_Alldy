package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ChiTietDonHangDtoResponse {
    private String id;
    private String maSanPham;
    private String sanPham;
    private String anh;
    private float size;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;
    private String idChiTietSanPham;
    private NhanXetDtoResponse nhanXet;

    public ChiTietDonHangDtoResponse(ChiTietDonHangModel model) {
        this.maSanPham = model.getChiTietSanPham().getSanPham().getMa();
        this.id = model.getId();
        this.sanPham = model.getChiTietSanPham().getSanPham().getTen();
        this.anh = model.getChiTietSanPham().getSanPham().getImages().size()>0 ? model.getChiTietSanPham().getSanPham().getImages().get(0).getTen() : "default.png";
        this.size = model.getChiTietSanPham().getSize() == null ? 0 : model.getChiTietSanPham().getSize().getMa();
        this.soLuong = model.getSoLuong();
        this.donGia = model.getDonGia();
        this.donGiaSauGiam = model.getDonGiaSauGiam();
        this.idChiTietSanPham = model.getChiTietSanPham().getId();
        if(model.getNhanXet() != null) this.nhanXet = new NhanXetDtoResponse(model.getNhanXet());
    }
}
