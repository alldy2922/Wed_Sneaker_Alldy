package fpoly.duantotnghiep.shoppingweb.model;
<<<<<<< HEAD
=======

>>>>>>> eac474d71b53808626d26d5c9cc2ffed84c9f1b4
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nhanvien")
public class NhanVienModel {
<<<<<<< HEAD

=======
>>>>>>> eac474d71b53808626d26d5c9cc2ffed84c9f1b4
    @Id
    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "vaitro")
    private VaiTroModel vaiTro;

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

}
