package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diachi")
public class DiaChiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "thanhphoname")
    private String thanhPhoName;

    @Column(name = "thanhphocode")
    private Integer thanhPhoCode;

    @Column(name = "quanhuyenname")
    private String quanHuyenName;

    @Column(name = "quanhuyencode")
    private Integer quanHuyenCode;

    @Column(name = "xaphuongname")
    private String xaPhuongName;

    @Column(name = "xaphuongcode")
    private String xaPhuongCode;

    @Column(name = "diachichitiet")
    private String diaChiChiTiet;

    @Column(name = "macdinh")
    private Boolean macDinh;
}