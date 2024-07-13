package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LichSuThaoTacRepository extends JpaRepository<LichSuThaoTacModel,String> {
    List<LichSuThaoTacModel> findAllByOrderByTimeChangeDesc();
}
