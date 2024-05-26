package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.DiaChiDtoResponse;
import fpoly.duantotnghiep.shoppingweb.repository.DiaChiRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaChiServeImpl implements IDiaChiServe {
    @Autowired
    DiaChiRepository DiaChiRepo;

    @Override
    public List<DiaChiDtoResponse> findAll() {
        return DiaChiRepo.findAll().stream().map(x->new DiaChiDtoResponse(x)).collect(Collectors.toList());
    }
}
