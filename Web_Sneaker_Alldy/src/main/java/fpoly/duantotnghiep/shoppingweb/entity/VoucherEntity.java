package fpoly.duantotnghiep.shoppingweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "voucher")
public class VoucherEntity {
    @Id
    private String ma;

    private String moTa;

    private String loaiMucGiam;

    private Double mucGiam;

    private DecimalFormat giaTriDonHang;

    private DecimalFormat mucGiamToiDa;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    private Long soLuong;

    private Boolean trangThaiXoa;

    private Integer hinhThucThanhToan;

    private Integer trangThai;

    private Boolean doiTuongSuDung;
}
