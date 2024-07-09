package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DonHangDTORequest {
    private String ma;
    private KhachHangModel nguoiSoHuu;
    private String voucher;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    @Size(max = 100, message = "Diạ chỉ chi tiết tối 100 ký tự")
    private String tenNguoiNhan;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại không đúng định dạng")
    private String soDienThoai;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    @Email(message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    private String thanhPhoName;
    @NotNull(message = "Vui lòng nhập dữ liệu")
    private Integer thanhPhoCode;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    private String quanHuyenName;
    @NotNull(message = "Vui lòng nhập dữ liệu")
    private Integer quanHuyenCode;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    private String xaPhuongName;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    private String xaPhuongCode;
    @NotBlank(message = "Vui lòng nhập dữ liệu")
    @Size(max = 255, message = "Diạ chỉ chi tiết tối 255 ký tự")
    private String diaChiChiTiet;
    private Date ngayDatHang;
    private Date ngayHoanHang;
    private Date ngayHuyHoan;
    private Date ngayKiemTra;
    private Date ngayGiaoHoan;
    private Date ngayHoanThanhHoan;
    private Integer trangThai;
    @Size(max = 255, message = "Diạ chỉ chi tiết tối 255 ký tự")
    private String ghiChu;
    private BigDecimal tienGiam;
    private BigDecimal phiGiaoHang;
    private int phuongThucThanhToan;
    private String trangThaiDetail;
    //    @NotBlank(message = "Nhập lý do hủy đơn")
    private String lyDoHuy;
    private String lyDoThayDoi;
    private String lyDoHuyHoan;
    private String tongTien;
    private Integer loai;
    private Integer trangThaiHoan;
    private String nhanVien;

    public DonHangModel mapModel() {
        DonHangModel donHangModel = new DonHangModel();
        if (this.voucher != null && this.voucher.length() > 0) {
            VoucherModel voucherModel = new VoucherModel();
            voucherModel.setMa(voucher);
            donHangModel.setVoucher(voucherModel);
        }
        donHangModel.setMa(ma);
        if (nguoiSoHuu != null) donHangModel.setNguoiSoHuu(nguoiSoHuu);
        if (lyDoHuy != null && !lyDoHuy.isBlank()) donHangModel.setLyDoHuy(lyDoHuy);
        donHangModel.setTenNguoiNhan(tenNguoiNhan);
        donHangModel.setSoDienThoai(soDienThoai);
        donHangModel.setEmail(email);
        donHangModel.setThanhphoName(thanhPhoName);
        donHangModel.setThanhPhoCode(thanhPhoCode);
        donHangModel.setQuanHuyenName(quanHuyenName);
        donHangModel.setQuanHuyenCode(quanHuyenCode);
        donHangModel.setXaPhuongName(xaPhuongName);
        donHangModel.setXaPhuongCode(xaPhuongCode);
        donHangModel.setDiaChiChiTiet(diaChiChiTiet);
        donHangModel.setNgayDatHang(ngayDatHang);
        donHangModel.setTrangThai(2);
        donHangModel.setGhiChu(ghiChu);
        donHangModel.setTienGiam(tienGiam == null ? BigDecimal.valueOf(0) : tienGiam);
        donHangModel.setPhiGiaoHang(phiGiaoHang == null? BigDecimal.valueOf(0) : phiGiaoHang);
        donHangModel.setPhuongThucThanhToan(phuongThucThanhToan == 1 ? false : true);
        donHangModel.setNgayHoanHang(ngayHoanHang);
        donHangModel.setLyDoThayDoi(lyDoThayDoi);
        donHangModel.setTrangThaiHoan(trangThaiHoan);
        donHangModel.setLyDoHuyHoan(lyDoHuyHoan);
        donHangModel.setNgayHuyHoan(ngayHuyHoan);
        donHangModel.setNgayGiaoHoan(ngayGiaoHoan);
        donHangModel.setNgayKiemTra(ngayKiemTra);
        donHangModel.setNgayHoanThanhHoan(ngayHoanThanhHoan);
        if(loai!=null){
            donHangModel.setLoai(this.loai);
        }
        if(nhanVien!=null){
            NhanVienModel nhanVienModel = new NhanVienModel();
            nhanVienModel.setUsername(nhanVien);
            donHangModel.setNhanVien(nhanVienModel);
        }
        return donHangModel;
    }
}
