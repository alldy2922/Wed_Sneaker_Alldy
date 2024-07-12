package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.LichSuThaoTacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.LichSuThaoTachRequest;

import java.util.List;

public interface ILichSuThaoTacService {
     List<LichSuThaoTacDTOResponse> getRecentActivities() ;

     void addActivity(String username, String action);

}
