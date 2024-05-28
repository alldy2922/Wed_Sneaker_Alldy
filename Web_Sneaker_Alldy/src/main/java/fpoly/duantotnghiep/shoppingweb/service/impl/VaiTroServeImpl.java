package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.DiaChiDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.VaiTroDtoResponse;
import fpoly.duantotnghiep.shoppingweb.repository.VaiTroRepository;
import fpoly.duantotnghiep.shoppingweb.service.IVaiTroServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaiTroServeImpl implements IVaiTroServe {
    @Autowired
    VaiTroRepository vaiTroRepository;
    @Override
    public List<VaiTroDtoResponse> findAll() {
        return vaiTroRepository.findAll().stream().map(x->new VaiTroDtoResponse(x)).collect(Collectors.toList());
    }
}
