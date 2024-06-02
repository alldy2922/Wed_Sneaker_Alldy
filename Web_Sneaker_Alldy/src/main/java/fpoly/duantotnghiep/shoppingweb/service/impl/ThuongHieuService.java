package fpoly.duantotnghiep.shoppingweb.service.impl;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.ThuongHieuDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KieuDangModel;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IThuongHieuRepository;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThuongHieuService implements IThuongHieuService {
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
