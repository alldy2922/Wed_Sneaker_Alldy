package fpoly.duantotnghiep.shoppingweb.service.impl;

<<<<<<< HEAD
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
=======
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.ImgUtil;
import jakarta.mail.MessagingException;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements IKhachHangService {
    @Autowired
    IKhachHangRepository khachHangRepository;


    @Override
    public List<KhachHangModel> getAll() {
        return khachHangRepository.findAll();
    }
<<<<<<< HEAD
=======

    @Override
    public List<KhachHangDtoResponse> getAllFromVoucher() {
        return khachHangRepository.findAll().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
    }

    @Override
    public KhachHangDtoResponse findById(String username) {
        return new KhachHangDtoResponse(khachHangRepository.findById(username).get());
    }

    @Override
    public Boolean exsistsByUsername(String username) {
        return khachHangRepository.existsById(username);
    }

    @Override
    public KhachHangDtoResponse add(KhachHangDTORequest khachHang) throws MessagingException {
        KhachHangModel khachHangModel = khachHangRepository.save(khachHang.mapToModel());
        return new KhachHangDtoResponse(khachHangModel);
    }

    @Override
    public KhachHangDtoResponse update(KhachHangDTORequest khachHang) {
        KhachHangModel khachHangDefault = khachHangRepository.findById(khachHang.getUsername()).get();
        khachHang.setPassword(khachHangDefault.getPassword());
        khachHang.setAnhDaiDien(khachHangDefault.getAnhDaiDien());
        KhachHangModel khachHangModel = khachHangRepository.save(khachHang.mapToModel());
        return new KhachHangDtoResponse(khachHangModel);
    }

    @Override
    public KhachHangDtoResponse update(KhachHangDTORequest khachHang, MultipartFile img) throws IOException {
        KhachHangModel khachHangDefault = khachHangRepository.findById(khachHang.getUsername()).get();
        khachHang.setPassword(khachHangDefault.getPassword());

        if (img == null) {
            if (khachHangDefault.getAnhDaiDien() != null) ImgUtil.deleteImg(khachHangDefault.getAnhDaiDien(), "user");
            khachHang.setAnhDaiDien(null);
        } else {
            if (!img.getOriginalFilename().equalsIgnoreCase(khachHang.getAnhDaiDien())) {//add ảnh
                byte[] bytes = img.getBytes();
                String fileName = img.getOriginalFilename();
                String name = khachHang.getUsername() + fileName.substring(fileName.lastIndexOf("."));
                Path path = Paths.get("src/main/resources/images/user/" + name);
                Files.write(path, bytes);
                khachHang.setAnhDaiDien(name);
            } else {
                khachHang.setAnhDaiDien(img.getOriginalFilename());
            }
        }

        KhachHangModel khachHangModel = khachHangRepository.save(khachHang.mapToModel());
        return new KhachHangDtoResponse(khachHangModel);
    }

    @Override
    public void deleteByUsername(String username) {
        khachHangRepository.deleteById(username);
    }

    @Override
    public List<KhachHangDtoResponse> khachHangVoucher(int dieuKien) {
        if (dieuKien == 0) {
            return khachHangRepository.findKhachMuaNhieu().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
        } else if (dieuKien == 1) {
            return khachHangRepository.findKhachMuaLanDau().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
        } else if (dieuKien == 2) {
            return khachHangRepository.findKhachMoiMua().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
        } else {
            return khachHangRepository.findAll().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
        }
    }

    @Override
    public List<KhachHangModel> findByUserNameIn(List<String> maKhachHang) {
        return khachHangRepository.findByUsernameIn(maKhachHang);
    }

//    @Override
//    public List<DiaChiModel> diaChiByTaiKhoan(String taiKhoan) {
//        return khachHangRepository.findAllDiaChiByTaiKhoan(taiKhoan);
//    }

>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
}
