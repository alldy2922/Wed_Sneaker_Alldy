package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangDTORequest {
    @NotBlank(message = "Không để trống username")
    @Pattern(regexp = "[a-zA-Z0-9]{1,20}",message = "username chỉ chứa chữ và số, và giới hạn 20 ký tự")
    private String username;
    @NotBlank(message = "Không để trống password")
    private String password;
    @NotBlank(message = "Không để trống họ và tên")
    @Size(max = 100,message = "Họ và tên tối đa 100 ký tự")
    private String hoVaTen;

    private Boolean gioiTinh;
    private LocalDate ngaySinh;

    @NotBlank(message = "Không để trống số điện thoại")
    @Pattern(regexp = "0\\d{9}",message = "Số điện thoại không đúng định dạng")
    private String soDienThoai;

    @NotBlank(message = "Không để trống email")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private String anhDaiDien;

    public KhachHangModel mapToModel(){
        KhachHangModel khachHangModel = new KhachHangModel();
        khachHangModel.setUsername(username);

        khachHangModel.setPassword(password);

        khachHangModel.setHoVaTen(hoVaTen);

        khachHangModel.setGioiTinh(gioiTinh);

        khachHangModel.setNgaySinh(ngaySinh);

        khachHangModel.setSoDienThoai(soDienThoai);

        khachHangModel.setEmail(email);

        khachHangModel.setAnhDaiDien(anhDaiDien);
        return khachHangModel;
    }
}
