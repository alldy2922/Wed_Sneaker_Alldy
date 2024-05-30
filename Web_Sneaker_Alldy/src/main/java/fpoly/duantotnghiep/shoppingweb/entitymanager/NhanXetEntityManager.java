package fpoly.duantotnghiep.shoppingweb.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NhanXetEntityManager {
    @Autowired
    private EntityManager entityManager;

    public Map<String,Long> getAvgRatesByMaSPAndPheDuyet(String maSP, Boolean pheDuyet){
        return entityManager.createQuery("""
                                 SELECT n.rating, count(n) FROM NhanXetModel n
                                 WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma =: maSP AND n.pheDuyet =: pheDuyet
                                 GROUP BY n.rating
                            """, Tuple.class)
                .setParameter("maSP",maSP)
                .setParameter("pheDuyet",pheDuyet)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        k -> "rate"+((Number) k.get(0)).intValue(),
                        v -> ((Number) v.get(1)).longValue())
                );
    }

    public Map<String,Long> getAvgRatesByMaSP(String maSP){
        return entityManager.createQuery("""
                                 SELECT n.rating, count(n) FROM NhanXetModel n
                                 WHERE n.chiTietDonHangModel.chiTietSanPham.sanPham.ma =: maSP 
                                 GROUP BY n.rating
                            """, Tuple.class)
                .setParameter("maSP",maSP)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        k -> "rate"+((Number) k.get(0)).intValue(),
                        v -> ((Number) v.get(1)).longValue())
                );
    }
}
