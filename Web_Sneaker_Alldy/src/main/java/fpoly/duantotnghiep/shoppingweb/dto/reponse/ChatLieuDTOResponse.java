package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChatLieuModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatLieuDTOResponse {
    private String id;
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public ChatLieuDTOResponse(ChatLieuModel model) {
        id = model.getId();
        ten = model.getTen();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
    }
}
