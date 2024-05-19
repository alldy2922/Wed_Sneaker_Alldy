package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "voucher")
public class VoucherModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @Column(name = "mota")
    private String mota;

    @Column(name = "loaimucgiam")
    private String loaiMucGiam;

    @Column(name = "mucgiam")
    private Double mucGiam;

    @Column(name = "giatridonhang")
    private Double giaTriDonHang;

    @Column(name = "mucgiamtoida")
    private Double mucGiamToiDa;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "soluong")
    private int soLuong;

    @Column(name = "trangthaixoa")
    private Integer trangThaiXoa;

    @Column(name = "hinhthucthanhtoan")
    private Integer hinhThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;


}
