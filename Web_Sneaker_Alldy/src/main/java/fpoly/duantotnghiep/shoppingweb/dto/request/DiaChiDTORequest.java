package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaChiDTORequest {

    private Long id;
    private String taiKhoan;
    @NotNull(message = "Không để trống code Thành Phố")
    private Integer thanhPhoCode;
    @NotNull(message = "Không để trống code Quận Huyện")
    private Integer quanHuyenCode;
    @NotBlank(message = "Không để trống code Xã Phường")
    private String xaPhuongCode;
    @NotBlank(message = "Không để trống tên Thành Phố")
    private String thanhPhoName;
    @NotBlank(message = "Không để trống tên Quận Huyện")
    private String quanHuyenName;
    @NotBlank(message = "Không để trống tên Phường Xã")
    private String xaPhuongName;
    @NotBlank(message = "Không để trống địa chỉ chi tiết")
    private String diaChiChiTiet;

    public DiaChiModel mapToModel() {
        DiaChiModel diaChiModel = new DiaChiModel();
        KhachHangModel model = new KhachHangModel();
        diaChiModel.setId(id);
        model.setUsername(taiKhoan);
        diaChiModel.setTaiKhoan(model);
        diaChiModel.setThanhPhoCode(thanhPhoCode);
        diaChiModel.setQuanHuyenCode(quanHuyenCode);
        diaChiModel.setXaPhuongCode(xaPhuongCode);
        diaChiModel.setThanhPhoName(thanhPhoName);
        diaChiModel.setQuanHuyenName(quanHuyenName);
        diaChiModel.setXaPhuongName(xaPhuongName);
        diaChiModel.setDiaChiChiTiet(diaChiChiTiet);
        return diaChiModel;
    }
}
