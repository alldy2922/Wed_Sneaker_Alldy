package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "thuonghieu")
public class ThuongHieuModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngaytao")
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    @UpdateTimestamp
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "thuongHieu",fetch = FetchType.EAGER)
    private List<DongSanPhamModel> dongSanPhams= new ArrayList<>();

    public ThuongHieuModel(String id) {
        this.id = id;
    }
}
