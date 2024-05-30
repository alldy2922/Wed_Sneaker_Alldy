package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.ThongBaoModel;
import fpoly.duantotnghiep.shoppingweb.repository.IThongBaoRepository;
import fpoly.duantotnghiep.shoppingweb.service.IThongBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThongBaoServiceImpl implements IThongBaoService {
    @Autowired
    private IThongBaoRepository thongBaoRepository;

    @Override
    public ThongBaoModel save(ThongBaoModel thongBaoModel) {
        return thongBaoRepository.save(thongBaoModel);
    }
}
