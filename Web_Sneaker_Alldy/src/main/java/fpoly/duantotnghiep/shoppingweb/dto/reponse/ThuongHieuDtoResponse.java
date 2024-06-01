package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThuongHieuDtoResponse {
    private String id;
    private String ten;
    private List<DongSanPhamResponese> dongSanPhamResponeseList;
    private Date ngayTao;
    private Date ngayCapNhat;

    public ThuongHieuDtoResponse(ThuongHieuModel model){
        id = model.getId();
        ten = model.getTen();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
        dongSanPhamResponeseList = model.getDongSanPhams().stream().map(d -> new DongSanPhamResponese(d.getId(),d.getTen())).collect(Collectors.toList());

    }
}
