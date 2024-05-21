package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl implements INhanVienServe {
    @Autowired
    INhanVienRepository nhanVienRepository;

/*
Pageable pageable = PageRequest.of(page, 5);
        Page<KhachHangEntity> pageKhachHangEntity = khachHangRepository.findAll(pageable);
        return new PageImpl<>(pageKhachHangEntity.getContent().stream().map(n -> new KhachHangDtoReponse()).collect(Collectors.toList()),
                pageable, pageKhachHangEntity.getTotalPages());
 */
    @Override
    public Page<NhanVienDtoResponse> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page,5);
        Page<NhanVienModel> nhanVienModelPage = nhanVienRepository.findAll(pageable);
        return new PageImpl<>(nhanVienModelPage.getContent().stream().map(n-> new NhanVienDtoResponse()).collect(Collectors.toList()),
                pageable,nhanVienModelPage.getTotalPages());
    }
}
