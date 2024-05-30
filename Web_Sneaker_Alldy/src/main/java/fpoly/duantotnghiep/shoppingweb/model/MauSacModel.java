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
@Table(name = "mausac")
public class MauSacModel {
    @Id
    @Column(name = "ma")
    @UuidGenerator
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngaytao")
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    @UpdateTimestamp
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "mauSac")
    @ToString.Exclude
    private List<SanPhamModel> sanPham;

    @PreRemove
    private void preRemove(){
        sanPham.forEach(s -> s.setMauSac(null));
    }

    public MauSacModel(String ma) {
        this.ma = ma;
    }
}
