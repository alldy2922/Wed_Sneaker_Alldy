package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.LichSuThaoTacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import fpoly.duantotnghiep.shoppingweb.repository.LichSuThaoTacRepository;
import fpoly.duantotnghiep.shoppingweb.service.ILichSuThaoTacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public Page<LichSuThaoTacDTOResponse> pagination(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        List<LichSuThaoTacDTOResponse> pageContent = repository.findAllByOrderByTimeChangeDesc().stream()
                .map(s -> new LichSuThaoTacDTOResponse(s))
                .collect(Collectors.toList());
        Page<LichSuThaoTacDTOResponse> pageDto = new PageImpl<>(pageContent.stream().skip(pageable.getOffset()).limit(limit).collect(Collectors.toList())
                , pageable, pageContent.size());
        return pageDto;
    }

    @Override
    public void addActivity(String username, String action) {
        LichSuThaoTacModel model = new LichSuThaoTacModel();
        model.setUsername(username);
        model.setAction(action);
        model.setTimeChange(new Date());
        repository.save(model);
    }

    @Override
    public List<LichSuThaoTacDTOResponse> getAllHistoryWithDateStartAndDateEnd(Date ngayBatDau, Date NgayKetThuc) {
        return null;
    }

    @Override
    public List<LichSuThaoTacDTOResponse> getAllHistoryByName(String ten) {
        return null;
    }

    @Override
    public Page<LichSuThaoTacDTOResponse> filter(String ten, Date ngayBatDau, Date ngayKetThuc, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timeChange"));
        List<LichSuThaoTacDTOResponse> pageContent = repository.findAllByCriteria(ten,ngayBatDau,ngayKetThuc,pageable).stream()
                .map(s -> new LichSuThaoTacDTOResponse(s))
                .collect(Collectors.toList());
        Page<LichSuThaoTacDTOResponse> pageDto = new PageImpl<>(pageContent.stream().skip(pageable.getOffset()).limit(size).collect(Collectors.toList())
                , pageable, pageContent.size());
        return pageDto;
    }

    private LichSuThaoTacDTOResponse convertToDTO(LichSuThaoTacModel entity) {
        LichSuThaoTacDTOResponse dto = new LichSuThaoTacDTOResponse();
        dto.setUsername(entity.getUsername());
        dto.setTimeChange(entity.getTimeChange());
        return dto;
    }
}
