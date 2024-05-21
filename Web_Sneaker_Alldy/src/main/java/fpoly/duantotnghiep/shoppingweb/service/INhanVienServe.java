package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.NhanVienDtoResponse;
import org.springframework.data.domain.Page;


import java.util.List;

public interface INhanVienServe {
     Page<NhanVienDtoResponse> getAll(Integer page);

}
