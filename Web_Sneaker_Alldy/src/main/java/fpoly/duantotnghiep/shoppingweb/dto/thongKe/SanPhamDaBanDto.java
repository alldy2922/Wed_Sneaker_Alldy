package fpoly.duantotnghiep.shoppingweb.dto.thongKe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDaBanDto {
    //Entity sp đã bán
    private SanPhamBanChayDto sanPham;
    private List<ChiTietSanPhamThongKeDto> chiTietSanPham;
}
