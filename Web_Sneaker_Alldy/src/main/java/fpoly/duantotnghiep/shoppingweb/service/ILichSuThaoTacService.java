package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.LichSuThaoTacDTOResponse;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface ILichSuThaoTacService {
    List<LichSuThaoTacDTOResponse> getRecentActivities() ;

    Page<LichSuThaoTacDTOResponse> pagination(Integer page, Integer limit);

    void addActivity(String username, String action);

    List<LichSuThaoTacDTOResponse>getAllHistoryWithDateStartAndDateEnd(Date ngayBatDau, Date NgayKetThuc);

    List<LichSuThaoTacDTOResponse>getAllHistoryByName(String ten);

    Page<LichSuThaoTacDTOResponse> filter(String ten, Date ngayBatDau, Date ngayKetThuc, int page, int size);
}
