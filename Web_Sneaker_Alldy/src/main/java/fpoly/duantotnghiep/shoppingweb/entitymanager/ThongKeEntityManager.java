package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.thongKe.ChiTietSanPhamThongKeDto;
import fpoly.duantotnghiep.shoppingweb.dto.thongKe.SanPhamBanChayDto;
import fpoly.duantotnghiep.shoppingweb.dto.thongKe.SanPhamDaBanDto;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ThongKeEntityManager {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ISanPhamRepository sanPhamRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Map<Integer,Long> getQuantityOrderByYear(String year){

        return entityManager.createQuery("""
                SELECT MONTH(d.ngayDatHang) AS month, COUNT(d)  AS quantity
                FROM DonHangModel d
                WHERE  YEAR(d.ngayDatHang) = :year 
                GROUP BY MONTH(d.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> ((Number) tuple.get("quantity")).longValue()
                        )
                );
    }

    public Map<Integer, BigDecimal> getRevenueInOrderByYear(String year){

        Map<Integer, BigDecimal> mapTongTien = new HashMap<>();
        Map<Integer, BigDecimal> mapTienGiam = new HashMap<>();
        Map<Integer, BigDecimal> result = new HashMap<>();

        mapTongTien = entityManager.createQuery("""
                SELECT MONTH(d.donHang.ngayDatHang) AS month, SUM(d.soLuong*d.donGiaSauGiam) AS revenue
                FROM ChiTietDonHangModel d
                WHERE YEAR(d.donHang.ngayDatHang) = :year AND d.donHang.trangThai <> 0 AND  d.donHang.trangThai <> 5
                GROUP BY MONTH(d.donHang.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> BigDecimal.valueOf(((Number) tuple.get("revenue")).doubleValue())
                        )
                );
        mapTienGiam = entityManager.createQuery("""
                SELECT MONTH(d.ngayDatHang) AS month,  SUM(d.tienGiam)  AS revenue
                FROM DonHangModel d
                WHERE YEAR(d.ngayDatHang) = :year AND d.trangThai <> 0 AND  d.trangThai <> 5
                GROUP BY MONTH(d.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> BigDecimal.valueOf(((Number) tuple.get("revenue")).doubleValue())
                        )
                );
        Map<Integer, BigDecimal> finalMapTienGiam = mapTienGiam;
        result = mapTongTien.entrySet().stream().peek(m -> {
            finalMapTienGiam.entrySet().stream().forEach(m1 -> {
                if (m.getKey() == m1.getKey()) m.setValue(m.getValue().subtract(m1.getValue()));
            });
        }).collect(Collectors.toMap(k -> k.getKey(),v -> v.getValue()));
        return result;
    }

    public Map<Integer, Long> getTotalProductsByYear(String year){
        return entityManager.createQuery("""
                SELECT MONTH(d.donHang.ngayDatHang) AS month, SUM(d.soLuong)  AS quantity
                FROM ChiTietDonHangModel d
                WHERE YEAR(d.donHang.ngayDatHang) = :year AND d.donHang.trangThai <> 0 AND  d.donHang.trangThai <> 5
                GROUP BY MONTH(d.donHang.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> ((Number) tuple.get("quantity")).longValue()
                        )
                );
    }

    public List<ChiTietSanPhamThongKeDto> getChiTietSanPhamDaBan(String maSanPham){

        return entityManager.createQuery("""
                                SELECT s AS sanPham, SUM(cd.soLuong) AS soLuong
                                 FROM ChiTietSanPhamModel s LEFT JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                 WHERE s.sanPham.ma = :maSanPham AND cd.donHang.trangThai <> 0 AND  cd.donHang.trangThai <> 5
                                 GROUP BY s
                                 ORDER BY soLuong DESC, s.size.ma ASC 
                                 """,Tuple.class)
                                .setParameter("maSanPham",maSanPham)
                                .getResultList().stream()
                                .map(r -> new ChiTietSanPhamThongKeDto(
                                        (ChiTietSanPhamModel) r.get("sanPham"),
                                        r.get("soLuong") == null ? 0L : (Long)  r.get("soLuong")
                                ))
                                .collect(Collectors.toList());


    }

    public Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate){
        StringBuilder jpql = new StringBuilder("SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE c.donHang.ngayDatHang between :firstDate AND :lastDate");
        }
        System.out.println(jpql);
        System.out.println((Long) entityManager.createQuery(jpql.toString()).setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate).getSingleResult());
        return (Long) entityManager.createQuery(jpql.toString())
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getSingleResult();
    }
    public Long getQuantityOrdersWithDate(String firstDate, String lastDate){
        StringBuilder jpql = new StringBuilder("SELECT COUNT(d) FROM DonHangModel d  ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE d.ngayDatHang between '"+firstDate+"' and '"+lastDate+"'");
        }
        return (Long) entityManager.createQuery(jpql.toString()).getSingleResult();
    }
    public BigDecimal getTotalPriceInOrdersWithDate(String firstDate, String lastDate){
        StringBuilder jpql = new StringBuilder("SELECT SUM(c.donGiaSauGiam*c.soLuong) - SUM(c.donHang.tienGiam) FROM ChiTietDonHangModel c  ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE c.donHang.ngayDatHang between :firstDate AND :lastDate");
        }
        return (BigDecimal) entityManager
                .createQuery(jpql.toString())
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getSingleResult();
    }



    public List<ChiTietSanPhamThongKeDto> getChiTietSanPhamDaBanWithDate(String maSanPham,Date firstDate, Date lastDate){

        return entityManager.createQuery("""
                                SELECT s AS sanPham, SUM(cd.soLuong) AS soLuong
                                 FROM ChiTietSanPhamModel s LEFT JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                 WHERE s.sanPham.ma = :maSanPham AND cd.donHang.ngayDatHang BETWEEN :firstDate And :lastDate and cd.donHang.trangThai <> 0 AND  cd.donHang.trangThai <> 5
                                 GROUP BY s
                                 ORDER BY soLuong DESC, s.size.ma ASC 
                                 """,Tuple.class)
                .setParameter("maSanPham",maSanPham)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getResultList().stream()
                .map(r -> new ChiTietSanPhamThongKeDto(
                        (ChiTietSanPhamModel) r.get("sanPham"),
                        (Long)  r.get("soLuong")
                ))
                .collect(Collectors.toList());


    }
    public List<SanPhamDaBanDto> getSanPhamDaBanWithDate(Date firstDate, Date lastDate){
        List<SanPhamBanChayDto> listSanPham = entityManager.createQuery("""
                                                                 SELECT s.sanPham.ma AS sanPham, SUM(cd.soLuong) AS soLuong
                                                                 FROM ChiTietSanPhamModel s JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                                                 WHERE cd.donHang.ngayDatHang BETWEEN :firstDate And :lastDate AND cd.donHang.trangThai <> 0 AND  cd.donHang.trangThai <> 5
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

        List<SanPhamDaBanDto> result = listSanPham.stream()
                                        .map(s -> new SanPhamDaBanDto(s,getChiTietSanPhamDaBanWithDate(s.getSanPham().getMa(),firstDate,lastDate)))
                                        .collect(Collectors.toList());
        return result;
    }

    public Map<String,String> getDoanhThuDetailByDate(Date firstDate, Date lastDate,Integer loai){
        Map<String,String> result = new HashMap<>();
        BigDecimal tongTien = (BigDecimal) entityManager
                .createQuery("""
                                SELECT SUM(c.donGia*c.soLuong) FROM ChiTietDonHangModel c  
                                WHERE c.donHang.ngayDatHang between :firstDate AND :lastDate AND c.donHang.loai = :loai AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5
                            """)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .setParameter("loai",loai)
                .getSingleResult();
        BigDecimal tienGiam = (BigDecimal) entityManager
                .createQuery("""
                                                SELECT SUM((c.donGia-c.donGiaSauGiam)*c.soLuong) + (SElECT SUM(d.tienGiam) FROM DonHangModel d WHERE d.ngayDatHang between :firstDate AND :lastDate AND d.loai = :loai AND d.trangThai <> 0 AND  d.trangThai <> 5  ) 
                                                FROM ChiTietDonHangModel c  
                                                WHERE c.donHang.ngayDatHang between :firstDate AND :lastDate AND c.donHang.loai = :loai AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5
                                            """)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .setParameter("loai",loai)
                .getSingleResult();
        result.put("tongTien",tongTien == null ? "0" : tongTien+"");
        result.put("tienGiam",tienGiam == null ? "0" : tienGiam+"");
        return result;
    }
    public String getTotalDoanhThuByDate(Date firstDate, Date lastDate){
        BigDecimal tongTien = (BigDecimal) entityManager
                .createQuery("""
                                SELECT SUM(c.donGiaSauGiam*c.soLuong) - (SElECT SUM(d.tienGiam) FROM DonHangModel d WHERE d.ngayDatHang between :firstDate AND :lastDate  AND d.trangThai <> 0 AND  d.trangThai <> 5  ) FROM ChiTietDonHangModel c  
                                WHERE c.donHang.ngayDatHang between :firstDate AND :lastDate  AND c.donHang.trangThai <> 0 AND  c.donHang.trangThai <> 5
                            """)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getSingleResult();
        return tongTien == null ? "0" : tongTien+"";
    }
    public Map<String, Long> getDetailOrdersByDate(Date firstDate, Date lastDate){
        return entityManager.createQuery("""
                SELECT d.trangThai, COUNT(d)
                FROM DonHangModel d
                WHERE d.ngayDatHang between :firstDate AND :lastDate AND d.loai=0
                GROUP BY d.trangThai
            """, Tuple.class)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> "TH" + (((Number) tuple.get(0)).intValue()),
                                tuple -> ((Number) tuple.get(1)).longValue()
                        )
                );
    }
    public Map<String, Long> getDetailOrdersTaiQuayByDate(Date firstDate, Date lastDate){
        return entityManager.createQuery("""
                SELECT d.trangThai, COUNT(d)
                FROM DonHangModel d
                WHERE d.ngayDatHang between :firstDate AND :lastDate AND d.loai=1
                GROUP BY d.trangThai
            """, Tuple.class)
                .setParameter("firstDate",firstDate)
                .setParameter("lastDate",lastDate)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> "TH" + (((Number) tuple.get(0)).intValue()),
                                tuple -> ((Number) tuple.get(1)).longValue()
                        )
                );
    }
}
