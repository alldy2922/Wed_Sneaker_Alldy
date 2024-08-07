package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDoiDTOReponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangTraDTOReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IDonHangService {
    List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai);

    List<DonHangReponseUser> getAllByKhachHangAndTrangThai(String nguoiSoHuu, Integer trangThai);
    List<DonHangReponseUser> getAllByKhachHangAndTrangThaiTra(String nguoiSoHuu, Integer trangThai);
    List<DonHangTraDTOReponse> getCTSPTra(String nguoiSoHuu, String ma);
    List<DonHangTraDTOReponse> getAllByDonHangTra(String ma);
    List<DonHangReponseUser> getAllByKhachHangAndTrangThaiDoi(String nguoiSoHuu, Integer trangThai);
    List<DonHangDoiDTOReponse> getCTSPDoi(String nguoiSoHuu, String ma);
    List<DonHangDoiDTOReponse> getAllByDonHangDoi(String ma);

    Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber);
    List<DonHangTraDTOReponse> getAllDonHangTra(Integer trangThai);
    List<DonHangDoiDTOReponse> getAllDonHangDoi(Integer trangThai);

    DonHangDtoResponse checkOut(DonHangDTORequest donHangDTORequest);

    DonHangDtoResponse findByMa(String ma);

    DonHangTraDTOReponse findByMaTra(String ma);
//    DonHangDoiDTOReponse findByMaDoi(String ma);
    List<DonHangDoiDTOReponse> findByMaDoi(String ma);

    DonHangReponseUser findByMaUser(String ma);

    Boolean existsByMa(String ma);

    void updateTrangThai(String maDonHang, Integer trangThai) throws MessagingException;
    void updateTrangThaiTra(String maDonHangTra, Integer trangThai) throws MessagingException;
    void updateTrangThaiDoi(String maDonHangDoi, Integer trangThai) throws MessagingException;


//    void huyDonHang(String maDonHang, String lyDo) throws MessagingException;

//    DonHangDtoResponse updateDonHang(DonHangDTORequest request);

    void huyTraHang(List<String> maDonHang, String lyDoTraHang) throws MessagingException;
    void huyTraHangNew(List<String> maDonHang, String lyDoTraHang) throws MessagingException;

    void huyDoiHang(List<String> maDonHang, String lyDoDoiHang) throws MessagingException;

    void huyDonHang(List<String> maDonHang, String lyDo) throws MessagingException;

    void huyDonHangUser(String maDonHang, String lyDo) throws MessagingException;

    DonHangDtoResponse updateDonHang(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoThayDoi);

    @Query("""
                SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c 
                WHERE c.donHang.ngayDatHang in (?1,?2)
            """)
    Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate);

    void updateTrangThaiTraHang(String maDonHang, Integer trangThai) throws MessagingException;

    void sendEmailRefundWithHtml()throws MessagingException;

    Long getQuantityOrdersWithDate(Date firstDate, Date lastDate);

    BigDecimal getTotalPriceInOrdersWithDate(Date firstDate, Date lastDate);

    DonHangDtoResponse updateTrangThai1(String maDonHang, Integer trangThai);

    void themDonHangAdmin(DonHangDTORequest donHangDTORequest, List<ChiTietDonHangDTORequest> chiTietDonHang);

    Map<String,Long> getQuantityProductInOrderDetailWithDate(Date firstDate, Date lastDate);

    void traDonHangUser(String maDonHang, String lyDoTraHang, Boolean phuongThucNhanTien, String ghiChu) throws MessagingException;

    DonHangDtoResponse traMotPhan(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoTraHang, Boolean phuongThucNhanTien, String ghiChu);

    DonHangDtoResponse doiMotPhan(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoDoiHang);

}
