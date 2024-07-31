package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangTraDTORequest {
    private String id;
    private String maDonHangTra;
    private String donHangID;
    private String sanPhamCT;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;
    private Date ngayXacNhan;
    private Date ngayKiemTra;
    private Date ngayHoanThanh;
    private Integer trangThai;

    public DonHangTraModel mapModel() {
        DonHangTraModel model = new DonHangTraModel();
        ChiTietSanPhamModel chiTietSanPhamModel = new ChiTietSanPhamModel();
        DonHangModel donhang = new DonHangModel();
        if(id!=null && !id.isBlank()) model.setId(id);
        model.setMaDonHangTra(maDonHangTra);
        donhang.setMa(donHangID);
        chiTietSanPhamModel.setId(sanPhamCT);
        model.setDonHang(donhang);
        model.setSoLuong(soLuong);
        model.setChiTietSanPham(chiTietSanPhamModel);
        model.setDonGia(donGia);
        model.setDonGiaSauGiam(donGiaSauGiam);
        model.setNgayXacNhan(ngayXacNhan);
        model.setNgayKiemTra(ngayKiemTra);
        model.setNgayHoanThanh(ngayHoanThanh);
        model.setTrangThai(trangThai);
        return model;
    }


}
