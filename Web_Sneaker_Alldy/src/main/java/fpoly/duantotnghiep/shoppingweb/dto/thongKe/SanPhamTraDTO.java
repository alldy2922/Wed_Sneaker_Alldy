package fpoly.duantotnghiep.shoppingweb.dto.thongKe;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamTraDTO {
    private ChiTietSanPhamDtoResponse chiTietSanPham;
    private Integer soLuong;

}
