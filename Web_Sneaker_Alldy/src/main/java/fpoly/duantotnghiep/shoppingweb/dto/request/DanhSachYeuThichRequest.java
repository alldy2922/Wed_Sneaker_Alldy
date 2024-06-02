package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DanhSachYeuThichRequest {
    private String id;
    private String nguoiSoHuu;
    private String sanPham;

    public DanhSachYeuThichModel maptomodel(){
        DanhSachYeuThichModel ds = new DanhSachYeuThichModel();
        ds.setId(id);
        if(nguoiSoHuu != null && !nguoiSoHuu.isBlank()) ds.setNguoiSoHuu(new KhachHangModel(nguoiSoHuu));
         ds.setSanPham(new SanPhamModel(sanPham));
        return ds;
    };


}
