package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LichSuThaoTacDTOResponse {
    String id;
    String action;
    String details;
    String username;
    Date timeChange;

    public LichSuThaoTacDTOResponse(LichSuThaoTacModel model){
        id = model.getId();
        action = model.getAction();
        details = model.getDetails();
        username = model.getUsername();
        timeChange = model.getTimeChange();
    }

}
