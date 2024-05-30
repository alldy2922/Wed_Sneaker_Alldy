package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.XuatXuResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.XuatXuRequest;
import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IXuatXuService  {
    List<XuatXuResponse> findAll();
    XuatXuResponse save(XuatXuRequest request);
    XuatXuResponse findById(String s);
    boolean existsById(String s);
    void deleteById(String s);
}
