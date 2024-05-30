package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.service.impl.DonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller("don-hang-ctrl-user")
@RequestMapping("don-hang")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private VnPayServiceImpl vnPayService;

    @GetMapping("chi-tiet-don-hang/{maDH}")
    public String viewChiTietDonHang(@PathVariable("maDH")String maDH){
        if(!donHangService.existsByMa(maDH)){
            return "admin/authen/notFound";
        }
        return "user/ChiTietDonHang";
    }

    @GetMapping("email/thanh-toan/{ma}")
    public Object ThanhToanHoaDon(HttpServletRequest request, @PathVariable("ma") String ma)throws MessagingException {
//        DonHangDtoResponse response = donHangService.checkOut(donHangDTORequest);
        DonHangDtoResponse response =  donHangService.findByMa(ma);

        if(response.getTrangThai() !=5){
            return "email/daThanhToan";
        }

        String diachi = response.getDiaChiChiTiet();
        DonHangReponseUser donHangReponseUser = donHangService.findByMaUser(ma);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(ma, baseUrl, (response.getTongTien().intValue()*100)+"");
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", vnpayUrl);
//            return new ResponseEntity<String>(headers,HttpStatus.FOUND);
        Map<String,String> vnPayUrl = new HashMap<>();
        vnPayUrl.put("vnPayUrl",vnpayUrl);
        int paymentStatus =vnPayService.orderReturn(request,diachi);
        System.out.println(paymentStatus);
        System.out.println(vnpayUrl);
//            if (paymentStatus == 1){
//                donHangService.updateTrangThai1(response.getMa(), 2);
//            } else {
//                donHangService.updateTrangThai1(response.getMa(), 5);
//            }
        return "redirect:"+vnpayUrl;
    }
}
