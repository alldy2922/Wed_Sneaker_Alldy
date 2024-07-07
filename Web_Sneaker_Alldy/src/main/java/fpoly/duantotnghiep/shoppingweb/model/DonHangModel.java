package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "donhang")
public class DonHangModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "Khachhang")
    private KhachHangModel nguoiSoHuu;

    @ManyToOne
    @JoinColumn(name = "nhanvien")
    private NhanVienModel nhanVien;

    @ManyToOne
    @JoinColumn(name = "voucher")
    private VoucherModel voucher;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "thanhphoName")
    private String thanhphoName;

    @Column(name = "thanhphoCode")
    private Integer thanhPhoCode;

    @Column(name = "quanhuyenName")
    private String quanHuyenName;

    @Column(name = "quanhuyenCode")
    private Integer quanHuyenCode;

    @Column(name = "xaphuongName")
    private String xaPhuongName;

    @Column(name = "xaphuongCode")
    private String xaPhuongCode;

    @Column(name = "diachichitiet")
    private String diaChiChiTiet;


    @Column(name = "ngaydathang")
    @CreationTimestamp
    private Date ngayDatHang;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "tiengiam")
    private BigDecimal tienGiam;

    @Column(name = "phigiaohang")
    private BigDecimal phiGiaoHang;

    @Column(name = "phuongthucthanhtoan")
    private Boolean phuongThucThanhToan;

    @Column(name = "lydohuy")
    private String lyDoHuy;

    @Column(name = "lydothaydoi")
    private String lyDoThayDoi;

    @Column(name = "ngaytrahang")
    private Date ngayTraHang;

    @Column(name = "lydotrahang")
    private String lyDoTraHang;

    @Column(name = "ngayxacnhan")
    private Date ngayXacNhan;

    @Column(name = "ngaygiaohang")
    private Date ngayGiaoHang;

    @Column(name = "ngayhoanthanh")
    private Date ngayHoanThanh;

    @Column(name = "ngayhuy")
    private Date ngayHuy;

    @Column(name = "loai",columnDefinition = "INT default 0")
    private Integer loai;

    @Formula("(SELECT SUM(c.dongiasaugiam*c.SoLuong) + d.phigiaohang - d.tiengiam FROM donhang d Join chitietdonhang c on c.DonHang = d.Ma where d.Ma = ma)")
    private BigDecimal tongTien;

    @OneToMany(mappedBy = "donHang",fetch = FetchType.LAZY)
    private List<ChiTietDonHangModel> danhSachSanPham;

    public String trangThaiDetail() {
        if (trangThai == 1) {
            return "Đã xác nhận";
        } else if (trangThai == 2) {
            return "Chưa xác nhận";
        } else {
            return "null";
        }
    }

    public String getLyDoHuy() {
        if(lyDoHuy==null) return "";
        return lyDoHuy;
    }

    @Override
    public String toString() {
        return "DonHangModel{" +
                "ma='" + ma + '\'' +
                ", nguoiSoHuu=" + (nguoiSoHuu == null ? "" : nguoiSoHuu.getUsername()) +
                ", tenNguoiNhan='" + tenNguoiNhan + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                ", thanhphoName='" + thanhphoName + '\'' +
                ", thanhPhoCode=" + thanhPhoCode +
                ", quanHuyenName='" + quanHuyenName + '\'' +
                ", quanHuyenCode=" + quanHuyenCode +
                ", xaPhuongName='" + xaPhuongName + '\'' +
                ", xaPhuongCode='" + xaPhuongCode + '\'' +
                ", diaChiChiTiet='" + diaChiChiTiet + '\'' +
                ", ngayDatHang=" + ngayDatHang +
                ", trangThai=" + trangThai +
                ", ghiChu='" + ghiChu + '\'' +
//                ", tienGiam=" + tienGiam +
                ", phiGiaoHang=" + phiGiaoHang +
                '}';
    }
}
