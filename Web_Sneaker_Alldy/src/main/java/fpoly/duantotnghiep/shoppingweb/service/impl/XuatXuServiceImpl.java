package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.XuatXuResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.XuatXuRequest;
import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IXuatXuRepository;
<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/XuatXuSeviceImpl.java
import fpoly.duantotnghiep.shoppingweb.service.security.IXuatXuSevice;
=======
import fpoly.duantotnghiep.shoppingweb.service.IXuatXuService;
>>>>>>> 1a545ceac75526f8d35bf8ab7db4358db035cfc5:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/XuatXuServiceImpl.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/XuatXuSeviceImpl.java
public class XuatXuSeviceImpl implements IXuatXuSevice {
=======
@Service
public class XuatXuServiceImpl implements IXuatXuService {
>>>>>>> 1a545ceac75526f8d35bf8ab7db4358db035cfc5:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/XuatXuServiceImpl.java
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
