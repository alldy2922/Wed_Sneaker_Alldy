package fpoly.duantotnghiep.shoppingweb.dto.thongKe;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamBanChayDto {
    private SanPhamDtoResponse sanPham;
    private Long soLuong;
}
