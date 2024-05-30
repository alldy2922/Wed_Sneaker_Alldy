package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangDTORequest {
    private String id;
    private String donHangID;
    private String sanPhamCT;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;

    public ChiTietDonHangModel mapModel() {
        ChiTietDonHangModel model = new ChiTietDonHangModel();
        ChiTietSanPhamModel chiTietSanPhamModel = new ChiTietSanPhamModel();
        DonHangModel donhang = new DonHangModel();
        if(id!=null && !id.isBlank()) model.setId(id);
        donhang.setMa(donHangID);
        chiTietSanPhamModel.setId(sanPhamCT);
        model.setDonHang(donhang);
        model.setSoLuong(soLuong);
        model.setChiTietSanPham(chiTietSanPhamModel);
        model.setDonGia(donGia);
        model.setDonGiaSauGiam(donGiaSauGiam);
        return model;
    }

    public ChiTietDonHangDTORequest(String donHangID, String sanPhamCT, Integer soLuong, BigDecimal donGia, BigDecimal donGiaSauGiam) {
        this.donHangID = donHangID;
        this.sanPhamCT = sanPhamCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donGiaSauGiam = donGiaSauGiam;
    }
}
