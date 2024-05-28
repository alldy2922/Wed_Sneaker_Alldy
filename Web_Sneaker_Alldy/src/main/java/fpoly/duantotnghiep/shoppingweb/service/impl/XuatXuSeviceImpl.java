package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IXuatXuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class XuatXuSeviceImpl implements IXuatXuService{
    @Autowired
    private IXuatXuRepository repo;

    public List<XuatXuResponse> findAll() {
        return repo.findAll().stream()
                .map(x -> new XuatXuResponse(x))
                .collect(Collectors.toList());
    }

    public XuatXuResponse save(XuatXuRequest request){
        XuatXuModel model = repo.save(request.mapXuatXuToModel());
        return new XuatXuResponse(model);
    }

    public XuatXuResponse findById(String s) {
        XuatXuModel model = repo.findById(s).get();
        return new XuatXuResponse(model);
    }

    public boolean existsById(String s) {
        return repo.existsById(s);
    }

    public void deleteById(String s) {
        repo.deleteById(s);
    }
}
