package fpoly.duantotnghiep.shoppingweb.config;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EmailParameters {
    private static String email;
    private static String orderId;
    private static List<ChiTietDonHangDtoResponse> lstSanPham;
    private final static AtomicBoolean shouldSendEmail = new AtomicBoolean(false);


    public static void setParameters(String email, String orderId, List<ChiTietDonHangDtoResponse> lstSanPham) {
        EmailParameters.email = email;
        EmailParameters.orderId = orderId;
        EmailParameters.lstSanPham = lstSanPham;
    }
// Getters and Setters

    public static String getEmail() {
        return email;
    }

    public static String getOrderId() {
        return orderId;
    }

    public static List<ChiTietDonHangDtoResponse> getLstSanPham() {
        return lstSanPham;
    }

}
