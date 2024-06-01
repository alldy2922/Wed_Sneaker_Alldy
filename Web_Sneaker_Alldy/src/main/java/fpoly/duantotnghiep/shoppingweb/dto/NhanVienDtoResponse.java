package fpoly.duantotnghiep.shoppingweb.dto;

import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienDtoResponse {
    private String vaiTro;
    private String tenVaiTro;
    private String username;
    private String hoVaTen;
    private Boolean gioiTinh;
    private String gioiTinhName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String email;
    private String anhDaiDien;

    public NhanVienDtoResponse(NhanVienModel model) {
        this.vaiTro = model.getVaiTro().getMa();
        this.tenVaiTro = model.getVaiTro().getTen();
        this.username = model.getUsername();
        this.hoVaTen = model.getHoVaTen();
        this.gioiTinhName = model.getGioiTinh() == null ? "Không xác định" : model.getGioiTinh() == true ? "Nam" : "Nữ";
        this.gioiTinh = model.getGioiTinh();
        this.ngaySinh = model.getNgaySinh();
        this.soDienThoai = model.getSoDienThoai();
        this.email = model.getEmail();
        this.anhDaiDien = model.getAnhDaiDien();
    }
}
