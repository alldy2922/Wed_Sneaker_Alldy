package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KieuDangModel;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKieuDangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KieuDangService implements IKieuDangService {
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
    public KieuDangDTOResponse save(KieuDangDtoRequest kieuDangDtoRequest) {
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
