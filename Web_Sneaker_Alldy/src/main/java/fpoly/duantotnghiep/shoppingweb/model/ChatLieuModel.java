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
@Table(name = "chatlieu")
public class ChatLieuModel {
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

    @OneToMany(mappedBy = "chatLieu")
    @ToString.Exclude
    private List<SanPhamModel> sanPham;

    @PreRemove
    private void preRemove(){
        sanPham.forEach(s -> s.setChatLieu(null));
    }

    public ChatLieuModel(String id) {
        this.id = id;
    }
}
