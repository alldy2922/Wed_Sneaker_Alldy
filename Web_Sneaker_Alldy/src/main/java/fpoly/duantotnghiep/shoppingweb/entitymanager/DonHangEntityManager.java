package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangTraDTOReponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonHangEntityManager {
    @Autowired
    private EntityManager entityManager;

    public Page<DonHangDtoResponse> getDonHangByTrangThai(Integer trangThai,Integer page, Integer limit, String sdt,Integer loai){
        Pageable pageable = PageRequest.of(page,limit);
        StringBuilder jpql = new StringBuilder("SELECT d FROM DonHangModel d WHERE d.trangThai ="+trangThai+" AND d.loai = "+loai);

        if(sdt != null){
            jpql.append(" And (d.soDienThoai  like '%"+sdt+"%' OR d.ma like '%"+sdt+"%')");
        }

        if(loai==0){
            if(trangThai == 0){
                jpql.append(" ORDER BY d.ngayHuy DESC ");
            }else if(trangThai == 1){
                jpql.append(" ORDER BY d.ngayXacNhan DESC ");
            }else if(trangThai == 3){
                jpql.append(" ORDER BY d.ngayGiaoHang DESC ");
            }else if(trangThai == 4){
                jpql.append(" ORDER BY d.ngayHoanThanh DESC ");
            }else{
                jpql.append(" ORDER BY d.ngayDatHang DESC ");
            }
        }else {
            jpql.append(" ORDER BY d.ngayDatHang DESC ");
        }
        List<DonHangModel> resultModel = entityManager.createQuery(jpql.toString()).getResultList();
        return new PageImpl(resultModel.stream().skip(pageable.getOffset()).limit(limit).map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                pageable,resultModel.size());
    }
    public Page<DonHangDtoResponse> getDonHangByTrangThaiTra(Integer trangThai, Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page,limit);
        StringBuilder jpql = new StringBuilder("SELECT d FROM DonHangModel d join DonHangTraModel t on d.ma = t.donHang.ma WHERE t.trangThai  ="+trangThai);

        if(trangThai == 1){
            jpql.append(" ORDER BY t.ngayXacNhan DESC ");
        }else if(trangThai == 2){
            jpql.append(" ORDER BY t.ngayKiemTra DESC ");
        }else if(trangThai == 3){
            jpql.append(" ORDER BY t.ngayHoanThanh DESC ");
        }else if(trangThai == 0) {
            jpql.append(" ORDER BY d.ngayHuyTraHang DESC ");
        }
//        else if(trangThai == 9){
//                jpql.append(" ORDER BY d.ngayHuyTraHang DESC ");
//    }
        List<DonHangModel> resultModel = entityManager.createQuery(jpql.toString()).getResultList();

        for (DonHangModel donHang : resultModel) {
            System.out.println(donHang);
        }

        return new PageImpl(resultModel.stream().skip(pageable.getOffset()).limit(limit).map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                pageable,resultModel.size());
    }

    public Page<DonHangDtoResponse> getDonHangByTrangThaiDoi(Integer trangThai, Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page,limit);
        StringBuilder jpql = new StringBuilder("SELECT d FROM DonHangModel d join DonHangDoiModel t on d.ma = t.donHang.ma WHERE t.trangThai  ="+trangThai);

        if(trangThai == 1){
            jpql.append(" ORDER BY t.ngayXacNhan DESC ");
        }else if(trangThai == 2){
            jpql.append(" ORDER BY t.ngayKiemTra DESC ");
        }else if(trangThai == 3) {
            jpql.append(" ORDER BY t.ngayGiao DESC ");
        }else if(trangThai == 4){
                jpql.append(" ORDER BY t.ngayHoanThanh DESC ");
        }else if(trangThai == 0) {
            jpql.append(" ORDER BY t.ngayHuy DESC ");
        }
//        else if(trangThai == 9){
//                jpql.append(" ORDER BY d.ngayHuyTraHang DESC ");
//    }
        List<DonHangModel> resultModel = entityManager.createQuery(jpql.toString()).getResultList();

        for (DonHangModel donHang : resultModel) {
            System.out.println(donHang);
        }

        return new PageImpl(resultModel.stream().skip(pageable.getOffset()).limit(limit).map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                pageable,resultModel.size());
    }
    public List<DonHangDtoResponse> getAllByTrangThaiTra(Integer trangThai){
        StringBuilder jpql = new StringBuilder("SELECT d FROM DonHangModel d join DonHangTraModel t on d.ma = t.donHang.ma WHERE t.trangThai  ="+trangThai);




        if(trangThai == 1){
            jpql.append(" ORDER BY t.ngayXacNhan DESC ");
        }else if(trangThai == 2){
            jpql.append(" ORDER BY t.ngayKiemTra DESC ");
        }else if(trangThai == 3){
            jpql.append(" ORDER BY t.ngayHoanThanh DESC ");
   }
//        else if(trangThai == 0){
//            jpql.append(" ORDER BY d.ngayHuyTraHang DESC ");
//        }


        List<DonHangModel> resultModel = entityManager.createQuery(jpql.toString()).getResultList();
        List<DonHangDtoResponse> donHangDtoResponses = resultModel.stream().map(model -> new DonHangDtoResponse(model)).collect(Collectors.toList());
        return donHangDtoResponses;

    }
}
