package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DongSanPhamRequest {
    private String id;
    @NotBlank(message = "Không được để trống tên!!")
    @Length(max = 100, message = "Tên tối đa 100 ký tự")
    private String ten;
    private String thuongHieu;
    private Date ngayTao;
    private Date ngayCapNhat;

    public DongSanPhamModel maptomodel(){
        DongSanPhamModel model = new DongSanPhamModel();
        model.setId(id);
        model.setTen(ten);
        if(thuongHieu != null && !thuongHieu.isBlank()) model.setThuongHieu(new ThuongHieuModel(thuongHieu));
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
