package fpoly.duantotnghiep.shoppingweb.util;//package fpoly.duantotnghiep.shoppingweb.util;
//
//import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
//import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
//import fpoly.duantotnghiep.shoppingweb.model.ThongBaoModel;
//import fpoly.duantotnghiep.shoppingweb.model.ThongBaoNhanModel;
//import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
//import fpoly.duantotnghiep.shoppingweb.service.IThongBaoNhanService;
//import fpoly.duantotnghiep.shoppingweb.service.IThongBaoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class SocketUtil {
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//    @Autowired
//    private IKhachHangService taiKhoanService;
//    @Autowired
//    private IThongBaoService thongBaoService;
//    @Autowired
//    private IThongBaoNhanService thongBaoNhanService;
//
//    private static SimpMessagingTemplate staticSimpMessagingTemplate;
//    private static IKhachHangService staticTaiKhoanService;
//    private static IThongBaoService staticThongBaoService;
//    private static IThongBaoNhanService staticThongBaoNhanService;
//
//    public static void sendNotification(ThongBaoModel thongBaoModel) {
//
//        KhachHangModel taiKhoanGui = new KhachHangModel();
//        taiKhoanGui.setId("afc05e0c-4c66-11ee-b10b-d69e940a783b");
//        thongBaoModel.setTaiKhoanGui(taiKhoanGui);
//
//        List<KhachHangDtoResponse> danhSachTaiKhoanHasRoleAdmin = staticTaiKhoanService.getByRoleAdmin();
//
//        List<ThongBaoNhanModel> thongBaoNhanModels = new ArrayList<>();
//        staticThongBaoService.save(thongBaoModel);
//
//        for (KhachHangDtoResponse t : danhSachTaiKhoanHasRoleAdmin) {
//            KhachHangModel taiKhoan = staticTaiKhoanService.findById(t.getId());
//            thongBaoNhanModels.add(new ThongBaoNhanModel(null, thongBaoModel, taiKhoan, false));
//        }
//
//
//        thongBaoNhanModels = staticThongBaoNhanService.saveAll(thongBaoNhanModels);
//
//        thongBaoNhanModels.forEach(t -> {
//            staticSimpMessagingTemplate.convertAndSend("/" + t.getNguoiNhan().getId(), t);
//        });
//
//    }
//
//    ////////////////////????//////////////////////////////////////////////////////////////////////////////
//
//    @Autowired
//    public void setStaticSimpMessagingTemplate(SimpMessagingTemplate staticSimpMessagingTemplate) {
//        SocketUtil.staticSimpMessagingTemplate = staticSimpMessagingTemplate;
//    }
//
//    @Autowired
//    public void setStaticTaiKhoanService(IKhachHangService staticTaiKhoanService) {
//        SocketUtil.staticTaiKhoanService = staticTaiKhoanService;
//    }
//
//    @Autowired
//    public void setStaticThongBaoService(IThongBaoService staticThongBaoService) {
//        SocketUtil.staticThongBaoService = staticThongBaoService;
//    }
//
//    @Autowired
//    public void setStaticThongBaoNhanService(IThongBaoNhanService staticThongBaoNhanService) {
//        SocketUtil.staticThongBaoNhanService = staticThongBaoNhanService;
//    }
//}
