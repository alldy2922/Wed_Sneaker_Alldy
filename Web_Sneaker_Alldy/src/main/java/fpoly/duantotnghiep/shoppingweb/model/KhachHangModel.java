package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import lombok.ToString;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "khachhang")
public class KhachHangModel {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @Column(name = "ngaysinh")
    private LocalDate ngaySinh;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "anhdaidien")
    private String anhDaiDien;

<<<<<<< HEAD
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "voucherkhachhang",
//            joinColumns = {@JoinColumn(name = "khachhang")},
//            inverseJoinColumns = {@JoinColumn(name = "voucher")})
//    private List<VoucherModel> voucher;
=======
//    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private List<DiaChiModel> danhSachDiaChi;
//
//    @OneToMany(mappedBy = "nguoiSoHuu",fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @JsonBackReference
//    private List<DonHangModel> danhSachDonHang;


//    @ManyToMany
//    @JoinTable(name = "voucher_taikhoan",
//            joinColumns = { @JoinColumn(name = "voucher") },
//            inverseJoinColumns = { @JoinColumn(name = "taikhoan") })
//    private Set<VoucherModel> vouchers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "voucherkhachhang",
            joinColumns = {@JoinColumn(name = "khachhang")},
            inverseJoinColumns = {@JoinColumn(name = "voucher")})
    private List<VoucherModel> voucher;

    public KhachHangModel(String username){
        this.username = username;
    }

//    @PreRemove
//    public void preRemove(){
//        danhSachDonHang.forEach(d -> d.setNguoiSoHuu(null));
//    }

>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
}
