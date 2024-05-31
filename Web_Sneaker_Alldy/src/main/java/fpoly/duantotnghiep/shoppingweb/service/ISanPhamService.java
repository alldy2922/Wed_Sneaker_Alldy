package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface ISanPhamService {
    List<SanPhamDtoResponse> findAll();

    Page<SanPhamDtoResponse> pagination(Integer page, Integer limit);

    Page<SanPhamDtoResponse> paginationInUser(Integer page, Integer limit);

    Page<SanPhamDtoResponse> paginationInUserByThuongHieu(Integer page, Integer limit, String idThuongHieu);

    SanPhamDtoResponse findByMa(String ma);

    SanPhamDtoRequest findDtoRequetsByMa(String ma);

    List<SanPhamDtoResponse> saveAll(List<SanPhamDtoRequest> sanPham);

    List<SanPhamModel> findByAllSanPhamWithKM();

    List<SanPhamModel> findAllWithKmWhereNgayBD();

    SanPhamDtoResponse save(SanPhamDtoRequest entity);

    SanPhamModel save1(SanPhamModel entity);

    SanPhamDtoResponse update(SanPhamDtoRequest entity) throws IOException;

    boolean existsById(String s);

    void deleteById(String s) throws IOException;

    Integer updateTrangThaiHIenThi(Boolean trangThai, String ma);


    Page<SanPhamDtoResponse>  filter(SanPhamDtoFilter sanPhamDtoFilter, Integer pageNumber, Integer limt);

    Page<SanPhamDtoResponse> filterInUser(SanPhamDtoFilter sanPhamDtoFilter, Integer pageNumber, Integer limt);

    Integer updateGiaBan(BigDecimal giaBan, String ma);

    List<SanPhamDtoResponse> getSanPhamTuongTu(String ma);

    List<SanPhamDtoResponse> getBanChay(Integer limit);

    List<SanPhamDtoResponse> getKhuyenMai(Integer limit);

    Page<SanPhamDtoResponse> getKhuyenMai(Integer limit, Integer page);

    List<SanPhamDtoResponse> getSanPhamMoi(Integer limit);

    Boolean existsByIdAdmin(String ma);

    Boolean existsByIdUser(String ma);

//    public void updateGiaGiam(SanPhamModel sanPhamModel);
}
