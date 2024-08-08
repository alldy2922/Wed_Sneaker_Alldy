package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.thongke;

import fpoly.duantotnghiep.shoppingweb.entitymanager.SanPhamEntityManager;
import fpoly.duantotnghiep.shoppingweb.entitymanager.ThongKeEntityManager;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${admin.domain}/thong-ke")
public class ThongKeRestController {

    @Autowired
    private IDonHangResponsitory donHangDAO;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private IDonHangService donHangService;
    @Autowired
    private ThongKeEntityManager thongKeEntityManager;
    @Autowired
    private SanPhamEntityManager sanPhamEntityManager;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//    --Bieu Do SP Ban chay
    @PostMapping("san-pham-ban-chay")
    public ResponseEntity<?> thongke(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam("num") int num){
        return ResponseEntity.ok(donHangDAO.thongKeSanPhamBanChay(start, end, num));
    }

    @GetMapping("tong-quat")
    public ResponseEntity<?> getTotalQauntityInOrdersWithDate(@RequestParam(required = false)
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date firstDate,
                                                              @RequestParam(required = false)
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date lastDate){

        lastDate.setHours(23); lastDate.setMinutes(59); lastDate.setSeconds(59);
        firstDate.setHours(00); firstDate.setMinutes(00); firstDate.setSeconds(00);


        Map<String,Object> result = new HashMap<>();
        result.put("quantityProducts",donHangService.getTotalQauntityInOrdersWithDate(firstDate, lastDate).toString());
        result.put("quantityProductsDetail",donHangService.getQuantityProductInOrderDetailWithDate(firstDate, lastDate));
        result.put("quantityOrders",donHangService.getQuantityOrdersWithDate(firstDate,lastDate).toString());
        result.put("totalPrice",thongKeEntityManager.getTotalDoanhThuByDate(firstDate,lastDate));
        result.put("sanPhamDaBan",thongKeEntityManager.getSanPhamDaBanWithDate(firstDate,lastDate));
        result.put("doanhThuDetail",thongKeEntityManager.getDoanhThuDetailByDate(firstDate,lastDate,0));
        result.put("doanhThuTaiQuayDetail",thongKeEntityManager.getDoanhThuDetailByDate(firstDate,lastDate,1));
        result.put("ordersDetail",thongKeEntityManager.getDetailOrdersByDate(firstDate,lastDate));
        result.put("ordersTaiQuayDetail",thongKeEntityManager.getDetailOrdersTaiQuayByDate(firstDate,lastDate));
        //api lấy tổng số tiền trả
        result.put("doanhThuTra",thongKeEntityManager.getDoanhThuTra(firstDate,lastDate));


        return ResponseEntity.ok(result);
    }

    @GetMapping("tong-quat-nam")
    public ResponseEntity<?> getQuantityOrderByYear(@RequestParam(required = false)String year){
        if(year==null){
            year = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()+"";
        }

        Map<String,List> result = new HashMap<>();
        result.put("revenue",thongKeEntityManager.getRevenueInOrderByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("totalProducts",thongKeEntityManager.getTotalProductsByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("quantityOrders",thongKeEntityManager.getQuantityOrderByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("months",thongKeEntityManager.getQuantityOrderByYear(year).entrySet().stream().map(m -> "Tháng " + m.getKey()).collect(Collectors.toList()));


        return ResponseEntity.ok(result);
    }
    //api lấy sản phẩm bị trả nhiều nhất
    @GetMapping("san-pham-tra")
    public ResponseEntity<?> getQuantityOrderTra(){


        Map<String,List> result = new HashMap<>();
        result.put("sanPhamTra",thongKeEntityManager.getChiTietSanPhamTra());

        return ResponseEntity.ok(result);
    }


    @GetMapping("san-pham-ban-chay")
    public ResponseEntity<?> getSanPhamBanChay(){
        return ResponseEntity.ok(sanPhamEntityManager.getSanPhamBanChay());
    }
    @GetMapping("san-pham-ban-chay/{ma}")
    public ResponseEntity<?> getChiTietBanChay(@PathVariable("ma")String ma){
        return ResponseEntity.ok(thongKeEntityManager.getChiTietSanPhamDaBan(ma));

    }


    @GetMapping("san-pham-ton")
    public ResponseEntity<?> getSanPhamTon(){
        return ResponseEntity.ok(sanPhamEntityManager.getSanPhamTon());
    }


    @GetMapping("san-pham-da-ban")
    public ResponseEntity<?> getSanPhamDaBanWithDate(@RequestParam(required = false)
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date firstDate,
                                                              @RequestParam(required = false)
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date lastDate){

        lastDate.setHours(23); lastDate.setMinutes(59); lastDate.setSeconds(59);
        firstDate.setHours(00); firstDate.setMinutes(00); firstDate.setSeconds(00);

        return ResponseEntity.ok(thongKeEntityManager.getSanPhamDaBanWithDate(firstDate,lastDate));
    }



}
