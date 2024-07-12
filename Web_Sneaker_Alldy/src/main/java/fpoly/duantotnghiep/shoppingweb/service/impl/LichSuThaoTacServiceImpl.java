package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.LichSuThaoTacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.LichSuThaoTachRequest;
import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import fpoly.duantotnghiep.shoppingweb.repository.LichSuThaoTacRepository;
import fpoly.duantotnghiep.shoppingweb.service.ILichSuThaoTacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichSuThaoTacServiceImpl implements ILichSuThaoTacService {

    @Autowired
    private LichSuThaoTacRepository repository;


    @Override
    public List<LichSuThaoTacDTOResponse> getRecentActivities() {
        return repository.findAllByOrderByTimeChangeDesc().stream()
                .map(m-> new LichSuThaoTacDTOResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public void addActivity(String username, String action) {
        LichSuThaoTacModel model = new LichSuThaoTacModel();
        model.setUsername(username);
        model.setAction(action);
        model.setTimeChange(new Date());
        repository.save(model);
    }


}
