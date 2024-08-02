package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "chitietdonhang")
public class ChiTietDonHangModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

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

    @Column(name = "dongiasaugiam")
    private BigDecimal donGiaSauGiam;

    @OneToOne(mappedBy = "chiTietDonHangModel", fetch = FetchType.EAGER)
    private NhanXetModel nhanXet;

    @Override
    public String toString() {
        return "ChiTietDonHangModel{" +
                "id='" + id + '\'' +
                ", donHang=" + donHang +
                ", chiTietSanPham=" + chiTietSanPham +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", donGiaSauGiam=" + donGiaSauGiam +
                ", nhanXet=" + nhanXet +
                '}';
    }
}
