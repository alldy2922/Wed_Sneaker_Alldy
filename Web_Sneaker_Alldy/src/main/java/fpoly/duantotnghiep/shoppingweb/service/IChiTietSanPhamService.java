package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;

import java.util.Date;
import java.util.List;

public interface IChiTietSanPhamService {
    List<ChiTietSanPhamDtoResponse> fillAllChiTietSP();

    ChiTietSanPhamDtoResponse finById(String id);

    List<ChiTietSanPhamDtoResponse> getAllBySanPhamMa(String maSP);



    ChiTietSanPhamDtoResponse updateSoLuong(ChiTietSanPhamDtoRequest request);

    Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size);

    ChiTietSanPhamDtoResponse save(ChiTietSanPhamDtoRequest entity);

    ChiTietSanPhamDtoResponse update(ChiTietSanPhamDtoRequest entity);

    void updateSL(String maCTSP, Long soLuong);

    void delete(String id);


    List<ChiTietSanPhamDtoResponse> saveAll(List<Float> sizes, ChiTietSanPhamDtoRequest model);

    Boolean existsById(String id);

    Boolean checkSoLuongSP(String id, Long soLuong);

    List<ChiTietSanPhamDtoResponse> getChiTietSanPhamNotInDonHang(String maDonHang);

    List<ChiTietSanPhamDtoResponse> getBySanPhamIdOrNameContais(String keyWord);

    Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate);

    void deleteBySize(float size);

    void deleteBySanPham(String maSanPham);
}
