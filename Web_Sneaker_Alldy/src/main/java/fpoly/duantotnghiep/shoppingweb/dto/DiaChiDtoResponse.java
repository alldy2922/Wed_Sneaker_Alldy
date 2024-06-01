package fpoly.duantotnghiep.shoppingweb.dto;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaChiDtoResponse {
    private Long id;
    private Integer thanhPhoCode;
    private Integer quanHuyenCode;
    private String xaPhuongCode;
    private String thanhPhoName;
    private String quanHuyenName;
    private String xaPhuongName;
    private String diaChiChiTiet;
    private Boolean macDinh;

    public DiaChiDtoResponse(DiaChiModel model) {
        this.id = model.getId();
        this.thanhPhoCode= model.getThanhPhoCode();
        this.xaPhuongCode = model.getXaPhuongCode();
        this.thanhPhoName = model.getThanhPhoName();
        this.quanHuyenName = model.getQuanHuyenName();
        this.quanHuyenCode = model.getQuanHuyenCode();
        this.xaPhuongName = model.getXaPhuongName();
        this.diaChiChiTiet = model.getDiaChiChiTiet();
        this.macDinh = model.getMacDinh();
    }
}
