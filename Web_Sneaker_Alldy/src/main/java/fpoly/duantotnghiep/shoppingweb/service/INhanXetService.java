package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanXetDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import org.springframework.data.domain.Page;

public interface INhanXetService {


    Page<NhanXetDtoResponse> getNhanXetBySanPhamAndPheDuyet(Integer page, Integer limit, String maSP, Boolean pheDuyet);

    Page<NhanXetDtoResponse> getNhanXetBySanPham(Integer page, Integer limit, String maSP);

    Page<NhanXetDtoResponse> getNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate, Boolean pheDuyet);

    Page<NhanXetDtoResponse> getAllNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate);

    NhanXetDtoResponse add(NhanXetDtoRequest nhanXetDtoRequest);


    void update(NhanXetDtoRequest nhanXetDtoRequest);

    Float getAvgRatingBySanPhamAndPheDuyet(String maSP, Boolean pheDuyet);

    Float getAvgRatingBySanPham(String maSP);

    Integer pheDuyetNhanXet(Boolean pheDuyet, String id);

    Boolean existsById(String id);

    NhanXetDtoResponse getByid(String id);
}
