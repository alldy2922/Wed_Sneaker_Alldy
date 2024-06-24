package fpoly.duantotnghiep.shoppingweb.dto.thongKe;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChiTietSanPhamThongKeDto {
    //
    private ChiTietSanPhamDtoResponse chiTietSanPham;
    private Long daBan;

    public ChiTietSanPhamThongKeDto(ChiTietSanPhamModel chiTietSanPham, Long daBan) {
        this.chiTietSanPham = new ChiTietSanPhamDtoResponse(chiTietSanPham);
        this.daBan = daBan;
    }
}
