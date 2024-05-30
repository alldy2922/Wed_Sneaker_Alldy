package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ThuongHieuDtoRequest {
    private String id;
    @NotBlank(message = "Không được để trống tên!!!")
    @Length(max = 100, message = "Tên tối đa 100 ký tự")
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public ThuongHieuModel mapToModel(){
        ThuongHieuModel model = new ThuongHieuModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return  model;
    }
}
