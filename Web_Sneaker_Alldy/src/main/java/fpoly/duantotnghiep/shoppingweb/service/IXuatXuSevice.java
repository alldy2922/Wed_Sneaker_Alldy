package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface IXuatXuSevice extends JpaRepository<XuatXuModel,String> {

}
