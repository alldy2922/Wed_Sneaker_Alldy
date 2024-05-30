package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.filter.VoucherDTOFiler;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDongSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoucherEntityManager {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private VoucherRepository voucherRepository;

    public Page<VoucherReponse> filterVoucherEntity(VoucherDTOFiler voucherDTOFiler, Integer pageNumber, Integer limit) {
        StringBuilder jpql = new StringBuilder("select v FROM VoucherModel v WHERE v.trangThaiXoa = 0");
        if (voucherDTOFiler.getMa() != null && voucherDTOFiler.getMa().isEmpty()) {
            jpql.append(" And (v.ma like '%" + voucherDTOFiler.getMa() + "')");
        }
        if (voucherDTOFiler.getTrangThai() != null) {
            jpql.append(" And v.trangThai =" + voucherDTOFiler.getTrangThai());
        }
        if (voucherDTOFiler.getLoaiMucGiam() != null) {
            if (voucherDTOFiler.getLoaiMucGiam() == 0)
                jpql.append(" And v.loaiMucGiam = 'PHAN TRAM'");
            if (voucherDTOFiler.getLoaiMucGiam() == 1)
                jpql.append(" And v.loaiMucGiam = 'TIEN'");
        }
        if (voucherDTOFiler.getGiaTriDonHang() != null)
            jpql.append(" And v.giaTriDonHang >= " + voucherDTOFiler.getGiaTriDonHang());
        if (voucherDTOFiler.getMucGiam() != null && voucherDTOFiler.getMucGiamMax() == null) {
            jpql.append(" And v.mucGiam >= " + voucherDTOFiler.getMucGiam());
        }
        if (voucherDTOFiler.getMucGiam() != null)
            jpql.append(" And v.mucGiam >= " + voucherDTOFiler.getMucGiam());
        if (voucherDTOFiler.getMucGiamMax() != null)
            jpql.append(" And v.mucGiam <= " + voucherDTOFiler.getMucGiamMax());
        if (voucherDTOFiler.getDoiTuongSuDung() != null)
            jpql.append(" And v.doiTuongSuDung = " + voucherDTOFiler.getDoiTuongSuDung());
        if (voucherDTOFiler.getHinhThucThanhToan() != null)
            jpql.append(" And v.hinhThucThanhToan = " + voucherDTOFiler.getHinhThucThanhToan());
//        if (voucherDTOFiler.getNgayBatDau() != null && voucherDTOFiler.getNgayKetThuc() != null) {
//            jpql.append(" AND v.ngayBatDau BETWEEN :startDate AND :endDate");
//        }
//        if (voucherDTOFiler.getNgayBatDau() != null)
//            jpql.append(" And v.ngayBatDau = TO_DATE('" + voucherDTOFiler.getNgayBatDau() + "', 'yyyy-MM-dd HH:mm')");
//        System.out.println(jpql);

//        if (voucherDTOFiler.getNgayBatDau() == null && voucherDTOFiler.getNgayKetThuc() != null)
//            jpql.append(" And v.ngayKetThuc  =" + voucherDTOFiler.getNgayKetThuc());
        if (voucherDTOFiler.getSort() != null) {
            if (voucherDTOFiler.getSort() == 3) jpql.append("ORDER BY v.mucGiam ASC");
            else if (voucherDTOFiler.getSort() == 4) jpql.append(" ORDER BY v.mucGiam DESC");
            else if (voucherDTOFiler.getSort() == 0) jpql.append(" ORDER BY v.ngayBatDau DESC");
            else if (voucherDTOFiler.getSort() == 2) jpql.append("ORDER BY v.ngayKetThuc ASC");
        }
        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<VoucherModel> listContent = query.getResultList();
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<VoucherModel> pageVoucher = voucherRepository.findAllVoucher(pageable);
        return new PageImpl<>((listContent.stream().skip(pageable.getOffset()).limit(limit).map(m -> new VoucherReponse(m)).collect(Collectors.toList())), pageable, pageVoucher.getTotalElements());
    }
}
