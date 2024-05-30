package fpoly.duantotnghiep.shoppingweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String token;

    private Date thoigian;

    public Boolean checkTimeAfter30Seconds(){
        Date dateNow = new Date();
        System.out.println(Duration.between(dateNow.toInstant(), thoigian.toInstant()).getSeconds());
        if(Duration.between(dateNow.toInstant(), thoigian.toInstant()).getSeconds() < -30){
            return true;
        }
        return false;
    }
}
