package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class XuatXuRequest {
    private String id;
    @NotBlank(message = "Không được để trống tên!!!")
    @Length(max = 100, message = "Tên tối đa 100 ký tự")
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public XuatXuModel mapXuatXuToModel(){
        XuatXuModel model = new XuatXuModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
