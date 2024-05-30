package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class VoucherRequest {
    private String ma;

    @NotEmpty(message = "Vui lòng nhập dữ liệu")
    private String moTa;

    private String loaiMucGiam;

    private Double mucGiam;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    @Min(value = 10000, message = "Giá trị đơn hàng phải lớn hơn 10.000đ ")
    private Double giaTriDonHang;

    private Double mucGiamToiDa;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayKetThuc;

    private int soLuong;

    private int trangThai;

    private int trangXoa;

    private int doiTuongSuDung;

    private int hinhThucThanhToan;

    private List<KhachHangModel> khachHang;


    public VoucherModel maptoModel() {
        VoucherModel model = new VoucherModel();
        model.setMa(ma);
        model.setMota(moTa);
        model.setLoaiMucGiam(loaiMucGiam);
        model.setMucGiam(mucGiam);
        model.setGiaTriDonHang(giaTriDonHang);
        model.setNgayBatDau(ngayBatDau);
        model.setNgayKetThuc(ngayKetThuc);
        model.setSoLuong(soLuong);
        model.setMucGiamToiDa(mucGiamToiDa);
        model.setHinhThucThanhToan(hinhThucThanhToan);
        model.setTrangThai(trangThai);
        model.setTrangThaiXoa(getTrangXoa());
        model.setKhachHang(khachHang);
        model.setDoiTuongSuDung(doiTuongSuDung);
        return model;
    }
}
