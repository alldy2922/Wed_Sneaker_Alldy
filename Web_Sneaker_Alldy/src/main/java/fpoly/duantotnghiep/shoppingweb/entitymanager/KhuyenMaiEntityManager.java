package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.filter.KhuyenMaiDTOFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.repository.KhuyenMaiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KhuyenMaiEntityManager {
    @Autowired
    EntityManager entityManager;
    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    public Page<KhuyenMaiResponse> filterKhuyenMaiEntity(KhuyenMaiDTOFilter khuyenMaiDTOFilter, Integer pageNumber, Integer limit) {
        StringBuilder jpql = new StringBuilder("select k FROM KhuyenMaiModel k WHERE k.mucGiam > 0");
        if (khuyenMaiDTOFilter.getMaTen() != null)
            jpql.append(" And (k.ma like '%" + khuyenMaiDTOFilter.getMaTen() + "')");
        if (khuyenMaiDTOFilter.getLoaiMucGiam() != null) {
            if (khuyenMaiDTOFilter.getLoaiMucGiam() == 0)
                jpql.append(" And k.loai = 'PHAN TRAM'");
            if (khuyenMaiDTOFilter.getLoaiMucGiam() == 1)
                jpql.append(" And k.loai = 'TIEN'");
        }

        if (khuyenMaiDTOFilter.getMucGiam() != null && khuyenMaiDTOFilter.getMucGiamMax() == null)
            jpql.append(" And k.mucGiam = " + khuyenMaiDTOFilter.getMucGiam());
        if (khuyenMaiDTOFilter.getMucGiam() != null)
            jpql.append(" And k.mucGiam >= " + khuyenMaiDTOFilter.getMucGiam());
        if (khuyenMaiDTOFilter.getMucGiamMax() != null)
            jpql.append(" And k.mucGiam <= " + khuyenMaiDTOFilter.getMucGiam());
        if (khuyenMaiDTOFilter.getTrangThai() != null)
            jpql.append(" And k.trangThai = " + khuyenMaiDTOFilter.getTrangThai());

        if (khuyenMaiDTOFilter.getNgayBatDau() != null && khuyenMaiDTOFilter.getNgayBatDau() != null)
            jpql.append(" And k.ngayBatDau >=" + khuyenMaiDTOFilter.getNgayBatDau());
        if (khuyenMaiDTOFilter.getNgayBatDau() != null && khuyenMaiDTOFilter.getNgayBatDau() != null)
            jpql.append(" And k.ngayKetThuc <=" + khuyenMaiDTOFilter.getNgayKetThuc());
        if (khuyenMaiDTOFilter.getNgayKetThuc() != null && khuyenMaiDTOFilter.getNgayBatDau() == null)
            jpql.append(" And k.ngayKetThuc =" + khuyenMaiDTOFilter.getNgayKetThuc());
        if (khuyenMaiDTOFilter.getNgayKetThuc() == null && khuyenMaiDTOFilter.getNgayBatDau() != null)
            jpql.append(" And k.ngayBatDau =" + khuyenMaiDTOFilter.getNgayBatDau());

        if (khuyenMaiDTOFilter.getSort() != null) {
            if (khuyenMaiDTOFilter.getSort() == 0) jpql.append("ORDER BY k.mucGiam ASC");
            if (khuyenMaiDTOFilter.getSort() == 1) jpql.append("ORDER BY k.mucGiam DESC");
            if (khuyenMaiDTOFilter.getSort() == 2) jpql.append("ORDER BY k.ngayBatDau DESC");
            if (khuyenMaiDTOFilter.getSort() == 3) jpql.append("ORDER BY k.ngayKetThuc ASC");
            else if (khuyenMaiDTOFilter.getSort() == 4) jpql.append("ORDER BY k.ten ASC");
            else if (khuyenMaiDTOFilter.getSort() == 5) jpql.append("ORDER BY k.ten DESC");
        }
        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<KhuyenMaiModel> listContent = query.getResultList();
        Pageable pageable = PageRequest.of(pageNumber, limit);
        Page<KhuyenMaiModel> pageKhuyenMai = khuyenMaiRepository.findAll(pageable);

        return new PageImpl<>((listContent.stream().skip(pageable.getOffset()).limit(limit).map(m -> new KhuyenMaiResponse(m)).collect(Collectors.toList())), pageable, pageKhuyenMai.getTotalElements());
    }
}
