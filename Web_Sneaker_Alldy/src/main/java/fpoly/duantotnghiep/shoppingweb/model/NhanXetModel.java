package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "nhanxet")
public class NhanXetModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "chitietdonhang")
    private ChiTietDonHangModel chiTietDonHangModel;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "tieude")
    private String tieuDe;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "thoigian")
    @CreationTimestamp
    private Date thoiGian;

    @Column(name = "pheduyet")
    private Boolean pheDuyet;

    @Column(name = "dachinhsua")
    private Boolean chinhSua;

}
