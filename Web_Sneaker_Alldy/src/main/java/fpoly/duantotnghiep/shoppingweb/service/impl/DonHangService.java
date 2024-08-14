package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.config.security.EmailParameters;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.*;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDoiDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.*;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.repository.*;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonHangService implements IDonHangService {

    @Autowired
    private IDonHangResponsitory donHangResponsitory;
    @Autowired
    private IChiTietDonHangService chiTietDonHangService;
    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    private IChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private IDonHangTraRepository donHangTraRepository;
    @Autowired
    private IDonHangDoiRepository donHangDoiRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai) {
        return null;
    }

    @Override
    public List<DonHangReponseUser> getAllByKhachHangAndTrangThai(String nguoiSoHuu, Integer trangThai) {
        return donHangResponsitory.findAllByKhachHangAndTrangThai(nguoiSoHuu, trangThai).stream().map(d -> new DonHangReponseUser(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangReponseUser> getAllByKhachHangAndTrangThaiTra(String nguoiSoHuu, Integer trangThai) {
        return donHangResponsitory.findAllByKhachHangAndTrangThaiTra(nguoiSoHuu, trangThai).stream().map(d -> new DonHangReponseUser(d)).collect(Collectors.toList());
    }
    @Override
    public List<DonHangReponseUser> getAllByKhachHangAndTrangThaiDoi(String nguoiSoHuu, Integer trangThai) {
        return donHangResponsitory.findAllByKhachHangAndTrangThaiDoi(nguoiSoHuu, trangThai).stream().map(d -> new DonHangReponseUser(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangTraDTOReponse> getCTSPTra(String nguoiSoHuu, String ma) {
        return donHangTraRepository.findAllByKhachHangAndMaDonHang(nguoiSoHuu, ma).stream().map(d -> new DonHangTraDTOReponse(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangTraDTOReponse> getAllByDonHangTra(String ma){
        List<DonHangTraModel> donHangTraModels = donHangTraRepository.getAllByDonHang(ma);
        return donHangTraModels.stream().map(d -> new DonHangTraDTOReponse(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangDoiDTOReponse> getCTSPDoi(String nguoiSoHuu, String ma) {
        return donHangDoiRepository.findAllByKhachHangAndMaDonHang(nguoiSoHuu, ma).stream().map(d -> new DonHangDoiDTOReponse(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangDoiDTOReponse> getAllByDonHangDoi(String ma){
        List<DonHangDoiModel> donHangTraModels = donHangDoiRepository.getAllByDonHang(ma);
        return donHangTraModels.stream().map(d -> new DonHangDoiDTOReponse(d)).collect(Collectors.toList());
    }
    @Override
    public Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, limit);

        Page<DonHangModel> pageModel = donHangResponsitory.getAllByTrangThai(trangThai, pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());
    }


    @Override
    public List<DonHangTraDTOReponse> getAllDonHangTra(Integer trangThai){
        List<DonHangTraModel> donHangTraModels = donHangTraRepository.getAllByTrangThai(trangThai);
        return donHangTraModels.stream().map(d -> new DonHangTraDTOReponse(d)).collect(Collectors.toList());
    }

    @Override
    public List<DonHangDoiDTOReponse> getAllDonHangDoi(Integer trangThai){
        List<DonHangDoiModel> donHangDoiModels = donHangDoiRepository.getAllByTrangThai(trangThai);
        return donHangDoiModels.stream().map(d -> new DonHangDoiDTOReponse(d)).collect(Collectors.toList());
    }
    @Override
    public DonHangDtoResponse checkOut(DonHangDTORequest donHangDTORequest) {
        DonHangModel model = donHangDTORequest.mapModel();
        model.setLoai(0);
        if (model.getPhuongThucThanhToan()) {
            model.setTrangThai(2);
        } else {
            model.setTrangThai(5);
        }
        donHangResponsitory.saveAndFlush(model);
        return new DonHangDtoResponse(donHangResponsitory.findById(model.getMa()).get());
    }

    @Override
    public DonHangDtoResponse findByMa(String ma) {
        return new DonHangDtoResponse(donHangResponsitory.findById(ma).orElse(new DonHangModel()));
    }

    @Override
    public DonHangTraDTOReponse findByMaTra(String ma) {
        return new DonHangTraDTOReponse(donHangTraRepository.findByMaDonHangN(ma));
    }

    @Override
    public List<DonHangDoiDTOReponse> findByMaDoi(String ma) {
        List<DonHangDoiModel> donHangList = donHangDoiRepository.findByMaDonHang(ma);
        return donHangList.stream()
                .map(DonHangDoiDTOReponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangReponseUser findByMaUser(String ma) {
        return new DonHangReponseUser((donHangResponsitory.findById(ma).orElse(new DonHangModel())));
    }

    @Override
    public Boolean existsByMa(String ma) {
        return donHangResponsitory.existsById(ma);
    }

    @Override
    public void updateTrangThai(String maDonHang, Integer trangThai) throws MessagingException {
//         donHangResponsitory.updateTrangThaiDonHang(trangThai,maDonHang);
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setTrangThai(trangThai);

        if (model.getLoai() == 0) {
            String subject = "";
            String messeger = "";
            String title = "";
            if (trangThai == 2) {
                subject = "Tạo đơn hàng!";
                title = "Tạo đơn hàng thành công";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được tạo. Cảm ơn bạn đã mua hàng";
            } else if (trangThai == 1) {
                subject = "Xác nhận đơn hàng!";
                title = "Xác nhận hàng thành công";
                model.setNgayXacNhan(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được xác nhận. Cảm ơn bạn đã mua hàng. Đơn hàng đang được đóng gói và sẽ đến tay bạn trong vài ngày tới";
            } else if (trangThai == 3) {
                subject = "Chuyển giao đơn hàng!";
                title = "Đơn hàng đang được giao";
                model.setNgayGiaoHang(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đang được giao. Cảm ơn bạn đã mua hàng. Đơn hàng đang được giao và sẽ đến tay bạn trong vài ngày tới";
            } else if (trangThai == 4) {
                subject = "Hoàn thành đơn hàng!";
                title = "Đơn hàng đã giao thành công";
                model.setNgayHoanThanh(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn được giao thành công. Cảm ơn bạn đã mua hàng.";
            } else if (trangThai == 5) {
                subject = "Đơn hàng chưa đuọc thanh toán!";
                title = "Đơn hàng của bạn chưa được thanh toán";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn chưa được thanh toán.Vui lòng thanh toán đơn hàng của bạn.";
            } else if (trangThai == 6) {
                subject = "Chờ xác nhận trả hàng!";
                title = "Đơn hàng đang chờ xác nhận trả hàng";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đang chờ xác nhận trả hàng. Chúng tôi sẽ xử lý yêu cầu của bạn sỡm nhất có thể.";
            }


            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(maDonHang);
            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }

            Context context = new Context();
            context.setVariable("donHang", new DonHangDtoResponse(model));
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", title);
            String finalSubject = subject;
            new Thread(() -> {
                try {
                    sendEmailDonHang(model.getEmail(), finalSubject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        donHangResponsitory.saveAndFlush(model);
    }

    @Override
    public void updateTrangThaiTra(String maDonHangTra, Integer trangThai) throws MessagingException {
//         donHangResponsitory.updateTrangThaiDonHang(trangThai,maDonHang);
        System.out.println("service= " + maDonHangTra);

        List<DonHangTraModel> models = donHangTraRepository.findByMaDonHang(maDonHangTra);
        for (DonHangTraModel model : models) {
            model.setTrangThai(trangThai);
            String subject = "";
            String messeger = "";
            String title = "";
            if (trangThai == 1) {
                subject = "Chờ xàc nhận trả hàng!";
                title = "Đơn hàng đang chờ xác nhận trả hàng";
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đang chờ xác nhận trả hàng. Chúng tôi sẽ xử lý yêu cầu của bạn sỡm nhất có thể.";
            } else if (trangThai == 2) {
                subject = "Kiểm tra hoàn hàng!";
                title = "Đơn hàng đang được kiểm tra hoàn hàng";
                model.setNgayKiemTra(new Date());

                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đang được kiểm tra hoàn hàng. Chúng tôi sẽ thông báo cho bạn khi quá trình kiểm tra hoàn tất.";
            } else if (trangThai == 3) {
                subject = "Hoàn tiền!";
                title = "Đơn hàng đã được hoàn tiền";
                model.setNgayHoanThanh(new Date());
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đã được hoàn tiền. Số tiền hoàn lại sẽ sớm có trong tài khoản của bạn.";
                //cộng lại sản phẩm
                List<DonHangTraModel> ctdhModel = donHangTraRepository.findByMaDonHang(maDonHangTra);
                ctdhModel.forEach(c -> {
                    int soLuongInDonHang = c.getSoLuong();
                    ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
                    sanPhamInDonHang.setSoLuong(soLuongInDonHang + sanPhamInDonHang.getSoLuong());
                    chiTietSanPhamRepository.save(sanPhamInDonHang);
                });
            } else if (trangThai == 0) {
                subject = "Từ chối Hoàn tiền!";
                title = "Tư Chối hoàn tiền";
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đã không được hoàn tiền.";
            }


            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(maDonHangTra);
            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }

            Context context = new Context();
            context.setVariable("donHangTra", new DonHangTraDTOReponse(model));
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", title);
            String finalSubject = subject;
            new Thread(() -> {
                try {
                    sendEmailDonHang(model.getDonHang().getEmail(), finalSubject, "email/capNhatTrangThaiTra", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
            donHangTraRepository.saveAndFlush(model);
        }
    }

    @Override
    public void updateTrangThaiDoi(String maDonHangDoi, Integer trangThai) throws MessagingException {
        System.out.println("service= " + maDonHangDoi);

        List<DonHangDoiModel> models = donHangDoiRepository.findByMaDonHang(maDonHangDoi);
        for (DonHangDoiModel model : models) {
            model.setTrangThai(trangThai);
            String subject = "";
            String messeger = "";
            String title = "";
            if (trangThai == 1) {
                subject = "Chờ xàc nhận đổi hàng!";
                title = "Đơn hàng đang chờ xác nhận đổi hàng";
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đang chờ xác nhận đổi hàng. Chúng tôi sẽ xử lý yêu cầu của bạn sỡm nhất có thể.";
            } else if (trangThai == 2) {
                subject = "Kiểm tra đổi hàng!";
                title = "Đơn hàng đang được kiểm tra hàng hoàn";
                model.setNgayKiemTra(new Date());
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đang được kiểm tra hàng hoàn. Chúng tôi sẽ thông báo cho bạn khi quá trình kiểm tra hoàn tất.";

            } else if (trangThai == 3) {
                subject = "Đơn Hàng Đổi Đang Được Giao!";
                title = "Đơn hàng đã được đổi hàng";
                model.setNgayGiao(new Date());
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng đổi của bạn đã được bàn giao cho đơn vị vận chuyển đang trên đường giao.Vui lòng để ý liên lạc.";
                List<DonHangDoiModel> ctdhModel = donHangDoiRepository.findByMaDonHang(maDonHangDoi);

                ctdhModel.forEach(c -> {

                    long soLuong = c.getSoLuong();
                    // Lấy thông tin sản phẩm từ đơn hàng đổi và đơn hàng hoàn thành
                    ChiTietSanPhamModel sanPhamMoi = chiTietSanPhamRepository.findById(c.getSanPhamDoi()).orElseThrow(() -> new RuntimeException("Product not found in DonHang"));
                    sanPhamMoi.setSoLuong(soLuong+sanPhamMoi.getSoLuong());
                    chiTietSanPhamRepository.save(sanPhamMoi);

                });
            } else if (trangThai == 4) {
                subject = "Đã Đổi Hàng!";
                title = "Đơn hàng đã được đổi hàng";
                model.setNgayHoanThanh(new Date());
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đã được đổi hàng.";
                List<DonHangDoiModel> ctdhModel = donHangDoiRepository.findByMaDonHang(maDonHangDoi);

                ctdhModel.forEach(c -> {

                    long soLuong = c.getSoLuong();
                    // Lấy thông tin sản phẩm từ đơn hàng đổi và đơn hàng hoàn thành
                    ChiTietSanPhamModel sanPhamMoi = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).orElseThrow(() -> new RuntimeException("Product not found in DonHang"));
                    sanPhamMoi.setSoLuong(sanPhamMoi.getSoLuong()-soLuong);
                    chiTietSanPhamRepository.save(sanPhamMoi);

                });

            } else if (trangThai == 0) {
                subject = "Từ chối Hoàn tiền!";
                title = "Tư Chối hoàn tiền";
                messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ", đơn hàng của bạn đã không được hoàn tiền.";
            }


            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(maDonHangDoi);
            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }

            Context context = new Context();
            context.setVariable("donHangTra", new DonHangDoiDTOReponse(model));
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", title);
            String finalSubject = subject;
            new Thread(() -> {
                try {
                    sendEmailDonHang(model.getDonHang().getEmail(), finalSubject, "email/capNhatTrangThaiTra", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
            donHangDoiRepository.saveAndFlush(model);
        }

    }
    @Override
    public void huyDonHang(List<String> maDonHang, String lyDo) throws MessagingException {
//         donHangResponsitory.updateTrangThaiDonHang(trangThai,maDonHang);
        maDonHang.forEach(ma -> {
            DonHangModel model = donHangResponsitory.findById(ma).get();
            model.setLyDoHuy(lyDo);
            model.setTrangThai(0);
            model.setNgayHuy(new Date());

            String subject = "Hủy đơn hàng!";
            String messeger = "Xin chào " + model.getTenNguoiNhan() + ", yêu cầu hoàn đơn hàng của bạn đã hủy. Cảm ơn bạn đã ghé qua cửa hàng";

            List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
            ctdhModel.forEach(c -> {
                int soLuongInDonHang = c.getSoLuong();
                ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
                sanPhamInDonHang.setSoLuong(soLuongInDonHang + sanPhamInDonHang.getSoLuong());
                chiTietSanPhamRepository.save(sanPhamInDonHang);
            });


            if (model.getLoai() == 0) {
                List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
                BigDecimal tongTien = BigDecimal.valueOf(0);
                for (ChiTietDonHangDtoResponse d : lstSanPham) {
                    tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
                }

                Context context = new Context();
                context.setVariable("donHang", new DonHangDtoResponse(model));
                context.setVariable("products", lstSanPham);
                context.setVariable("totalPrice", tongTien);
                context.setVariable("mess", messeger);
                context.setVariable("title", subject);
                context.setVariable("lyDo", lyDo);
                new Thread(() -> {
                    try {
                        sendEmailDonHang(model.getEmail(), subject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            donHangResponsitory.saveAndFlush(model);
        });
    }

    @Override
    public void huyTraHang(List<String> maDonHang, String lyDoTraHang) throws MessagingException {
        maDonHang.forEach(ma -> {
            DonHangModel model = donHangResponsitory.findById(ma).get();
            model.setLyDoTraHang(lyDoTraHang);
            model.setTrangThai(0);

            String subject = "Từ chối hoàn đơn hàng!";
            String messeger = "Xin chào " + model.getTenNguoiNhan() + ",yêu cầu đơn hàng của bạn đã bị hủy. Cảm ơn bạn đã ghé qua cửa hàng";

            List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
           


            if (model.getLoai() == 0) {
                List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
                BigDecimal tongTien = BigDecimal.valueOf(0);
                for (ChiTietDonHangDtoResponse d : lstSanPham) {
                    tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
                }

                Context context = new Context();
                context.setVariable("donHang", new DonHangDtoResponse(model));
                context.setVariable("products", lstSanPham);
                context.setVariable("totalPrice", tongTien);
                context.setVariable("mess", messeger);
                context.setVariable("title", subject);
                context.setVariable("lyDoTraHang", lyDoTraHang);
                new Thread(() -> {
                    try {
                        sendEmailDonHang(model.getEmail(), subject, "email/capNhatTrangThaiDonHangTra", context, lstSanPham);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }).start();
            }


            donHangResponsitory.saveAndFlush(model);
        });
    }


    //bản cũ
    @Override
    public void huyDonHangUser(String maDonHang, String lyDo) throws MessagingException {
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setNgayHuy(new Date());
        model.setLyDoHuy(lyDo);
        model.setTrangThai(0);
        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        ctdhModel.forEach(c -> {
            int soLuongInDonHang = c.getSoLuong();
            ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
            sanPhamInDonHang.setSoLuong(soLuongInDonHang + sanPhamInDonHang.getSoLuong());
            chiTietSanPhamRepository.save(sanPhamInDonHang);
        });
        donHangResponsitory.save(model);
    }
///////////////


    @Override
    public DonHangDtoResponse updateDonHang(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoThayDoi) {
        DonHangModel donHangOld = donHangResponsitory.findById(request.getMa()).orElse(null);
        DonHangModel model = request.mapModel();
        model.setLyDoThayDoi(lyDoThayDoi);
        if (donHangOld.getLoai() == 1) {
            model.setEmail(null);
        }
        model.setLoai(donHangOld.getLoai());
        Boolean phuongThucThanhToan = model.getPhuongThucThanhToan();
        if(donHangOld.getLoai()==0){
            if (phuongThucThanhToan) {
                model.setTrangThai(2);
                System.out.println("ASDASDASDASDASD");
            } else {
                model.setTrangThai(5);
            }
        }else{
            model.setTrangThai(donHangOld.getTrangThai());
        }
        List<String> maCTSPNew = products.stream().map(c -> c.getId()).collect(Collectors.toList());
        List<ChiTietDonHangModel> ctdhModelOld = chiTietDonHangRepository.findAllByDonHang(model);
        ctdhModelOld.forEach(c -> {
            if (!maCTSPNew.contains(c.getId())) {
                //Thêm lại số lượng khi xóa sản phẩm khỏi đơn hàng
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
                chiTietSanPhamModel.setSoLuong(chiTietSanPhamModel.getSoLuong() + c.getSoLuong());
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);

                chiTietDonHangRepository.deleteById(c.getId());//xóa khỏi đơn hàng
            }
        });
        products.forEach(p -> {
            if (p.getId() != null) {
                ChiTietDonHangModel chiTietDHOld = chiTietDonHangRepository.findById(p.getId()).get();
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(p.getSanPhamCT()).get();
                Long soLuong = chiTietSanPhamModel.getSoLuong() - (p.getSoLuong() - chiTietDHOld.getSoLuong());
                chiTietSanPhamModel.setSoLuong(soLuong);
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);
            } else {
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(p.getSanPhamCT()).get();
                Long soLuong = chiTietSanPhamModel.getSoLuong() - p.getSoLuong();
                chiTietSanPhamModel.setSoLuong(soLuong);
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);
            }
        });

        products.forEach(p -> chiTietDonHangRepository.saveAndFlush(p.mapModel()));

        if (donHangOld.getVoucher() != null) {
            model.setVoucher(donHangOld.getVoucher());
        }
        if (donHangOld.getNguoiSoHuu() != null) {
            model.setNguoiSoHuu(donHangOld.getNguoiSoHuu());
        } else {
            model.setNguoiSoHuu(null);
        }

        if (model.getLoai() == 1 || model.getLoai() == 0) {
            return new DonHangDtoResponse(donHangResponsitory.save(model));
        }

        String subject = "Cập nhật thông tin đơn hàng!";
        String messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn vừa cập nhật thông tin!";
        chiTietDonHangRepository.findAllByDonHang(model).forEach(c -> {
            c.setChiTietSanPham(chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get());
        });
        List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangRepository.findAllByDonHang(model).stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (ChiTietDonHangDtoResponse d : lstSanPham) {
            tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
        }

        Context context = new Context();
        context.setVariable("donHang", new DonHangDtoResponse(model));
        context.setVariable("products", lstSanPham);
        context.setVariable("totalPrice", tongTien);
        context.setVariable("mess", messeger);
        context.setVariable("title", subject);
        context.setVariable("lyDoThayDoi", lyDoThayDoi);
        new Thread(() -> {
            try {
                sendEmailDonHang(model.getEmail(), subject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();

        return new DonHangDtoResponse(donHangResponsitory.save(model));
    } @Override
    public DonHangDtoResponse updateDonHangDoi(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoThayDoi) {
        DonHangModel donHangOld = donHangResponsitory.findById(request.getMa()).orElse(null);
        DonHangModel model = request.mapModel();
        model.setLyDoThayDoi(lyDoThayDoi);
        if (donHangOld.getLoai() == 1) {
            model.setEmail(null);
        }
        model.setLoai(donHangOld.getLoai());
        Boolean phuongThucThanhToan = model.getPhuongThucThanhToan();
        if(donHangOld.getLoai()==0){
            if (phuongThucThanhToan) {
                model.setTrangThai(4);
                System.out.println("ASDASDASDASDASD");
            } else {
                model.setTrangThai(5);
            }
        }else{
            model.setTrangThai(donHangOld.getTrangThai());
        }
//        model.setPhuongThucThanhToan(donHangOld.getPhuongThucThanhToan());

        List<String> maCTSPNew = products.stream().map(c -> c.getId()).collect(Collectors.toList());
        List<ChiTietDonHangModel> ctdhModelOld = chiTietDonHangRepository.findAllByDonHang(model);
        ctdhModelOld.forEach(c -> {
            if (!maCTSPNew.contains(c.getId())) {
                //Thêm lại số lượng khi xóa sản phẩm khỏi đơn hàng
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
                chiTietSanPhamModel.setSoLuong(chiTietSanPhamModel.getSoLuong() + c.getSoLuong());
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);

                chiTietDonHangRepository.deleteById(c.getId());//xóa khỏi đơn hàng
            }
        });

        products.forEach(p -> {
            if (p.getId() != null) {
                ChiTietDonHangModel chiTietDHOld = chiTietDonHangRepository.findById(p.getId()).get();
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(p.getSanPhamCT()).get();
                Long soLuong = chiTietSanPhamModel.getSoLuong() - (p.getSoLuong() - chiTietDHOld.getSoLuong());
                chiTietSanPhamModel.setSoLuong(soLuong);
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);
            } else {
                ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(p.getSanPhamCT()).get();
                Long soLuong = chiTietSanPhamModel.getSoLuong() - p.getSoLuong();
                chiTietSanPhamModel.setSoLuong(soLuong);
                chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);
            }
        });

        products.forEach(p -> chiTietDonHangRepository.saveAndFlush(p.mapModel()));

        if (donHangOld.getVoucher() != null) {
            model.setVoucher(donHangOld.getVoucher());
        }
        if (donHangOld.getNguoiSoHuu() != null) {
            model.setNguoiSoHuu(donHangOld.getNguoiSoHuu());
        } else {
            model.setNguoiSoHuu(null);
        }

        if (model.getLoai() == 1 || model.getLoai() == 0) {
            return new DonHangDtoResponse(donHangResponsitory.save(model));
        }

        String subject = "Cập nhật thông tin đơn hàng!";
        String messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn vừa cập nhật thông tin!";
        chiTietDonHangRepository.findAllByDonHang(model).forEach(c -> {
            c.setChiTietSanPham(chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get());
        });
        List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangRepository.findAllByDonHang(model).stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (ChiTietDonHangDtoResponse d : lstSanPham) {
            tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
        }

        Context context = new Context();
        context.setVariable("donHang", new DonHangDtoResponse(model));
        context.setVariable("products", lstSanPham);
        context.setVariable("totalPrice", tongTien);
        context.setVariable("mess", messeger);
        context.setVariable("title", subject);
        context.setVariable("lyDoThayDoi", lyDoThayDoi);
        new Thread(() -> {
            try {
                sendEmailDonHang(model.getEmail(), subject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();

        return new DonHangDtoResponse(donHangResponsitory.save(model));
    }

    //bản cũ
    @Override
    public void traDonHangUser(String maDonHang, String lyDoTraHang, Boolean phuongThucNhanTien, String ghiChu) throws MessagingException {
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setLyDoTraHang(lyDoTraHang);
        model.setTrangThai(6);
        model.setPhuongThucNhanTien(phuongThucNhanTien);
        model.setGhiChu(ghiChu);
        String subject = "Chờ xác nhận trả hàng!";
        String title = "Đơn hàng đang chờ xác nhận trả hàng";
        String messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đang chờ xác nhận trả hàng. Chúng tôi sẽ xử lý yêu cầu của bạn sỡm nhất có thể.";


        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        if (model.getLoai() == 0) {
            List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }

            Context context = new Context();
            context.setVariable("donHang", new DonHangDtoResponse(model));
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", subject);
            new Thread(() -> {
                try {
                    sendEmailDonHang(model.getEmail(), subject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();

        }
        donHangResponsitory.save(model);
    }
////////////
    @Override
    public void huyTraHangNew(List<String> maDonHang, String lyDoTraHang) throws MessagingException {
        maDonHang.forEach(ma -> {
            List<DonHangTraModel> models = donHangTraRepository.findByMaDonHang(ma);
            models.forEach(model -> {
                model.getDonHang().setLyDoTraHang(lyDoTraHang);
                model.setTrangThai(0);

                String subject = "Từ chối hoàn đơn hàng!";
                String messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ",yêu cầu đơn hàng của bạn đã bị hủy. Cảm ơn bạn đã ghé qua cửa hàng";

                List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model.getDonHang());

                if (model.getDonHang().getLoai() == 0) {
                    List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
                    BigDecimal tongTien = BigDecimal.valueOf(0);
                    for (ChiTietDonHangDtoResponse d : lstSanPham) {
                        tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
                    }
                    Context context = new Context();
                    context.setVariable("products", lstSanPham);
                    context.setVariable("donHangTra", new DonHangTraDTOReponse(model));
                    context.setVariable("totalPrice", tongTien);
                    context.setVariable("mess", messeger);
                    context.setVariable("title", subject);
                    context.setVariable("lyDoTraHang", lyDoTraHang);
                    new Thread(() -> {
                        try {
                            sendEmailDonHang(model.getDonHang().getEmail(), subject, "email/capNhatTrangThaiTra", context, lstSanPham);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                donHangTraRepository.saveAndFlush(model);
            });
        });
    }

    @Override
    public void huyDoiHang(List<String> maDonHang, String lyDoDoiHang) throws MessagingException {
        maDonHang.forEach(ma -> {
            List<DonHangDoiModel> models = donHangDoiRepository.findByMaDonHang(ma);
            models.forEach(model -> {
                model.getDonHang().setLyDoDoiHang(lyDoDoiHang);
                model.setNgayHuy(new Date());
                model.setTrangThai(0);
                model.getDonHang().setTrangThai(4);

                String subject = "Từ chối đổi hàng!";
                String messeger = "Xin chào " + model.getDonHang().getTenNguoiNhan() + ",yêu cầu đơn hàng của bạn đã bị hủy. Cảm ơn bạn đã ghé qua cửa hàng";

                List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model.getDonHang());

                if (model.getDonHang().getLoai() == 0) {
                    List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m -> new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
                    BigDecimal tongTien = BigDecimal.valueOf(0);
                    for (ChiTietDonHangDtoResponse d : lstSanPham) {
                        tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
                    }
                    Context context = new Context();
                    context.setVariable("products", lstSanPham);
                    context.setVariable("donHangDoi", new DonHangDoiDTOReponse(model));
                    context.setVariable("totalPrice", tongTien);
                    context.setVariable("mess", messeger);
                    context.setVariable("title", subject);
                    context.setVariable("lyDoDoiHang", lyDoDoiHang);
                    new Thread(() -> {
                        try {
                            sendEmailDonHang(model.getDonHang().getEmail(), subject, "email/capNhatTrangThaiDoi", context, lstSanPham);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                donHangDoiRepository.saveAndFlush(model);
            });
        });
    }

    @Override
    public DonHangDtoResponse traMotPhan(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products, String lyDoTraHang, Boolean phuongThucNhanTien, String ghiChu) {
        DonHangModel donHangOld = donHangResponsitory.findById(request.getMa()).orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        DonHangModel model = request.mapModel();
        model.setLyDoTraHang(lyDoTraHang);
        model.setNgayXacNhan(donHangOld.getNgayXacNhan());
        model.setNgayDatHang(donHangOld.getNgayDatHang());
        model.setPhiGiaoHang(donHangOld.getPhiGiaoHang());
        model.setNgayGiaoHang(donHangOld.getNgayGiaoHang());
        model.setNgayHoanThanh(donHangOld.getNgayHoanThanh());
        model.setPhuongThucNhanTien(phuongThucNhanTien);
        model.setGhiChu(ghiChu);
        model.setTrangThai(4);

        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        String maDHTra = codeDonHang();
        List<String> maCTSPNew = products.stream().map(c -> c.getId()).collect(Collectors.toList());
        List<ChiTietDonHangModel> ctdhModelOld = chiTietDonHangRepository.findAllByDonHang(model);
        List<DonHangTraModel> donHangTraList = new ArrayList<>();

        products.forEach(p -> {
            // Tạo một đối tượng DonHangTraModel mới trong mỗi vòng lặp
            DonHangTraModel donHangTra = new DonHangTraModel();

            donHangTra.setMaDonHangTra(maDHTra);
            System.out.println("Processing product: " + p);
            donHangTra.setDonHang(p.mapModel().getDonHang());
            donHangTra.setDonGia(p.getDonGia());
            donHangTra.setNgayXacNhan(new Date());
            donHangTra.setChiTietSanPham(p.mapModel().getChiTietSanPham());
            donHangTra.setTrangThai(1);
            donHangTra.setSoLuong(p.getSoLuong());
            donHangTra.setDonGiaSauGiam(p.getDonGiaSauGiam());
            // Thêm đối tượng DonHangTraModel mới vào danh sách
            donHangTraList.add(donHangTra);
        });
        // In ra danh sách DonHangTraModel
        System.out.println("dgr" + donHangTraList);
        // Lưu tất cả các đối tượng DonHangTraModel vào repository
        donHangTraRepository.saveAll(donHangTraList);

        if (donHangOld.getNguoiSoHuu() != null) {
            model.setNguoiSoHuu(donHangOld.getNguoiSoHuu());
        } else {
            model.setNguoiSoHuu(null);
        }
        return new DonHangDtoResponse(donHangResponsitory.save(model));
    }

    @Override
    public DonHangDtoResponse doiMotPhan(DonHangDTORequest request, List<DonHangDoiDTORequest> products, String lyDoDoiHang) {
        DonHangModel donHangOld = donHangResponsitory.findById(request.getMa()).orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        DonHangModel model = request.mapModel();
        model.setLyDoDoiHang(lyDoDoiHang);
        model.setNgayXacNhan(donHangOld.getNgayXacNhan());
        model.setNgayDatHang(donHangOld.getNgayDatHang());
        model.setPhiGiaoHang(donHangOld.getPhiGiaoHang());
        model.setNgayGiaoHang(donHangOld.getNgayGiaoHang());
        model.setNgayHoanThanh(donHangOld.getNgayHoanThanh());
        model.setTrangThai(4);
        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        String maDHDoi = codeDonHang();
        List<ChiTietDonHangModel> ctdhModelOld = chiTietDonHangRepository.findAllByDonHang(model);

        List<DonHangDoiModel> donHangDoiList = products.stream().map(c->{
            System.out.println("ấda"+c.getDonHangID());
            DonHangDoiModel donHangDoiModel = c.mapModel();
            donHangDoiModel.setMaDonHangDoi(maDHDoi);
            donHangDoiModel.setNgayXacNhan(new Date());
            donHangDoiModel.setTrangThai(1);
            donHangDoiModel.setDonHang(model);
            donHangDoiModel.setSoLuong(c.getSoLuong());
            donHangDoiModel.setDonGia(c.getDonGia());
            donHangDoiModel.setDonGiaSauGiam(c.getDonGiaSauGiam());
            donHangDoiModel.setSanPhamDoi(c.getSanPhamDoi());


            return donHangDoiModel;
        }).collect(Collectors.toList());
        donHangDoiRepository.saveAllAndFlush(donHangDoiList);




        // Lưu tất cả các đối tượng DonHangTraModel vào repository
        donHangDoiRepository.saveAll(donHangDoiList);

        if (donHangOld.getNguoiSoHuu() != null) {
            model.setNguoiSoHuu(donHangOld.getNguoiSoHuu());
        } else {
            model.setNguoiSoHuu(null);
        }
        return new DonHangDtoResponse(donHangResponsitory.save(model));
    }

    public void sendEmailDonHang(String email, String subject, String tempalteHtml, Context context, List<ChiTietDonHangDtoResponse> lstSanPham) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        String htmlContent = templateEngine.process(tempalteHtml, context);
        helper.setText(htmlContent, true);

        ClassPathResource resource = new ClassPathResource("./images/product/default.png");
        helper.addInline("logo", resource);

        lstSanPham.forEach(s -> {
            ClassPathResource img = new ClassPathResource("./images/product/" + s.getAnh());
            try {
                helper.addInline(s.getAnh() + "", img);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        javaMailSender.send(mimeMessage);
    }


//bản cũ
    @Override
    public void updateTrangThaiTraHang(String maDonHang, Integer trangThai) throws MessagingException {
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
//        NhanVienModel modelNV = nhanVienRepository.findById(maDonHang).get();
//        String email = "heinikens0408@gmail.com";
        model.setTrangThai(trangThai);

        if (model.getLoai() == 0) {
            String subject = "";
            String messeger = "";
            String title = "";
            if (trangThai == 6) {
                subject = "Xác nhận trả hàng!";
                title = "Xác nhận trả thành công";
                model.setNgayXacNhan(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được xác nhận trả hàng. Cảm ơn bạn đã mua hàng. Bạn có tham khảo một số mẫu giày khác trong cửa hàng";
            } else if (trangThai == 7) {
                subject = "Kiểm tra trả hàng!";
                title = "Kiểm tra trả hàng thành công";
                model.setNgayXacNhan(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được xác nhận kiểm tra trả hàng. Cảm ơn bạn đã mua hàng. Bạn có tham khảo một số mẫu giày khác trong cửa hàng";
            } else if (trangThai == 8) {
                subject = "Hoàn thành trả hàng và hoàn tiền!";
                title = "Xác nhận trả hàng thành công";
                model.setNgayHoanThanh(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã trả hàng thành công. Cảm ơn bạn đã mua hàng. Bạn có tham khảo một số mẫu giày khác trong cửa hàng";
            }
            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(maDonHang);
            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }
            Context context = new Context();
            context.setVariable("donHang", new DonHangDtoResponse(model));
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", title);
            String finalSubject = subject;
            new Thread(() -> {
                try {
                    sendEmailTraHang(model.getEmail(), finalSubject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        donHangResponsitory.saveAndFlush(model);
    }
//
    public void sendEmailTraHang(String email, String subject, String templateHtml, Context context, List<ChiTietDonHangDtoResponse> lstSanPham) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        String htmlContent = templateEngine.process(templateHtml, context);
        helper.setText(htmlContent, true);

        ClassPathResource resource = new ClassPathResource("./images/product/default.png");
        helper.addInline("logo", resource);

        lstSanPham.forEach(s -> {
            ClassPathResource img = new ClassPathResource("./images/product/" + s.getAnh());
            try {
                helper.addInline(s.getAnh() + "", img);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        javaMailSender.send(mimeMessage);
    }

    public void sendEmailDoiHang(String email, String subject, String templateHtml, Context context, List<ChiTietDonHangDtoResponse> lstSanPham) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        String htmlContent = templateEngine.process(templateHtml, context);
        helper.setText(htmlContent, true);

        ClassPathResource resource = new ClassPathResource("./images/product/default.png");
        helper.addInline("logo", resource);

        lstSanPham.forEach(s -> {
            ClassPathResource img = new ClassPathResource("./images/product/" + s.getAnh());
            try {
                helper.addInline(s.getAnh() + "", img);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        javaMailSender.send(mimeMessage);
    }

    @Scheduled(fixedRate = 30000)
    @Override
    public void sendEmailRefundWithHtml() throws MessagingException {
////        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        String email = EmailParameters.getEmail();
        String orderId = EmailParameters.getOrderId();
        List<ChiTietDonHangDtoResponse> lstSanPham = EmailParameters.getLstSanPham();

        if (email == null || orderId == null) {
            System.out.println("Parameters are not set or email sending is disabled. Skipping email sending." + email + orderId);
            return;
        } else {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            helper.setTo(email);
            String subject = "Thông báo hoàn tiền" + orderId;
            helper.setSubject(subject);
            String templeHtml = "email/testNhacNho.html";
            Context context = new Context();
            context.setVariable("orderId", orderId);
            String htmlContent = templateEngine.process(templeHtml, context);
            helper.setText(htmlContent, true);

            ClassPathResource resource = new ClassPathResource("./images/product/default.png");
            helper.addInline("logo", resource);

            lstSanPham.forEach(s -> {
                ClassPathResource img = new ClassPathResource("./images/product/" + s.getAnh());
                try {
                    helper.addInline(s.getAnh() + "", img);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            javaMailSender.send(mimeMessage);
            System.out.println("Email sent successfully to: " + email);
        }


    }

    @Override
    public Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate) {
        return donHangResponsitory.getTotalQauntityInOrdersWithDate(firstDate, lastDate) == null ? 0L : donHangResponsitory.getTotalQauntityInOrdersWithDate(firstDate, lastDate);
    }

    @Override
    public Long getQuantityOrdersWithDate(Date firstDate, Date lastDate) {
        return donHangResponsitory.getQuantityOrdersWithDate(firstDate, lastDate) == null ? 0L : donHangResponsitory.getQuantityOrdersWithDate(firstDate, lastDate);
    }

    @Override
    public BigDecimal getTotalPriceInOrdersWithDate(Date firstDate, Date lastDate) {
        return donHangResponsitory.getTotalPriceInOrdersWithDate(firstDate, lastDate) == null ? BigDecimal.valueOf(0) : donHangResponsitory.getTotalPriceInOrdersWithDate(firstDate, lastDate);
    }

    @Override
    public DonHangDtoResponse updateTrangThai1(String maDonHang, Integer trangThai) {
        DonHangModel donHangModel = donHangResponsitory.findById(maDonHang).get();
        donHangModel.setTrangThai(trangThai);
        return new DonHangDtoResponse(donHangResponsitory.saveAndFlush(donHangModel));
    }

    @Override
    public void themDonHangAdmin(DonHangDTORequest donHangDTORequest, List<ChiTietDonHangDTORequest> chiTietDonHang) {

        if (donHangDTORequest.getLoai() == null) donHangDTORequest.setLoai(0);
        if (donHangDTORequest.getLoai() == 1) {
            donHangDTORequest.setEmail(null);
        }

        DonHangModel model = donHangDTORequest.mapModel();

        if (donHangDTORequest.getLoai() == 0){
            if (model.getPhuongThucThanhToan()) {
                model.setTrangThai(2);
            } else {
                model.setTrangThai(5);
            }
        }else{
            if(model.getPhuongThucThanhToan()){
                model.setTrangThai(donHangDTORequest.getTrangThai());
            }else {
                model.setTrangThai(5);
            }
        }
        model = donHangResponsitory.saveAndFlush(model);


        DonHangModel finalModel = model;
        List<ChiTietDonHangModel> lstCTDHModel = chiTietDonHang.stream().map(c -> {
            ChiTietDonHangModel chiTietDonHangModel = c.mapModel();
            chiTietDonHangModel.setDonHang(finalModel);
            return chiTietDonHangModel;
        }).collect(Collectors.toList());
        chiTietDonHangRepository.saveAllAndFlush(lstCTDHModel);

        lstCTDHModel.forEach(c ->{
            ChiTietSanPhamModel chiTietSanPhamModel = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
            chiTietSanPhamModel.setSoLuong(chiTietSanPhamModel.getSoLuong()-c.getSoLuong());
            chiTietSanPhamRepository.saveAndFlush(chiTietSanPhamModel);
        });

        if (model.getLoai() == 0) {
            String subject = "";
            String messeger = "";
            String title = "";
            if (model.getTrangThai() == 2) {
                subject = "Tạo đơn hàng thành công!";
                title = "Tạo đơn hàng thành công";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được tạo. Cảm ơn bạn đã mua hàng";
            } else if (model.getTrangThai() == 5) {
                subject = "Thanh toán đơn hàng!";
                title = "Đơn hàng của bạn chưa được thanh toán";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn chưa được thanh toán.Vui lòng thanh toán đơn hàng của bạn.";
            }


            DonHangDtoResponse donHangDtoResponse = new DonHangDtoResponse(donHangResponsitory.findById(model.getMa()).get());
            List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangRepository.findAllByDonHang(model).stream().map(m -> {
                m.setChiTietSanPham(chiTietSanPhamRepository.findById(m.getChiTietSanPham().getId()).get());
                ChiTietDonHangDtoResponse chiTietDonHangDtoResponse = new ChiTietDonHangDtoResponse(m);
                return chiTietDonHangDtoResponse;
            }).collect(Collectors.toList());

            BigDecimal tongTien = BigDecimal.valueOf(0);
            for (ChiTietDonHangDtoResponse d : lstSanPham) {
                tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
            }
            Context context = new Context();
            context.setVariable("donHang", donHangDtoResponse);
            context.setVariable("products", lstSanPham);
            context.setVariable("totalPrice", tongTien);
            context.setVariable("mess", messeger);
            context.setVariable("title", title);
            String finalSubject = subject;
            new Thread(() -> {
                try {
                    sendEmailDonHang(finalModel.getEmail(), finalSubject, "email/capNhatTrangThaiDonHang", context, lstSanPham);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
//    @Override
//    public DonHangDtoResponse updateTrangThai1(String maDonHang,Integer trangThai){
//        DonHangModel donHangModel = donHangResponsitory.findById(maDonHang).get();
//        donHangModel.setTrangThai(trangThai);
//        return new DonHangDtoResponse(donHangResponsitory.saveAndFlush(donHangModel));
//    }

    @Override
    public Map<String,Long> getQuantityProductInOrderDetailWithDate(Date firstDate, Date lastDate){
        Long hoaDonOnline = donHangResponsitory.getTotalQauntityInOrdersWithDateAndLoai(firstDate,lastDate,0)
                            == null ? 0L :  donHangResponsitory.getTotalQauntityInOrdersWithDateAndLoai(firstDate,lastDate,0);
        Long hoaDonTaiQuay = donHangResponsitory.getTotalQauntityInOrdersWithDateAndLoai(firstDate,lastDate,1)
                == null ? 0L :  donHangResponsitory.getTotalQauntityInOrdersWithDateAndLoai(firstDate,lastDate,1);
        Map<String,Long> result = new HashMap<>();
        result.put("hoaDonOnline",hoaDonOnline);
        result.put("hoaDonTaiQuay",hoaDonTaiQuay);
        return result;
    }
    @Override
    public  Map<String,Long> getSoLuongSanPhamHoaDon(String maDonHang) {
        Long hoaDon =  donHangResponsitory.getTotalQauntitySum(maDonHang);

        Map<String,Long> result = new HashMap<>();
        result.put("hoaDon",hoaDon);

        return result;
    }
    private String codeDonHang() {
        final String ALLOWED_CHARACTERS = "asdfghjklqwertyuiopzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final int CODE_LENGTH = 8;

        StringBuilder code = new StringBuilder();

        Random random = new Random();
        int maxIndex = ALLOWED_CHARACTERS.length();

        // Sinh ngẫu nhiên các ký tự cho mã
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(maxIndex);
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }
}

