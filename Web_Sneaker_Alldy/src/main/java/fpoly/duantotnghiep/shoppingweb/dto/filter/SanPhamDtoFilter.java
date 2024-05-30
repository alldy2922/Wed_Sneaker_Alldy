package fpoly.duantotnghiep.shoppingweb.dto.filter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

