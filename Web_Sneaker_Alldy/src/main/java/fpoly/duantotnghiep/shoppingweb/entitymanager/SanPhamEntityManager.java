package fpoly.duantotnghiep.shoppingweb.entitymanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.thongKe.SanPhamBanChayDto;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDongSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
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
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SanPhamEntityManager {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IDongSanPhamRepository dongSanPhamRepository;
    @Autowired
    private ISanPhamRepository sanPhamRepository;

    public Page<SanPhamDtoResponse> filterMultipleProperties(SanPhamDtoFilter sanPham, Integer pageNumber, Integer limit){
        StringBuilder jpql = new StringBuilder("select s FROM SanPhamModel s WHERE s.trangThai = true ");

        if(sanPham.getTen() != null){
            jpql.append(" And (s.ten like '%"+sanPham.getTen()+"%' Or s.ma like '%"+sanPham.getTen()+"%')");
//            queryBuider.append("And s.ten like '%"+sanPham.getTen()+"%'");
        }

        if(sanPham.getMauSac()!=null) {
            if(sanPham.getMauSac().equalsIgnoreCase("khac")){
                jpql.append(" And s.mauSac is null");
            }else {
                jpql.append(" And s.mauSac.ma = '" + sanPham.getMauSac() + "'");
            }
        }
        if(sanPham.getDongSanPham()!=null) {
            if(sanPham.getDongSanPham().equalsIgnoreCase("khac")){
                jpql.append(" And s.dongSanPham is null");
            }else{
                DongSanPhamModel dongSanPhamModel = dongSanPhamRepository.findById(sanPham.getDongSanPham()).orElse(null);
                if(dongSanPhamModel==null){
                    jpql.append(" And s.dongSanPham.thuongHieu.id = '" + sanPham.getDongSanPham() + "'");
                }else {
                    jpql.append(" And s.dongSanPham.id = '" + sanPham.getDongSanPham() + "'");
                }
            }
        }
        if(sanPham.getKieuDang()!=null) {
            if(sanPham.getKieuDang().equalsIgnoreCase("khac")) {
                jpql.append(" And s.kieuDang is null");
            }else{
                jpql.append(" And s.kieuDang.id = '" + sanPham.getKieuDang() + "'");
            }
        }
        if(sanPham.getXuatXu()!=null) {
            if(sanPham.getXuatXu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.xuatXu is null");
            }else{
                jpql.append(" And s.xuatXu.id = '" + sanPham.getXuatXu() + "'");
            }
        }
        if(sanPham.getChatLieu()!=null) {
            if(sanPham.getChatLieu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.chatLieu is null");
            }else {
                jpql.append(" And s.chatLieu.id = '" + sanPham.getChatLieu() + "'");
            }
        }
        if(sanPham.getGiaBan()!=null) jpql.append(" And s.giaBan >= " + sanPham.getGiaBan());
        if(sanPham.getGiaMax()!=null) jpql.append(" And s.giaBan <= " + sanPham.getGiaMax());

        if(sanPham.getSort()!=null){
            if(sanPham.getSort()==1) jpql.append( "ORDER BY s.giaBan DESC");
            else if(sanPham.getSort()==2) jpql.append(" ORDER BY s.giaBan");
            else if(sanPham.getSort()==3) jpql.append(" ORDER BY s.ten DESC");
            else if(sanPham.getSort()==4) jpql.append(" ORDER BY s.ten");
            else if(sanPham.getSort()==5) jpql.append("ORDER BY s.soLuong DESC");
            else if(sanPham.getSort()==6) jpql.append("ORDER BY s.soLuong");
            else if(sanPham.getSort()==7) jpql.append("ORDER BY s.ngayTao DESC");
            else if(sanPham.getSort()==8) jpql.append("ORDER BY s.ngayTao");
            else if(sanPham.getSort()==9) jpql.append("ORDER BY s.ngayCapNhat DESC");
            else if(sanPham.getSort()==10) jpql.append("ORDER BY s.ngayCapNhat");
        }

        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<SanPhamModel> listContent = query.getResultList();
        Pageable pageable = PageRequest.of(pageNumber,limit);

        return new PageImpl<>(listContent.stream().skip(pageable.getOffset()).limit(limit).map(m -> new SanPhamDtoResponse(m)).collect(Collectors.toList())
                ,pageable,listContent.size());
    }

    public Page<SanPhamDtoResponse> filterMultiplePropertiesInUser(SanPhamDtoFilter sanPham, Integer pageNumber, Integer limit){
        StringBuilder jpql = new StringBuilder("select s FROM SanPhamModel s WHERE s.trangThai = true and s.hienThi = true");

        if(sanPham.getTen() != null){
            jpql.append(" And (s.ten like '%"+sanPham.getTen()+"%')");
//            queryBuider.append("And s.ten like '%"+sanPham.getTen()+"%'");
        }

        if(sanPham.getMauSac()!=null) {
            if(sanPham.getMauSac().equalsIgnoreCase("khac")){
                jpql.append(" And s.mauSac is null");
            }else {
                jpql.append(" And s.mauSac.ma = '" + sanPham.getMauSac() + "'");
            }
        }
        if(sanPham.getDongSanPham()!=null) {
            if(sanPham.getDongSanPham().equalsIgnoreCase("khac")){
                jpql.append(" And s.dongSanPham is null");
            }else{
                DongSanPhamModel dongSanPhamModel = dongSanPhamRepository.findById(sanPham.getDongSanPham()).orElse(null);
                if(dongSanPhamModel==null){
                    jpql.append(" And s.dongSanPham.thuongHieu.id = '" + sanPham.getDongSanPham() + "'");
                }else {
                    jpql.append(" And s.dongSanPham.id = '" + sanPham.getDongSanPham() + "'");
                }
            }
        }
        if(sanPham.getKieuDang()!=null) {
            if(sanPham.getKieuDang().equalsIgnoreCase("khac")) {
                jpql.append(" And s.kieuDang is null");
            }else{
                jpql.append(" And s.kieuDang.id = '" + sanPham.getKieuDang() + "'");
            }
        }
        if(sanPham.getXuatXu()!=null) {
            if(sanPham.getXuatXu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.xuatXu is null");
            }else{
                jpql.append(" And s.xuatXu.id = '" + sanPham.getXuatXu() + "'");
            }
        }
        if(sanPham.getChatLieu()!=null) {
            if(sanPham.getChatLieu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.chatLieu is null");
            }else {
                jpql.append(" And s.chatLieu.id = '" + sanPham.getChatLieu() + "'");
            }
        }
        if(sanPham.getGiaBan()!=null) jpql.append(" And s.giaBan >= " + sanPham.getGiaBan());
        if(sanPham.getGiaMax()!=null) jpql.append(" And s.giaBan <= " + sanPham.getGiaMax());

        if(sanPham.getSort()!=null){
            if(sanPham.getSort()==1) jpql.append( "ORDER BY s.giaBan DESC");
            else if(sanPham.getSort()==2) jpql.append(" ORDER BY s.giaBan");
            else if(sanPham.getSort()==3) jpql.append(" ORDER BY s.ten DESC");
            else if(sanPham.getSort()==4) jpql.append(" ORDER BY s.ten");
            else if(sanPham.getSort()==5) jpql.append("ORDER BY s.soLuong DESC");
            else if(sanPham.getSort()==6) jpql.append("ORDER BY s.soLuong");
            else if(sanPham.getSort()==7) jpql.append("ORDER BY s.ngayTao DESC");
            else if(sanPham.getSort()==8) jpql.append("ORDER BY s.ngayTao");
            else if(sanPham.getSort()==9) jpql.append("ORDER BY s.ngayCapNhat DESC");
            else if(sanPham.getSort()==10) jpql.append("ORDER BY s.ngayCapNhat");
        }

        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<SanPhamModel> listContent = query.getResultList();
        listContent = listContent.stream().filter(s -> s.getHienThi()==true).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(pageNumber,limit);

        return new PageImpl<>(listContent.stream()
                .skip(pageable.getOffset()).limit(limit).map(m -> new SanPhamDtoResponse(m)).collect(Collectors.toList())
                ,pageable,listContent.size());
    }

    public List<SanPhamBanChayDto> getSanPhamBanChay(){
        return entityManager.createQuery("""
                                 SELECT s.sanPham.ma AS sanPham, SUM(cd.soLuong) AS soLuong
                                 FROM ChiTietSanPhamModel s JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                 WHERE cd.donHang.trangThai <> 0 AND  cd.donHang.trangThai <> 5 AND s.sanPham.trangThai = true
                                 GROUP BY s.sanPham.ma
                                 order by soLuong DESC 
                            """, Tuple.class)
                            .getResultList()
                            .stream()
                            .limit(5)
                            .map(r -> new SanPhamBanChayDto(
                                    new SanPhamDtoResponse(sanPhamRepository.findById(r.get("sanPham").toString()).get()),
                                    ((Number) r.get("soLuong")).longValue()
                            )).collect(Collectors.toList());
    }
    public List<SanPhamDtoResponse> getSanPhamTon(){
        return entityManager.createQuery("""
                                 SELECT s 
                                 FROM SanPhamModel s 
                                 where s.trangThai = true
                                 order by s.ngayTao , s.soLuong DESC
                            """, Tuple.class)
                .getResultList()
                .stream()
                .limit(5)
                .map(r -> new SanPhamDtoResponse((SanPhamModel) r.get(0))
                ).collect(Collectors.toList());
    }

    public List<SanPhamBanChayDto> getSanPhamDaBanWithDate(Date firstDate, Date lastDate){
        return entityManager.createQuery("""
                                 SELECT s.sanPham.ma AS sanPham, SUM(cd.soLuong) AS soLuong
                                 FROM ChiTietSanPhamModel s JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                 WHERE cd.donHang.ngayDatHang BETWEEN :firstDate And :lastDate
                                 GROUP BY s.sanPham.ma
                                 order by soLuong DESC 
                            """, Tuple.class)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getResultList()
                .stream()
                .limit(5)
                .map(r -> new SanPhamBanChayDto(
                        new SanPhamDtoResponse(sanPhamRepository.findById(r.get("sanPham").toString()).get()),
                        ((Number) r.get("soLuong")).longValue()
                )).collect(Collectors.toList());
    }

    public List<ChiTietSanPhamDtoResponse> filterAllCtsp(SanPhamDtoFilter sanPham){
        StringBuilder jpql = new StringBuilder("select s FROMChiTietSanPhamModel s WHERE s.sanPham.trangThai = true and s.sanPham.hienThi = true");

        if(sanPham.getTen() != null){
            jpql.append(" And (s.sanPham.ten like '%"+sanPham.getTen()+"%')");
//            queryBuider.append("And s.ten like '%"+sanPham.getTen()+"%'");
        }

        if(sanPham.getMauSac()!=null) {
            if(sanPham.getMauSac().equalsIgnoreCase("khac")){
                jpql.append(" And s.sanPham.mauSac is null");
            }else {
                jpql.append(" And s.sanPham.mauSac.ma = '" + sanPham.getMauSac() + "'");
            }
        }
        if(sanPham.getDongSanPham()!=null) {
            if(sanPham.getDongSanPham().equalsIgnoreCase("khac")){
                jpql.append(" And s.sanPham.dongSanPham is null");
            }else{
                DongSanPhamModel dongSanPhamModel = dongSanPhamRepository.findById(sanPham.getDongSanPham()).orElse(null);
                if(dongSanPhamModel==null){
                    jpql.append(" And s.sanPham.dongSanPham.thuongHieu.id = '" + sanPham.getDongSanPham() + "'");
                }else {
                    jpql.append(" And s.sanPham.dongSanPham.id = '" + sanPham.getDongSanPham() + "'");
                }
            }
        }
        if(sanPham.getKieuDang()!=null) {
            if(sanPham.getKieuDang().equalsIgnoreCase("khac")) {
                jpql.append(" And s.sanPham.kieuDang is null");
            }else{
                jpql.append(" And s.sanPham.kieuDang.id = '" + sanPham.getKieuDang() + "'");
            }
        }
        if(sanPham.getXuatXu()!=null) {
            if(sanPham.getXuatXu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.sanPham.xuatXu is null");
            }else{
                jpql.append(" And s.sanPham.xuatXu.id = '" + sanPham.getXuatXu() + "'");
            }
        }
        if(sanPham.getChatLieu()!=null) {
            if(sanPham.getChatLieu().equalsIgnoreCase("khac")) {
                jpql.append(" And s.sanPham.chatLieu is null");
            }else {
                jpql.append(" And s.sanPham.chatLieu.id = '" + sanPham.getChatLieu() + "'");
            }
        }
        if(sanPham.getGiaBan()!=null) jpql.append(" And s.sanPham.giaBan >= " + sanPham.getGiaBan());
        if(sanPham.getGiaMax()!=null) jpql.append(" And s.sanPham.giaBan <= " + sanPham.getGiaMax());

        if(sanPham.getSort()!=null){
            if(sanPham.getSort()==1) jpql.append( "ORDER BY s.sanPham.giaBan DESC");
            else if(sanPham.getSort()==2) jpql.append(" ORDER BY s.sanPham.giaBan");
            else if(sanPham.getSort()==3) jpql.append(" ORDER BY s.sanPham.ten DESC");
            else if(sanPham.getSort()==4) jpql.append(" ORDER BY s.sanPham.ten");
            else if(sanPham.getSort()==5) jpql.append("ORDER BY s.sanPham.soLuong DESC");
            else if(sanPham.getSort()==6) jpql.append("ORDER BY s.sanPham.soLuong");
            else if(sanPham.getSort()==7) jpql.append("ORDER BY s.sanPham.ngayTao DESC");
            else if(sanPham.getSort()==8) jpql.append("ORDER BY s.sanPham.ngayTao");
            else if(sanPham.getSort()==9) jpql.append("ORDER BY s.sanPham.ngayCapNhat DESC");
            else if(sanPham.getSort()==10) jpql.append("ORDER BY s.sanPham.ngayCapNhat");
        }

        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<ChiTietSanPhamModel> listContent = query.getResultList();
        listContent = listContent.stream().filter(s -> s.getSanPham().getHienThi()==true).collect(Collectors.toList());

        return listContent.stream().map(s -> new ChiTietSanPhamDtoResponse(s)).collect(Collectors.toList());
    }


}
