package fpoly.duantotnghiep.shoppingweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ToString
@Table(name = "anh")
public class AnhModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sanpham")
    @JsonBackReference
    private SanPhamModel sanPham;

    @Column(name = "ten")
    private String ten;

    @Override
    public String toString() {
        return "AnhModel{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                '}';
    }
}
