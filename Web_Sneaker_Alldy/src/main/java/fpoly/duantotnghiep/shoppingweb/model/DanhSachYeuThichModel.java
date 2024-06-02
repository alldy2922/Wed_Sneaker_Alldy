package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "danhsachyeuthich")
public class DanhSachYeuThichModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "Khachhang")
    private KhachHangModel nguoiSoHuu;

    @ManyToOne
    @JoinColumn(name = "sanpham")
    private SanPhamModel sanPham;

    @Override
    public String toString() {
        return "DanhSachYeuThichModel{" +
                "id='" + id + '\'' +
                ", nguoiSoHuu=" + nguoiSoHuu +
                ", sanPham=" + sanPham +
                '}';
    }
}
