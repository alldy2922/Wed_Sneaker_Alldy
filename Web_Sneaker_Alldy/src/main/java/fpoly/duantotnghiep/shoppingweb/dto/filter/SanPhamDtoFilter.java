package fpoly.duantotnghiep.shoppingweb.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDtoFilter {

    private String ma;
    private String ten;
    private String mauSac;
    private String dongSanPham;
    private String kieuDang;
    private String chatLieu;
    private String xuatXu;
    private BigDecimal giaBan;
    private BigDecimal giaMax;
    private Integer sort;

}

