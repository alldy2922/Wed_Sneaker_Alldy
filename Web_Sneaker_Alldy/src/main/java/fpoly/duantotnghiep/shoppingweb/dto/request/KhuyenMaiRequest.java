package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KhuyenMaiRequest {
    private String ma;

    @NotBlank(message = "Vui lòng nhâp dữ liệu")
    private String ten;

    private String loai;

    @NotNull(message = "Vui lòng nhâp dữ liệu")
    private BigDecimal mucGiam;

    @NotNull(message = "Vui lòng nhâp dữ liệu")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayBatDau;

    @NotNull(message = "Vui lòng nhâp dữ liệu")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    private Integer trangThai;

    private Date ngayTao;

    private Date ngayCapNhat;

    private List<SanPhamModel> sanPham;

    public KhuyenMaiModel mapToModel() {
        KhuyenMaiModel model = new KhuyenMaiModel();
        model.setMa(ma);
        model.setTen(ten);
        model.setLoai(loai);
        model.setMucGiam(mucGiam);
        model.setNgayBatDau(ngayBatDau);
        model.setNgayKetThuc(ngayKetThuc);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        model.setSanPham(sanPham);
        model.setTrangThai(trangThai);
        return model;
    }

    @Override
    public String toString() {
        return "KhuyenMaiRequest{" +
                "ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", loai='" + loai + '\'' +
                ", mucGiam=" + mucGiam +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", trangThai=" + trangThai +
                ", ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +
                ", sanPham=" +
                sanPham.stream().map(s -> s.getMa() + " - " + s.getTen()).collect(Collectors.joining());
    }
}
