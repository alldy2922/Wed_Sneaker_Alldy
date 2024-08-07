package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangDoiModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangDoiDTORequest {
    private String maDonHangDoi;
    private String donHangID;
    private String sanPhamCT;
    private String lyDoTra;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;
    private Date ngayXacNhan;
    private Date ngayKiemTra;
    private Date ngayGiao;
    private Date ngayHoanThanh;
    private Date ngayHuy;
    private Integer trangThai;
    private String lyDoDoiHang;
    private DonHangModel donHang;
    private ChiTietSanPhamModel chiTietSanPhamModel;

    public DonHangDoiModel mapModel() {
        DonHangDoiModel model = new DonHangDoiModel();
        ChiTietSanPhamModel chiTietSanPhamModel = new ChiTietSanPhamModel();
        DonHangModel donhang = new DonHangModel();
        model.setMaDonHangDoi(maDonHangDoi);
        donhang.setMa(donHangID);
        chiTietSanPhamModel.setId(sanPhamCT);
        model.setDonHang(donhang);
        model.setSoLuong(soLuong);
        model.setChiTietSanPham(chiTietSanPhamModel);
        model.setDonGia(donGia);
        model.setDonGiaSauGiam(donGiaSauGiam);
        model.setNgayXacNhan(ngayXacNhan);
        model.setNgayKiemTra(ngayKiemTra);
        model.setNgayGiao(ngayGiao);
        model.setNgayHoanThanh(ngayHoanThanh);
        model.setNgayHuy(ngayHuy);
        model.setTrangThai(trangThai);
        model.setDonHang(donHang);
        model.setChiTietSanPham(chiTietSanPhamModel);
        return model;
    }
}
