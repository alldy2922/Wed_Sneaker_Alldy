package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.dto.request.DongSanPhamRequest;
import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDongSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DongSanPhamServiceImpl implements IDongSanPhamService {
    @Autowired
    IDongSanPhamRepository repo;

    public List<DongSanPhamResponese> findAll() {
        return repo.findAll().stream()
                .map(d -> new DongSanPhamResponese(d))
                .collect(Collectors.toList());
    }


    public DongSanPhamResponese save(DongSanPhamRequest dongSanPhamDtoRequest) {
        DongSanPhamModel model = repo.save(dongSanPhamDtoRequest.maptomodel());
        return new DongSanPhamResponese(model);
    }

    public DongSanPhamResponese findById(String s) {
        DongSanPhamModel model = repo.findById(s).get();
        return new DongSanPhamResponese(model);
    }

    public boolean existsById(String s) {
        return repo.existsById(s);
    }

    public void deleteById(String s) {
        repo.deleteById(s);
    }

    @Override
    public void deleteByThuongHieu(String maTHuongHieu){
        ThuongHieuModel thuongHieuModel = new ThuongHieuModel();
        thuongHieuModel.setId(maTHuongHieu);
        repo.deleteByThuongHieu(thuongHieuModel);
    }

}
