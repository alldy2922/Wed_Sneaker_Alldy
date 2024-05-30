package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDiaChiRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDiaChiRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class DiaChiServiceImpl implements IDiaChiService {
    @Autowired
    IDiaChiRepository repository;

    @Autowired
    IDiaChiRepository diaChiRepository;

    @Override
    public DiaChiModel findByIdModel(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void addDiaChi(DiaChiModel diaChiModel) {

        this.repository.save(diaChiModel);
    }

    @Override
    public Page<DiaChiDTOResponse> getAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<DiaChiModel> pageModel = diaChiRepository.findAll(pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(k -> new DiaChiDTOResponse(k)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());

    }

    @Override
    public DiaChiDTOResponse findById(Long id) {
        return new DiaChiDTOResponse(diaChiRepository.findById(id).get());
    }

    @Override
    public Boolean exsistsById(Long id) {
        return diaChiRepository.existsById(id);
    }

    @Override
    public DiaChiDTOResponse add(DiaChiDTORequest diaChi) {
        DiaChiModel model = diaChi.mapToModel();
        List<DiaChiModel> lst = diaChiRepository.getAllByTaiKhoan(model.getTaiKhoan());
        if (lst.size() == 0) model.setMacDinh(true);
        else model.setMacDinh(false);
        DiaChiModel diaChiModel = diaChiRepository.save(model);
        return new DiaChiDTOResponse(diaChiModel);
    }

    @Override
    public DiaChiDTOResponse update(DiaChiDTORequest diaChi) {
        Boolean macDinh = diaChiRepository.findById(diaChi.getId()).get().getMacDinh();
        DiaChiModel model = diaChi.mapToModel();
        model.setMacDinh(macDinh);
        DiaChiModel diaChiModel = diaChiRepository.save(model);
        return new DiaChiDTOResponse(diaChiModel);
    }

    @Override
    public void deleteById(Long id) {
        diaChiRepository.deleteById(id);

    }

    @Override
    public void setMacDinh(String nguoiSoHuu, Long idDiaChi) {
        KhachHangModel khachHangModel = new KhachHangModel();

        khachHangModel.setUsername(nguoiSoHuu);
        List<DiaChiModel> lstModel = diaChiRepository.getAllByTaiKhoan(khachHangModel);
        for (var ls : lstModel) {
            if (ls.getId().equals(idDiaChi)) {
                ls.setMacDinh(true);
            } else {
                ls.setMacDinh(false);
            }
        }
        ;
        diaChiRepository.saveAll(lstModel);
    }

    @Override
    public DiaChiDTOResponse getDiaChiMacDinh(String nguoiSoHuu, Boolean macDinh) {
        KhachHangModel khachHangModel = new KhachHangModel();
        khachHangModel.setUsername(nguoiSoHuu);
        DiaChiModel model = diaChiRepository.findByTaiKhoanAndMacDinh(khachHangModel, macDinh);
        if (model == null) {
            return null;
        }
        return new DiaChiDTOResponse(model);
    }

}
