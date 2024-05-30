package fpoly.duantotnghiep.shoppingweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fpoly.duantotnghiep.shoppingweb.enumtype.KhuyenMaiType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "voucher")
public class VoucherModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @Column(name = "mota")
    private String mota;

    @Column(name = "loaimucgiam")
    private String loaiMucGiam;

    @Column(name = "mucgiam")
    private Double mucGiam;

    @Column(name = "giatridonhang")
    private Double giaTriDonHang;

    @Column(name = "mucgiamtoida")
    private Double mucGiamToiDa;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "soluong")
    private int soLuong;

    @Column(name = "trangthaixoa")
    private Integer trangThaiXoa;

    @Column(name = "hinhthucthanhtoan")
    private Integer hinhThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Formula("( SELECT count(d.Voucher) FROM Voucher v join donhang d on d.Voucher = v.Ma WHERE v.Ma = ma )")
    private int soLuongSuDung;

    @Column(name = "doituongsudung")
    private Integer doiTuongSuDung;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "voucherkhachhang",
            joinColumns = {@JoinColumn(name = "voucher")},
            inverseJoinColumns = {@JoinColumn(name = "khachhang")})
    @JsonBackReference
    private List<KhachHangModel> khachHang;

}
