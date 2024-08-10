package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangTraDTOReponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamDoi;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangTraModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
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


//    public List<ChiTietSanPhamDoi> getChiTietSanPhamDoi(Integer trangThai) {
//        // Base JPQL query
//
//        // Điều kiện sắp xếp
//
//        // Điều kiện lựa chọn các trường cần lấy
//        String selectClause = "";
//
//        if (trangThai ==1){
//            selectClause = """
//            SELECT dh AS chitiet,  tr.ngayXacNhan AS ngayXN
//            FROM DonHangModel dh
//            LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma
//            WHERE tr.trangThai = :trangThai
//            GROUP BY dh.ma,tr.ngayXacNhan
//            ORDER BY tr.ngayXacNhan
//            """;
//        } else if (trangThai == 2) {
//            selectClause = """
//            SELECT dh AS chitiet,  tr.ngayKiemTra AS ngayKT
//            FROM DonHangModel dh
//            LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma
//            WHERE tr.trangThai = :trangThai
//            GROUP BY dh.ma,tr.ngayKiemTra
//            ORDER BY tr.ngayKiemTra
//            """;
//        } else if (trangThai == 3) {
//            selectClause = """
//                SELECT dh AS chitiet, tr.ngayGiao AS ngayGiao
//                FROM DonHangModel dh
//                LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma
//                WHERE tr.trangThai = :trangThai
//                GROUP BY dh.ma, tr.ngayGiao
//                """;
//        }else if (trangThai == 4) {
//            selectClause = """
//                SELECT dh AS chitiet, tr.ngayHoanThanh AS ngayHT
//                FROM DonHangModel dh
//                LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma
//                WHERE tr.trangThai = :trangThai
//                GROUP BY dh.ma, tr.ngayHoanThanh
//                """;
//        }
//        else if (trangThai == 0) {
//            selectClause = """
//                SELECT dh AS chitiet, tr.ngayHuy AS ngayHuy
//                FROM DonHangModel dh
//                LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma
//                WHERE tr.trangThai = :trangThai
//                GROUP BY dh.ma, tr.ngayHuy
//                """;
//        }
//        // Construct the full JPQL query
//        String jpql = selectClause ;
//
//        // Create and execute the query
//        return entityManager.createQuery(jpql, Tuple.class)
//                .setParameter("trangThai", trangThai)
//                .getResultList()
//                .stream()
//                .map(tuple -> {
//                    DonHangDtoResponse chitiet = new DonHangDtoResponse((DonHangModel) tuple.get("chitiet"));
//                    Date ngayXN = trangThai == 1 ? (Date) tuple.get("ngayXN") : null;
//                    Date ngayKT = trangThai == 2 ? (Date) tuple.get("ngayKT") : null;
//                    Date ngayGiao = trangThai == 3 ? (Date) tuple.get("ngayGiao") : null;
//                    Date ngayHT = trangThai == 4 ? (Date) tuple.get("ngayHT") : null;
//                    Date ngayHuy = trangThai == 0 ? (Date) tuple.get("ngayHuy") : null;
//
//                    return new ChiTietSanPhamDoi(chitiet, ngayXN, ngayKT,ngayGiao, ngayHT,ngayHuy);
//                })
//                .collect(Collectors.toList());
//    }
public Page<ChiTietSanPhamDoi> getChiTietSanPhamDoi(Integer trangThai, Integer page, Integer limit, String sdt) {
    Pageable pageable = PageRequest.of(page, limit);
    StringBuilder selectClause = new StringBuilder();

    selectClause.append("SELECT dh AS chitiet");

    if (trangThai == 1) {
        selectClause.append(", tr.ngayXacNhan AS ngayXN ");
    } else if (trangThai == 2) {
        selectClause.append(", tr.ngayKiemTra AS ngayKT ");
    } else if (trangThai == 3) {
        selectClause.append(", tr.ngayGiao AS ngayGiao ");
    } else if (trangThai == 4) {
        selectClause.append(", tr.ngayHoanThanh AS ngayHT ");
    } else if (trangThai == 0) {
        selectClause.append(", tr.ngayHuy AS ngayHuy ");
    }

    selectClause.append("FROM DonHangModel dh ");
    selectClause.append("LEFT JOIN DonHangDoiModel tr ON tr.donHang.ma = dh.ma ");
    selectClause.append("WHERE tr.trangThai = :trangThai ");

    // Thêm điều kiện tìm kiếm theo số điện thoại hoặc mã đơn hàng
    if (sdt != null && !sdt.isEmpty()) {
        selectClause.append("AND (dh.soDienThoai LIKE :sdt OR dh.ma LIKE :sdt) ");
    }

    // Thêm điều kiện sắp xếp theo trang thái
    if (trangThai == 1) {
        selectClause.append("GROUP BY dh.ma, tr.ngayXacNhan ");
        selectClause.append("ORDER BY tr.ngayXacNhan DESC ");
    } else if (trangThai == 2) {
        selectClause.append("GROUP BY dh.ma, tr.ngayKiemTra ");
        selectClause.append("ORDER BY tr.ngayKiemTra DESC ");
    } else if (trangThai == 3) {
        selectClause.append("GROUP BY dh.ma, tr.ngayGiao ");
        selectClause.append("ORDER BY tr.ngayGiao DESC ");
    } else if (trangThai == 4) {
        selectClause.append("GROUP BY dh.ma, tr.ngayHoanThanh ");
        selectClause.append("ORDER BY tr.ngayHoanThanh DESC ");
    } else if (trangThai == 0) {
        selectClause.append("GROUP BY dh.ma, tr.ngayHuy ");
        selectClause.append("ORDER BY tr.ngayHuy DESC ");
    }

    // Tạo truy vấn JPQL và thêm tham số
    Query query = entityManager.createQuery(selectClause.toString(), Tuple.class);
    query.setParameter("trangThai", trangThai);

    if (sdt != null && !sdt.isEmpty()) {
        query.setParameter("sdt", "%" + sdt + "%");
    }

    List<Tuple> resultList = query.getResultList();

    // Áp dụng phân trang cho kết quả
    List<ChiTietSanPhamDoi> result = resultList.stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .map(tuple -> {
                DonHangDtoResponse chitiet = new DonHangDtoResponse((DonHangModel) tuple.get("chitiet"));
                Date ngayXN = trangThai == 1 ? (Date) tuple.get("ngayXN") : null;
                Date ngayKT = trangThai == 2 ? (Date) tuple.get("ngayKT") : null;
                Date ngayGiao = trangThai == 3 ? (Date) tuple.get("ngayGiao") : null;
                Date ngayHT = trangThai == 4 ? (Date) tuple.get("ngayHT") : null;
                Date ngayHuy = trangThai == 0 ? (Date) tuple.get("ngayHuy") : null;

                return new ChiTietSanPhamDoi(chitiet, ngayXN, ngayKT, ngayGiao, ngayHT, ngayHuy);
            })
            .collect(Collectors.toList());

    return new PageImpl<>(result, pageable, resultList.size());
}
}
