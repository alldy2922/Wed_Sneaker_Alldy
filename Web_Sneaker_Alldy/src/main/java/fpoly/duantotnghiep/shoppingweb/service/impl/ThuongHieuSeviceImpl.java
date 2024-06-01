package fpoly.duantotnghiep.shoppingweb.service.impl;

<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ThuongHieuSeviceImpl.java
=======
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ThuongHieuDtoRequest;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ThuongHieuService.java
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ThuongHieuSeviceImpl.java
=======
import org.springframework.stereotype.Service;
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ThuongHieuService.java

import java.util.List;
import java.util.stream.Collectors;

public class ThuongHieuSeviceImpl implements IThuongHieuService{
    @Autowired
    private IThuongHieuRepository iThuongHieuRepository;
    @Override
    public List<ThuongHieuDtoResponse> findAll() {
        return iThuongHieuRepository.findAll().stream()
                .map(m-> new ThuongHieuDtoResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public ThuongHieuDtoResponse save(ThuongHieuDtoRequest thuongHieuDtoRequest) {
        ThuongHieuModel model = iThuongHieuRepository.save(thuongHieuDtoRequest.mapToModel());
        return new ThuongHieuDtoResponse(model);
    }

    @Override
    public ThuongHieuDtoResponse findById(String s) {
        ThuongHieuModel model = iThuongHieuRepository.findById(s).orElse(new ThuongHieuModel());
        return new ThuongHieuDtoResponse(model);
    }

    @Override
    public boolean existsById(String s) {
        return iThuongHieuRepository.existsById(s);
    }

    @Override
    public void deleteById(String s) {
        iThuongHieuRepository.deleteById(s);
    }

    @Override
    public void deleteByIds(List<String> s) {
        for (String id : s){
            iThuongHieuRepository.deleteById(id);
        }
    }

    @Override
    public List<ThuongHieuDtoResponse> getThuongHieuBanChay(){
//        System.out.println("1111"+iThuongHieuRepository.getAllOrderByBanChay());
        return iThuongHieuRepository.getAllOrderByBanChay().stream().limit(4)
                .map(id -> new ThuongHieuDtoResponse(iThuongHieuRepository.findById(id).orElse(new ThuongHieuModel())))
                .collect(Collectors.toList());
    }
}
