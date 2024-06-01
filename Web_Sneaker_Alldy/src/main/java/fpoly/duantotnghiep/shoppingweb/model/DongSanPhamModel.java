package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "dongsanpham")

public class DongSanPhamModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "ten")
    private String ten;

    @ManyToOne
    @JoinColumn(name = "thuonghieu")
    private ThuongHieuModel thuongHieu;

    @Column(name = "ngaytao")
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    @UpdateTimestamp
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "dongSanPham",fetch = FetchType.LAZY)
    private List<SanPhamModel> danhSachSanPham;

    @PreRemove
    private void preRemove(){
        danhSachSanPham.forEach(s -> s.setDongSanPham(null));
    }

    public DongSanPhamModel(String id) {
        this.id = id;
    }
}
