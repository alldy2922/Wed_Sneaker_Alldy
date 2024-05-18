package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.entity.KhachHangEntity;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    IKhachHangRepository khachHangRepository;

    @Override
    public Page<KhachHangDtoReponse> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<KhachHangEntity> pageKhachHangEntity = khachHangRepository.findAll(pageable);
        return new PageImpl<>(pageKhachHangEntity.getContent().stream().map(n -> new KhachHangDtoReponse()).collect(Collectors.toList()),
                pageable, pageKhachHangEntity.getTotalPages());
    }
}
