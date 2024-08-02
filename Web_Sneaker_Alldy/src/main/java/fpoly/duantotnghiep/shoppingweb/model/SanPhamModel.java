package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ToString
@Table(name = "sanpham")
public class SanPhamModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "mausac")
    private MauSacModel mauSac;

    @ManyToOne
    @JoinColumn(name = "dongsp")
    private DongSanPhamModel dongSanPham;

    @ManyToOne
    @JoinColumn(name = "xuatxu")
    private XuatXuModel xuatXu;

    @ManyToOne
    @JoinColumn(name = "kieudang")
    private KieuDangModel kieuDang;

    @ManyToOne
    @JoinColumn(name = "chatlieu")
    private ChatLieuModel chatLieu;

    @Column(name = "ten")
    private String ten;

    @Column(name = "gianhap")
    private BigDecimal giaNiemYet;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "ngaytao")
    @CreationTimestamp
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    @UpdateTimestamp
    private Date ngayCapNhat;

    @Column(name = "hienthi")
    private Boolean hienThi;

    @Column(name = "trangthai")
    private Boolean trangThai;

//    @Transient
    @Formula("(SELECT SUM(c.soluong) FROM chitietsanpham c WHERE c.sanpham = ma AND c.TrangThai = 1)")
    private Long soLuong;

    @Formula("""
            (SELECT k.MucGiam FROM khuyenmai_sanpham ks join sanpham s on s.Ma = ks.SanPham
             									join KhuyenMai k on k.Ma = ks.KhuyenMai
             where k.NgayBatDau <= current_date And k.NgayKetThuc >=current_date and s.Ma = ma AND k.TrangThai = 0)
            """)
    private Float mucGiam;
    @Formula("""
            (SELECT k.Loai FROM khuyenmai_sanpham ks join sanpham s on s.Ma = ks.SanPham
             									join KhuyenMai k on k.Ma = ks.KhuyenMai
             where k.NgayBatDau <= current_date And k.NgayKetThuc >=current_date and s.Ma = ma AND k.TrangThai = 0) 
            """)
    private String loaiGiam;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.EAGER)
    private List<AnhModel> Images;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.EAGER)
    private List<ChiTietSanPhamModel> ctsp;


    public Long getSoLuongSanPham() {
        if (ctsp == null) return 0L;
        return ctsp.stream().filter(c -> c.getTrangThai()==true).map(c -> c.getSoLuong()).reduce(0L, (c1, c2) -> c1 + c2);
    }

    public BigDecimal getDonGiaSauGiam(){
        if(this.mucGiam == null) return giaBan;

        else{
            if(loaiGiam.equalsIgnoreCase("TIEN")){
                return this.giaBan.subtract(BigDecimal.valueOf(this.mucGiam));
            }else{
                BigDecimal tienGiam = giaBan.multiply(BigDecimal.valueOf(mucGiam).divide(BigDecimal.valueOf(100)));
                System.out.println(tienGiam);
                return this.giaBan.subtract(tienGiam);
            }
        }
    }

//    public Long getSoLuong() {
//        if (ctsp == null) return 0L;
//        return ctsp.stream().filter(c -> c.getTrangThai()==true).map(c -> c.getSoLuong()).reduce(0L, (c1, c2) -> c1 + c2);
//
//    }

    @Override
    public String toString() {
        return "SanPhamModel{" +
                "ma='" + ma + '\'' +
                ", mauSac=" + mauSac.getTen() +
                ", ten='" + ten + '\'' +
                ", giaNhap=" + giaNiemYet +
                ", giaBan=" + giaBan +
                ", moTa='" + moTa + '\'' +
                ", ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +
                ", hienThi=" + hienThi +
                ", trangThai=" + trangThai +
                ", ctsp=" + ctsp +
                '}';
    }

    public SanPhamModel(String ma){ this.ma = ma;}
}
