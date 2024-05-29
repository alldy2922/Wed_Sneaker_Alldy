package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.VourcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class VoucherServiceImpl implements VourcherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<VoucherReponse> findAll() {
        return voucherRepository.findAll().stream().map(x -> new VoucherReponse(x)).collect(Collectors.toList());
    }
}
