package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISizeRepository;
import fpoly.duantotnghiep.shoppingweb.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements ISizeService {
    @Autowired
    private ISizeRepository sizeRepository;

    @Override
    public List<SizeDTOResponse> findAll() {
        return sizeRepository.findAll().stream()
                .map(m -> new SizeDTOResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public List<SizeDTOResponse> getByChieuDai(Float chieuDai) {
        return sizeRepository.findAll().stream()
                .filter(m -> Float.compare(m.getChieuDai(),chieuDai)==0)
                .map(m -> new SizeDTOResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public SizeDTOResponse save(SizeDTORequest sizeDTORequest) {
        SizeModel model = sizeRepository.save(sizeDTORequest.mapToModel());
        return new SizeDTOResponse(model);
    }

    @Override
    public SizeDTOResponse findById(Float s) {
        SizeModel model = sizeRepository.findById(s).get();
        return new SizeDTOResponse(model);
    }

    @Override
    public boolean existsById(Float s) {
        return sizeRepository.existsById(s);
    }

    @Override
    public void deleteById(Float s) {
        System.out.println(s);
        sizeRepository.deleteById(s);
    }


}
