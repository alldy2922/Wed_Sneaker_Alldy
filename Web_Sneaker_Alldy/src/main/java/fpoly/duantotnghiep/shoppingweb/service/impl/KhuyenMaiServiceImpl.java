package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.filter.KhuyenMaiDTOFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.KhuyenMaiEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.KhuyenMaiRepository;
import fpoly.duantotnghiep.shoppingweb.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository repository;
    @Autowired
    KhuyenMaiEntityManager khuyenMaiEntityManager;
    @Autowired
    ISanPhamRepository sanPhamRepository;

    @Override
    public Page<KhuyenMaiResponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<KhuyenMaiModel> pageModel = repository.findAll(pageable);
        return pageModel.map(x -> new KhuyenMaiResponse(x));
    }


    @Override
    public KhuyenMaiResponse findById(String id) {
        KhuyenMaiModel getById = repository.findById(id).get();
        return new KhuyenMaiResponse(getById);
    }

    @Override
    public KhuyenMaiModel findById1(String id) {
        KhuyenMaiModel getById = repository.findById(id).get();
        return getById;
    }

    @Override
    public Page<KhuyenMaiResponse> locKM(KhuyenMaiDTOFilter khuyenMaiDTOFilter, Integer pageNumber, Integer limit) {
        return khuyenMaiEntityManager.filterKhuyenMaiEntity(khuyenMaiDTOFilter, pageNumber, limit);
    }

    @Override
    public void save(KhuyenMaiRequest khuyenMai) {
        repository.save(khuyenMai.mapToModel());
    }

    @Override
    public void capNhatTrangThai(KhuyenMaiModel km) {
        repository.save(km);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGiamGiaWithNgayBD() {
        for (var i : repository.findAllSanPhamWithKmWhereNgayBatDau()) {
            i.setTrangThai(0);
            repository.save(i);
        }
        System.out.println("Thành công");
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGiamGiaWithNgayKT() {
        for (var i : repository.findAllSanPhamWithKmWhereNgayKetThuc()) {
            i.setTrangThai(1);
            repository.save(i);
        }
    }
}
