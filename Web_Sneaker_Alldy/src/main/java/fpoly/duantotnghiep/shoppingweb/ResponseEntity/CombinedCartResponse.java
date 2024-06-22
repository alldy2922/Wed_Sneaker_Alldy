package fpoly.duantotnghiep.shoppingweb.ResponseEntity;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CombinedCartResponse {
    private List<GioHangDtoReponse> sessionCartContents;
    private List<GioHangDtoReponse> databaseCartContents;
}
