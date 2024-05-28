package fpoly.duantotnghiep.shoppingweb.dto;

import fpoly.duantotnghiep.shoppingweb.model.VaiTroModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaiTroDtoResponse {
    private String ma;
    private String ten;

    public VaiTroDtoResponse(VaiTroModel model){
        this.ma = model.getMa();
        this.ten = model.getTen();
    }
}
