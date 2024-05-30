package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giohang")
public class GioHangModel {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "soluong")
    private Integer soLuong;

    @ManyToOne
    @JoinColumn(name = "khachhang")
    private KhachHangModel khachHang;

    @ManyToOne
    @JoinColumn(name = "chitietsanpham")
    private ChiTietSanPhamModel chiTietSanPham;
}
