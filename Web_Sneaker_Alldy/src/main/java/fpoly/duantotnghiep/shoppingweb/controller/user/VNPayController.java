package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.impl.DonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VNPayController {
    @Autowired
    private VnPayServiceImpl vnPayService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private IDonHangResponsitory donHangResponsitory;
    @GetMapping("/vnpay-payment")
    public String GetMappings(HttpServletRequest request,
                              Authentication authentication,
                              Model model, DonHangModel donHangModel) throws MessagingException {
        int paymentStatus =vnPayService.orderReturn(request,donHangModel.getDiaChiChiTiet());
        String totalPrice = request.getParameter("vnp_Amount");
        String oderInfo = request.getParameter("vnp_OrderInfo");
        model.addAttribute("totalPrice", totalPrice);
        String url = "";

        Integer loaiDonHang = donHangResponsitory.findById(oderInfo).get().getLoai();
        if(loaiDonHang==1){
            url = "admin/thanhToan/thanhCong";
        }else {
            if(authentication == null){
                url = "redirect:/gio-hang";
            }else {
                url = "redirect:/lich-su-mua-hang1";
            }
        }
        System.out.println(paymentStatus);
        if(paymentStatus == 1){
            if(loaiDonHang!=1) donHangService.updateTrangThai(oderInfo,1);
            else donHangService.updateTrangThai(oderInfo,4);
            return url;
        }else{
            if(loaiDonHang==1){
                donHangService.updateTrangThai(oderInfo, 5);
                return "redirect:/admin/don-hang/ban-hang";
            }
            else {
                donHangService.updateTrangThai(oderInfo, 5);
                return "user/authen/thanhToanFail";
            }
        }
//        return paymentStatus == 1 ? url : "user/authen/thanhToanFail";
    }
}
