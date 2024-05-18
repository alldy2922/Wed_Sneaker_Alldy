package fpoly.duantotnghiep.shoppingweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "khachhang")
public class KhachHangEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "hovaten")
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "anhdaidien")
    private String anhDaiDien;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "voucherkhachhang",
            joinColumns = {@JoinColumn(name = "khachhang")},
            inverseJoinColumns = {@JoinColumn(name = "voucher")})
    private List<VoucherEntity> voucher;
}
