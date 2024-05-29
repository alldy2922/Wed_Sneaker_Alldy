package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@NotNull
public class SizeDTORequest {
    @NotNull(message = "Không để trống mã")
    @Max(value = 50, message = "Mã không quá 50 ")
    @Min(value = 20,message = "Mã tối thiểu 20")
    private Float ma;
    @NotNull(message = "Không để trống chiều dài")
    @Max(value = 70, message = "Chiều dài không quá 70 ")
    @Min(value = 20,message = "Chiều dài tối thiểu 20")
    private Float chieuDai;
    private Date ngayTao;
    private Date ngayCapNhat;

    public SizeModel mapToModel() {
        SizeModel model = new SizeModel();
        model.setMa(ma);
        model.setChieuDai(chieuDai);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
    public SizeDTORequest(SizeModel model){
        ma = model.getMa();
        chieuDai = model.getChieuDai();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
    }
}
