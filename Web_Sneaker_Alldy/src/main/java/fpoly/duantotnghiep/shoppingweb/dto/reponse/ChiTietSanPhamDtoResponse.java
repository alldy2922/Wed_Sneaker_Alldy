package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamDtoResponse {
    private String id;
    private String sanPham;
    private Float size;
    private Long soLuong;
    private SanPhamDtoResponse sanPhamDTO;



    public ChiTietSanPhamDtoResponse(ChiTietSanPhamModel model) {
        id = model.getId();
        sanPham = model.getSanPham().getTen();
//        size = model.getSize() == null ? 0 : model.getSize().getMa();
        soLuong = model.getSoLuong();
        sanPhamDTO = new SanPhamDtoResponse(model.getSanPham());
    }
}
