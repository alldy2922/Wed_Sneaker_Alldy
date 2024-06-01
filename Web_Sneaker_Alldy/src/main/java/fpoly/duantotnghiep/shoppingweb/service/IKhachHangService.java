package fpoly.duantotnghiep.shoppingweb.service;

<<<<<<< HEAD
=======
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;

>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;

import java.util.List;

public interface IKhachHangService {
<<<<<<< HEAD
    List<KhachHangModel> getAll();
=======

    Page<KhachHangDtoResponse> getAll(Integer page, Integer limit);

    Page<KhachHangDtoResponse> search(String keyWord, Integer page, Integer limit);

    List<KhachHangDtoResponse> getAllFromVoucher();


    KhachHangDtoResponse findById(String username);

//    List<DiaChiModel> diaChiByTaiKhoan(String taiKhoan);

    Boolean exsistsByUsername(String username);

    KhachHangDtoResponse add(KhachHangDTORequest khachHang) throws MessagingException;

    KhachHangDtoResponse update(KhachHangDTORequest khachHang);

    KhachHangDtoResponse update(KhachHangDTORequest khachHang, MultipartFile img) throws IOException;

    void deleteByUsername(String username);

    List<KhachHangDtoResponse> khachHangVoucher(int dieuKien);

    List<KhachHangModel> findByUserNameIn(List<String> maKhachHang);
//    List<DiaChiModel> diaChiByTaiKhoan(String taiKhoan);
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
}
