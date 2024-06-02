package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDtoResponse {

    private String username;
    private String password;
    private String hoVaTen;
    private Boolean gioiTinh;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String email;
    private String anhDaiDien;

    public KhachHangDtoResponse(KhachHangModel model) {
        this.username = model.getUsername();
        this.password = model.getPassword();
        this.hoVaTen = model.getHoVaTen();
        this.gioiTinh = model.getGioiTinh();
        this.ngaySinh = model.getNgaySinh();
        this.soDienThoai = model.getSoDienThoai();
        this.email = model.getEmail();
        this.anhDaiDien = model.getAnhDaiDien();
    }
}
