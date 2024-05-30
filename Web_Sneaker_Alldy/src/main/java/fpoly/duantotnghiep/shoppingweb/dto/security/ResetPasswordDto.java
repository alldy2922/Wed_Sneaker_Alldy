package fpoly.duantotnghiep.shoppingweb.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Vui lòng nhập mật khẩu cũ")
    private String oldPass;
    @NotBlank(message = "Vui lòng nhập mật khẩu mới")
    @Pattern(regexp = "[a-zA-Z0-9]{5,20}",message = "Mật khẩu chỉ được chứa 5 - 20 các số và chữ cái không dấu")
    private String newPass;
    private String verifyNewPass;

    public Boolean checkVerifyPassword(){
        return verifyNewPass.equals(newPass);
    }
}
