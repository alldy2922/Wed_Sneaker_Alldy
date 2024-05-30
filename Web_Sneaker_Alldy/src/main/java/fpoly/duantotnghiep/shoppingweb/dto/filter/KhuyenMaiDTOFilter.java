package fpoly.duantotnghiep.shoppingweb.dto.filter;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class KhuyenMaiDTOFilter {
    private String maTen;
    private Integer loaiMucGiam;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayBatDau;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayKetThuc;
    private BigDecimal mucGiam;
    private BigDecimal mucGiamMax;
    private Integer trangThai;
    private Integer sort;
}
