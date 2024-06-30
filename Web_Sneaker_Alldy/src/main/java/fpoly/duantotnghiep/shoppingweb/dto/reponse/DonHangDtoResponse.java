package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class DonHangDtoResponse {
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
    private Date ngayHuy;
    private BigDecimal tongTien;

    private NhanVienDtoResponse nhanVienDtoResponse;

    public DonHangDtoResponse(DonHangModel model) {
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
        this.trangThai = model.getTrangThai();
        this.ghiChu = model.getGhiChu();
        this.tienGiam = model.getTienGiam() == null ? BigDecimal.valueOf(0) : model.getTienGiam();
        this.phiGiaoHang = model.getPhiGiaoHang();
        this.trangThaiDetail = model.trangThaiDetail();
        this.phuongThucThanhToan = model.getPhuongThucThanhToan()+"";
        this.lyDoHuy = model.getLyDoHuy();
        this.lyDoThayDoi = model.getLyDoThayDoi();

        if(model.getNgayXacNhan()!=null) this.ngayXacNhan = model.getNgayXacNhan();
        if(model.getNgayGiaoHang()!=null) this.ngayGiaoHang = model.getNgayGiaoHang();
        if(model.getNgayHoanThanh()!=null) this.ngayHoanThanh = model.getNgayHoanThanh();
        if(model.getNgayHuy()!=null) this.ngayHuy = model.getNgayHuy();

        if(model.getVoucher()!=null){
            voucherCode = model.getVoucher().getMa();
            voucherName = model.getVoucher().getMota();
        }
        this.tongTien = model.getTongTien();
        if(model.getNhanVien()!=null){
            nhanVienDtoResponse = new NhanVienDtoResponse(model.getNhanVien());
        }
    }
}
