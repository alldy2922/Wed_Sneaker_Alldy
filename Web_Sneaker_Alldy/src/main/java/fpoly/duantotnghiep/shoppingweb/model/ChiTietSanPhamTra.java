package fpoly.duantotnghiep.shoppingweb.model;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamTra {
    private DonHangDtoResponse donHangDtoResponse;

    private Date ngayXacNhan;
    private Date ngayKiemTra;
    private Date ngayHoanThanh;
    private Date ngayHuy;

}
