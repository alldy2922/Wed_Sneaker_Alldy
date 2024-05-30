package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDtoReponse {
    private String username;
    private String password;
    private String hoTen;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String soDienThoai;
    private String email;
    private String anhDaiDien;

    public KhachHangDtoReponse(KhachHangModel khachHangModel) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.anhDaiDien = anhDaiDien;
    }
}
