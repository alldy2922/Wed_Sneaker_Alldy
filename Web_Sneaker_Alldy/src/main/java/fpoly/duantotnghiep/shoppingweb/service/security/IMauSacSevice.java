package fpoly.duantotnghiep.shoppingweb.service.security;

import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMauSacSevice extends JpaRepository<MauSacModel,String> {
}
