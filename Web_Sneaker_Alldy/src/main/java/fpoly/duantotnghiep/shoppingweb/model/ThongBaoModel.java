package fpoly.duantotnghiep.shoppingweb.model;

import fpoly.duantotnghiep.shoppingweb.enumtype.ThongBaoType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "thongbao")
public class ThongBaoModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "nhanvien")
    private NhanVienModel taiKhoanGui;

    @Column(name = "loaithongbao")
    private String loaiThongBao;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "thoigian")
    private Date thoiGianGui;

    @Column(name = "url")
    private String url;

    public void setLoaiThongBao(ThongBaoType loaiThongBao) {
        this.loaiThongBao = loaiThongBao.name();
    }

}
