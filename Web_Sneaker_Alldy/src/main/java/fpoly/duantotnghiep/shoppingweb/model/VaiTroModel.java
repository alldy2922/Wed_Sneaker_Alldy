package fpoly.duantotnghiep.shoppingweb.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "vaitro")
public class VaiTroModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "vaiTro",fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<NhanVienModel> danhSachNhanVien;
}
