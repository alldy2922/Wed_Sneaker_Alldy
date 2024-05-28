package fpoly.duantotnghiep.shoppingweb.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "chitietsanpham")
public class ChiTietSanPhamModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "sanpham")
    @ToString.Exclude
    private SanPhamModel sanPham;

    @ManyToOne
    @JoinColumn(name = "size")
    private SizeModel size;

    @Column(name = "soluong")
    private Long soLuong;

    @Column(name = "trangthai")
    private Boolean trangThai;

    @Column(name = "ngaytao")
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngaycapnhap")
    @UpdateTimestamp
    private Date ngayCapNhat;

    @OneToMany(mappedBy = "chiTietSanPham",fetch = FetchType.EAGER)
    private List<ChiTietSanPhamModel> chiTietDonHangList;

    public ChiTietSanPhamModel(String sanPham) {
    }

    public Boolean kiemTraCoTrongDonHang(){
        if(chiTietDonHangList.size()==0)return false;
        else return true;
    }
}
