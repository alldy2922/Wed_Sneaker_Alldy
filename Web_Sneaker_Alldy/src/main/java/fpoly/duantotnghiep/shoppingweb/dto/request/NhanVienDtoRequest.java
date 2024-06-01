package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.model.VaiTroModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienDtoRequest {
    @NotBlank(message = "Không để trống username")
    @Pattern(regexp = "[a-zA-Z0-9]{1,20}", message = "username chỉ chứa chữ và số, và giới hạn 20 ký tự")
    private String username;

    @NotBlank(message = "Vui lòng chọn vai trò")
    private String vaiTro;
    private String password;

    @NotBlank(message = "Không để trống họ và tên")
    private String hoVaTen;

    private Boolean gioiTinh;
    private LocalDate ngaySinh;

    @NotBlank(message = "Không để trống số điện thoại")
    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại không đúng định dạng")
    private String soDienThoai;

    @NotBlank(message = "Không để trống email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private String anhDaiDien;

    public NhanVienModel mapToModel() {
        NhanVienModel nhanVienModel = new NhanVienModel();
        nhanVienModel.setUsername(username);

        VaiTroModel vaiTroModel = new VaiTroModel();
        vaiTroModel.setMa(vaiTro);
        nhanVienModel.setVaiTro(vaiTroModel);

        nhanVienModel.setPassword(password);
        nhanVienModel.setHoVaTen(hoVaTen);
        nhanVienModel.setGioiTinh(gioiTinh);
        nhanVienModel.setNgaySinh(ngaySinh);
        nhanVienModel.setSoDienThoai(soDienThoai);
        nhanVienModel.setEmail(email);
        nhanVienModel.setAnhDaiDien(anhDaiDien);

        return nhanVienModel;
    }
}
