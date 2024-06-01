package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/KieuDangSeviceImpl.java
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDTORequest;
=======
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/KieuDangService.java
import fpoly.duantotnghiep.shoppingweb.model.KieuDangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKieuDangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KieuDangSeviceImpl implements IKieuDangSevice {
    @Autowired
    private IKieuDangRepository iKieuDangRepository;


    @Override
    public Page<KieuDangDTOResponse> findAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page,limit);
        Page<KieuDangModel> pageModel = iKieuDangRepository.findAll(pageable);
        return new PageImpl<>(pageModel.getContent().stream().map(n -> new KieuDangDTOResponse(n)).collect(Collectors.toList()),
                pageable,pageModel.getTotalElements());
    }
    @Override
    public Page<KieuDangDTOResponse> search(String keyWord, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page,limit);
        Page<KieuDangModel> pageModel = iKieuDangRepository.search(keyWord,pageable);
        return new PageImpl<>(pageModel.getContent().stream().map(n -> new KieuDangDTOResponse(n)).collect(Collectors.toList()),
                pageable,pageModel.getTotalElements());
    }

    @Override
    public List<KieuDangDTOResponse> getAll() {
        return iKieuDangRepository.findAll().stream()
                .map(m-> new KieuDangDTOResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public KieuDangDTOResponse save(KieuDangDTORequest kieuDangDtoRequest) {
        KieuDangModel model = iKieuDangRepository.save(kieuDangDtoRequest.mapToModel());
        return new KieuDangDTOResponse(model);
    }


    public KieuDangDTOResponse findById(String s) {
        return new KieuDangDTOResponse(iKieuDangRepository.findById(s).get());
    }


    public boolean existsById(String s) {
        return iKieuDangRepository.existsById(s);
    }


    public void deleteById(String s) {
        iKieuDangRepository.deleteById(s);
    }

    @Override
    public void deleteByIds(List<String> s) {
        for (String id : s){
            iKieuDangRepository.deleteById(id);
        }
    }

    @Override
    public int update( String id, String ten) {
        int update = iKieuDangRepository.update(id, ten);
        return update;
    }
}
