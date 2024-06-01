package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DongSanPhamResponese {
    private String id;
    private String idThuongHieu;
    private String ten;
    private String thuongHieu;
    private Date ngayTao;
    private Date ngayCapNhat;

    public DongSanPhamResponese(String id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public DongSanPhamResponese(DongSanPhamModel model){
        id = model.getId();
        ten = model.getTen();
        thuongHieu = model.getThuongHieu() == null?"": model.getThuongHieu().getTen();
        idThuongHieu = model.getThuongHieu() == null?"": model.getThuongHieu().getId();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
    }
}
