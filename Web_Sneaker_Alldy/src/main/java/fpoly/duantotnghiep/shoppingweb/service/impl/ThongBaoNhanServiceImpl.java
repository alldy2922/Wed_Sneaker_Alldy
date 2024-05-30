package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.ThongBaoNhanModel;
import fpoly.duantotnghiep.shoppingweb.repository.IThongBaoNhanRepository;
import fpoly.duantotnghiep.shoppingweb.service.IThongBaoNhanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThongBaoNhanServiceImpl implements IThongBaoNhanService {
    @Autowired
    private IThongBaoNhanRepository thongBaoNhanRepository;

    @Override
    public List<ThongBaoNhanModel> saveAll(List<ThongBaoNhanModel> thongBaoNhanModel) {
        return thongBaoNhanRepository.saveAll(thongBaoNhanModel);
    }

    @Override
    public ThongBaoNhanModel save(ThongBaoNhanModel thongBaoNhanModel) {
        return thongBaoNhanRepository.save(thongBaoNhanModel);
    }

    @Override
    public List<ThongBaoNhanModel> getAllByNguoiNhanId(String idNguoiNhan) {
        return thongBaoNhanRepository.getAllByNguoiNhanIdOrderByThongBaoGuiDesc(idNguoiNhan);
    }
}
