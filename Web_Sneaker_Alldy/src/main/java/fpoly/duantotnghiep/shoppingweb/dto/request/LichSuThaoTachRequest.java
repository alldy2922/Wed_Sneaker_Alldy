package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.LichSuThaoTacModel;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LichSuThaoTachRequest {
    String id;
    String action;
    String details;
    String username;
    Date timeChange;

    public LichSuThaoTacModel mapModel(){
        LichSuThaoTacModel model = new LichSuThaoTacModel();
        model.setId(this.id);
        model.setAction(this.action);
        model.setDetails(this.details);
        model.setUsername(this.username);
        model.setTimeChange(this.timeChange);
        return model;
    }


}
