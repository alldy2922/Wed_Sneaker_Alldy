package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "chitietdonhangtra")
@Entity
public class DonHangTraModel {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "madonhangtra")
    private String maDonHangTra;

    @ManyToOne
    @JoinColumn(name = "donhang")
    @ToString.Exclude
    private DonHangModel donHang;

    @ManyToOne
    @JoinColumn(name = "chitietsanpham")
    private ChiTietSanPhamModel chiTietSanPham;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "dongia")
    private BigDecimal donGia;

    @Column(name = "dongiagiamsau")
    private BigDecimal donGiaSauGiam;

    @Column(name = "ngayxacnhan")
    private Date ngayXacNhan;

  @Column(name = "ngaykiemtra")
    private Date ngayKiemTra;

  @Column(name = "ngayhoanthanh")
    private Date ngayHoanThanh;

  @Column(name = "trangthai")
    private Integer trangThai;

}
