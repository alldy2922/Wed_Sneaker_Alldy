package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KhuyenMaiResponse {
    private String ma;

    private String ten;

    private String loai;

    private BigDecimal mucGiam;

    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;

    private Date ngayTao;
    private Integer trangThai;

    private Date ngayCapNhat;

    private List<SanPhamModel> sanPham;

    public KhuyenMaiResponse(KhuyenMaiModel model) {
        this.ma = model.getMa();
        this.ten = model.getTen();
        this.loai = model.getLoai();
        this.mucGiam = model.getMucGiam();
        this.ngayBatDau = model.getNgayBatDau();
        this.ngayKetThuc = model.getNgayKetThuc();
        this.ngayTao = model.getNgayTao();
        this.trangThai = model.getTrangThai();
        this.ngayCapNhat = model.getNgayCapNhat();
        this.sanPham = model.getSanPham();
    }
}
