package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "thongbaonhan")
public class ThongBaoNhanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "thongbao")
    private ThongBaoModel thongBaoGui;

    @ManyToOne
    @JoinColumn(name = "nhanvien")
    private NhanVienModel nguoiNhan;

    @Column(name = "trangthai")
    private Boolean trangThai;
}
