package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;

@Data
public class DonHangTraDTOReponse {
    private String maDonHangTra;
    private String maDonHang;
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
    private Date ngayHuy;
    private Integer trangThai;
    private DonHangDtoResponse donHangDto;

    public DonHangTraDTOReponse(DonHangTraModel model) {
        this.maSanPham = model.getChiTietSanPham().getSanPham().getMa();
        this.maDonHang = model.getDonHang().getMa();
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
        this.ngayHuy = model.getNgayHuy();
        this.trangThai = model.getTrangThai();
        this.donHangDto = new DonHangDtoResponse(model.getDonHang());
    }


}
