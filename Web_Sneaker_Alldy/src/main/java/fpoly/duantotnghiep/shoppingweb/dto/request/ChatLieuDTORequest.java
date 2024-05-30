package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChatLieuModel;
import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatLieuDTORequest {
    private String id;
    @NotBlank(message = "Không để trống tên")
    @Length(max = 100, message = "Tên tối đa 100 ký tự")
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public ChatLieuModel mapToModel() {
        ChatLieuModel model = new ChatLieuModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
