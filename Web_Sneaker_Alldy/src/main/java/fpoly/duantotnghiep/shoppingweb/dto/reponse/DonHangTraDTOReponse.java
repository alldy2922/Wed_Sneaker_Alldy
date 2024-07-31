package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;

import java.math.BigDecimal;
import java.util.Date;

public class DonHangTraDTOReponse {
    private String id;
    private String maDonHangTra;
    private String maSanPham;
    private String sanPham;
    private String anh;
    private float size;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;
    private String idChiTietSanPham;
    private Date ngayXacNhan;
    private Date ngayKiemTra;
    private Date ngayHoanThanh;
    private Integer trangThai;
    public DonHangTraDTOReponse(DonHangTraModel model) {
        this.maSanPham = model.getChiTietSanPham().getSanPham().getMa();
        this.id = model.getId();
        this.maDonHangTra = model.getMaDonHangTra();
        this.sanPham = model.getChiTietSanPham().getSanPham().getTen();
        this.anh = model.getChiTietSanPham().getSanPham().getImages().size()>0 ? model.getChiTietSanPham().getSanPham().getImages().get(0).getTen() : "default.png";
        this.size = model.getChiTietSanPham().getSize() == null ? 0 : model.getChiTietSanPham().getSize().getMa();
        this.soLuong = model.getSoLuong();
        this.donGia = model.getDonGia();
        this.donGiaSauGiam = model.getDonGiaSauGiam();
        this.idChiTietSanPham = model.getChiTietSanPham().getId();
       this.ngayXacNhan = model.getNgayXacNhan();
        this.ngayKiemTra = model.getNgayKiemTra();
       this.ngayHoanThanh = model.getNgayHoanThanh();
       this.trangThai = model.getTrangThai();
    }
}
