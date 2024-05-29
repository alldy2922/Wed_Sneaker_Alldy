package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import fpoly.duantotnghiep.shoppingweb.repository.IMauSacRepository;
import fpoly.duantotnghiep.shoppingweb.service.IMauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MauSacServiceImpl implements IMauSacService {
    @Autowired
    private IMauSacRepository mauSacRepository;


    public List<MauSacDTOResponse> findAll() {
        return mauSacRepository.findAll().stream()
                .map(m -> new MauSacDTOResponse(m))
                .collect(Collectors.toList());
    }

    public MauSacDTOResponse save( MauSacDTORequest mauSacDTORequest) {

        MauSacModel model = mauSacRepository.save(mauSacDTORequest.mapToModel());
        return new MauSacDTOResponse(model);
    }

    public MauSacDTOResponse findById(String s) {
        MauSacModel model = mauSacRepository.findById(s).get();
        return new MauSacDTOResponse(model);
    }

    public boolean existsById(String s) {
        return mauSacRepository.existsById(s);
    }

    public void deleteById(String s) {
        mauSacRepository.deleteById(s);
    }
}
