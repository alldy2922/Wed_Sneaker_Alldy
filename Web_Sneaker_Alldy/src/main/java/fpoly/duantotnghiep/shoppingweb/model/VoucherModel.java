package fpoly.duantotnghiep.shoppingweb.model;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.text.DecimalFormat;
import java.time.LocalDate;
=======
import java.util.Date;
import java.util.List;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "voucher")
public class VoucherModel {
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
