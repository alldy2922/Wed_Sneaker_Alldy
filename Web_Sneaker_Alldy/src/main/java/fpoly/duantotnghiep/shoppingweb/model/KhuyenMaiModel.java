package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "khuyenmai")

public class KhuyenMaiModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @NotEmpty(message = "Vui lòng nhập dữ liệu")
    @Column(name = "ten")
    private String ten;

    @Column(name = "loai")
    private String loai;

    //    @NotNull(message = "Vui lòng nhập dữ liệu")
    @Column(name = "mucgiam")
    private BigDecimal mucGiam;

    //    @NotEmpty(message = "Vui lòng nhập dữ liệu")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    //    @NotEmpty(message = "Vui lòng nhập dữ liệu")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @CreationTimestamp
    @Column(name = "ngaytao")
    private Date ngayTao;

    @UpdateTimestamp
    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "khuyenmai_sanpham",
            joinColumns = {@JoinColumn(name = "khuyenmai")},
            inverseJoinColumns = {@JoinColumn(name = "sanpham")})
    private List<SanPhamModel> sanPham;

}
