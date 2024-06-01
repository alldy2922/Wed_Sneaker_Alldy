package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChiTietSanPhamDtoRequest {
    private String id;
    private String sanPham;
    private Float size;
    @Min(value = 0, message = "số lượng phải lớn hơn hoặc bằng 0")
    @Max(value = 1000000,message = "Số lượng phải bé hơn hoặc bằng 1000000")
    @NotNull(message = "Không để trống số lượng")
    private Long soLuong;

    public ChiTietSanPhamModel mapToModel(){
        ChiTietSanPhamModel model = new ChiTietSanPhamModel();
        model.setId(id);

        SanPhamModel sanPhamModel = new SanPhamModel();
        sanPhamModel.setMa(sanPham);
        model.setSanPham(sanPhamModel);

        SizeModel sizeModel = new SizeModel();
        sizeModel.setMa(size);
        model.setSize(sizeModel);

        model.setSoLuong(soLuong);
        return model;
    }
}
