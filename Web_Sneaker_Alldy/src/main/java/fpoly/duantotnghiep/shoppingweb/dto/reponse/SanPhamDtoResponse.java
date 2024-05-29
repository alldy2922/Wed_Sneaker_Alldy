package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDtoResponse {
    private String ma;
    private String ten;
    private String mauSac;
    private String dongSanPham;
    private String thuongHieu;
    private String xuatXu;
    private String kieuDang;
    private String chatLieu;
    private BigDecimal giaNiemYet;
    private BigDecimal giaBan;
    private String moTa;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Boolean hienThi;
    private List<String> anh;
    private Long soLuong;

    private Float mucGiam;
    private String loaiGiam;

    public SanPhamDtoResponse(SanPhamModel model){
        ma = model.getMa();
        ten = model.getTen();
        mauSac = model.getMauSac() == null ? "" : model.getMauSac().getTen();
        dongSanPham = model.getDongSanPham()== null ? "" : model.getDongSanPham().getTen();
        thuongHieu = model.getDongSanPham()== null ? "" : model.getDongSanPham().getThuongHieu().getTen();
        xuatXu = model.getXuatXu() == null ? "" : model.getXuatXu().getTen();
        kieuDang = model.getKieuDang()== null ? "" : model.getKieuDang().getTen();
        chatLieu = model.getChatLieu()== null ? "" : model.getChatLieu().getTen();
        giaNiemYet = model.getDonGiaSauGiam();
        giaBan = model.getGiaBan();
        moTa = model.getMoTa();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
        hienThi = model.getHienThi();
        if(model.getImages() != null)
        anh = model.getImages().stream()
//                .sorted(Comparator.comparing(AnhModel::getId))
                .map(img -> img.getTen())
                .collect(Collectors.toList());

        soLuong = model.getSoLuongSanPham();

        if(model.getMucGiam() != null) mucGiam = model.getMucGiam();
        if(model.getLoaiGiam() != null)loaiGiam = model.getLoaiGiam();
    }

}
