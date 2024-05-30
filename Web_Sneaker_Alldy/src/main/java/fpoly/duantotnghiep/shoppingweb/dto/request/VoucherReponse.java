package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class VoucherReponse {

    private String ma;

    private String moTa;

    private String loaiMucGiam;

    private Double mucGiam;

    private Double giaTriDonHang;

    private Double mucGiamToiDa;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayKetThuc;

    private int soLuong;

    private Integer trangThaiXoa;

    private String hinhThucThanhToan;

    private Integer trangThai;

    private String doiTuongSuDung;

    private Integer soLuongSuDung;
    private List<KhachHangModel> danhSachKhachHang;

    public VoucherReponse(VoucherModel model) {
        this.ma = model.getMa();
        this.moTa = model.getMota();
        this.loaiMucGiam = model.getLoaiMucGiam();
        this.mucGiam = model.getMucGiam();
        this.giaTriDonHang = model.getGiaTriDonHang();
        this.ngayBatDau = model.getNgayBatDau();
        this.ngayKetThuc = model.getNgayKetThuc();
        this.soLuong = model.getSoLuong();
        this.mucGiamToiDa = model.getMucGiamToiDa();
        this.hinhThucThanhToan = model.getHinhThucThanhToan() == 0 ? "Thanh toán khi nhận hàng" :
                model.getHinhThucThanhToan() == 1 ? "Thanh toán online" :
                        "Tất cả các hình thức thanh toán";
        this.trangThaiXoa = model.getTrangThaiXoa();
        this.trangThai = model.getTrangThai();
        this.doiTuongSuDung = model.getDoiTuongSuDung() == 0 ? "Tất cả khách hàng" : "Tùy chọn khách";
        this.soLuongSuDung = model.getSoLuongSuDung();
        if (this.doiTuongSuDung.equals("Tùy chọn khách")) {
            this.danhSachKhachHang = model.getKhachHang();
        }
    }
}
