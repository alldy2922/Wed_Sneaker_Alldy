package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DanhSachYeuThichResponse {
    private String id;
    private String nguoiSoHuu;
    private String sanPham;
    private SanPhamDtoResponse sanPhamm;

    public DanhSachYeuThichResponse(DanhSachYeuThichModel ds){
        id = ds.getId();
        nguoiSoHuu = ds.getNguoiSoHuu() == null?"": ds.getNguoiSoHuu().getUsername();
        sanPham = ds.getSanPham() == null?"": ds.getSanPham().getMa();
        sanPhamm = new SanPhamDtoResponse(ds.getSanPham());
    }
}
