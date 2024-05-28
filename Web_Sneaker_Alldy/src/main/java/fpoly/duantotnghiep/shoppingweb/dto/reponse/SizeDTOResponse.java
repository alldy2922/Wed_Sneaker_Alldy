package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SizeDTOResponse {
    private Float ma;
    private Float chieuDai;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayCapNhat;

    public SizeDTOResponse(SizeModel model) {
        ma = model.getMa();
        chieuDai = model.getChieuDai();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
    }
}
