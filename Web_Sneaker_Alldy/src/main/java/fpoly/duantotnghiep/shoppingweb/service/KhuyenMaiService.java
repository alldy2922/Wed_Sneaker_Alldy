package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.filter.KhuyenMaiDTOFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KhuyenMaiService {
    public Page<KhuyenMaiResponse> findAll(int pageNumber, int pageSize);

    public KhuyenMaiResponse findById(String id);

    KhuyenMaiModel findById1(String id);

    Page<KhuyenMaiResponse> locKM(KhuyenMaiDTOFilter khuyenMaiDTOFilter, Integer pageNumber, Integer limit);

    public void save(KhuyenMaiRequest khuyenMai);

    void capNhatTrangThai(KhuyenMaiModel km);

    public void delete(String id);

    public void updateGiamGiaWithNgayBD();

    public void updateGiamGiaWithNgayKT();
}
