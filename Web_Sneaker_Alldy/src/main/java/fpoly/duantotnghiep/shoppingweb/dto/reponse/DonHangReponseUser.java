package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DonHangReponseUser {
    private String ma;
    private String nguoiSoHuu;
    private String tenNguoiNhan;
    private String soDienThoai;
    private String email;
    private String thanhPhoName;
    private Integer thanhPhoCode;
    private String quanHuyenName;
    private Integer quanHuyenCode;
    private String xaPhuongName;
    private String xaPhuongCode;
    private String diaChiChiTiet;
    private Date ngayDatHang;
    private Integer trangThai;
    private Integer trangThaiHoan;
    private String ghiChu;
    private BigDecimal tienGiam;
    private BigDecimal phiGiaoHang;
    private String trangThaiDetail;
    private String phuongThucThanhToan;
    private String lyDoHuy;
    private String lyDoThayDoi;
    private String voucherCode;
    private String voucherName;
    private Date ngayXacNhan;
    private Date ngayGiaoHang;
    private Date ngayHoanThanh;
    private Date ngayHoanHang;
    private Date ngayHuy;
    private BigDecimal tongTien;
    private List<ChiTietDonHangDtoResponse> chiTietDonHang;
    private NhanVienDtoResponse nhanVienDtoResponse;


    public DonHangReponseUser(DonHangModel model) {
        this.ma = model.getMa();
        this.nguoiSoHuu = model.getNguoiSoHuu() == null ? "" : model.getNguoiSoHuu().getHoVaTen();
        this.tenNguoiNhan = model.getTenNguoiNhan();
        this.soDienThoai = model.getSoDienThoai();
        this.email = model.getEmail();
        this.thanhPhoName = model.getThanhphoName();
        this.thanhPhoCode = model.getThanhPhoCode();
        this.quanHuyenName = model.getQuanHuyenName();
        this.quanHuyenCode = model.getQuanHuyenCode();
        this.xaPhuongName = model.getXaPhuongName();
        this.xaPhuongCode = model.getXaPhuongCode();
        this.diaChiChiTiet = model.getDiaChiChiTiet();
        this.ngayDatHang = model.getNgayDatHang();
        this.ngayHoanHang = model.getNgayHoanHang();
        this.trangThai = model.getTrangThai();
        this.ghiChu = model.getGhiChu();
        this.tienGiam = model.getTienGiam() == null ? BigDecimal.valueOf(0) : model.getTienGiam();
        this.phiGiaoHang = model.getPhiGiaoHang();
        this.trangThaiDetail = model.trangThaiDetail();
        this.phuongThucThanhToan = model.getPhuongThucThanhToan() == true ? "Thanh toán khi nhận hàng" : "Thanh toán online";
        this.lyDoHuy = model.getLyDoHuy();
        this.lyDoThayDoi = model.getLyDoThayDoi();
        this.trangThaiHoan = model.getTrangThaiHoan();

        if (model.getNgayXacNhan() != null) this.ngayXacNhan = model.getNgayXacNhan();
        if (model.getNgayGiaoHang() != null) this.ngayGiaoHang = model.getNgayGiaoHang();
        if (model.getNgayHoanThanh() != null) this.ngayHoanThanh = model.getNgayHoanThanh();
        if (model.getNgayHuy() != null) this.ngayHuy = model.getNgayHuy();
        this.tongTien = model.getTongTien();
        if (model.getVoucher() != null) {
            voucherCode = model.getVoucher().getMa();
            voucherName = model.getVoucher().getMota();
        }
        if(model.getNhanVien()!=null){
            nhanVienDtoResponse = new NhanVienDtoResponse(model.getNhanVien());
        }
        this.chiTietDonHang = model.getDanhSachSanPham().stream().map(s -> new ChiTietDonHangDtoResponse(s)).collect(Collectors.toList());

    }


}
