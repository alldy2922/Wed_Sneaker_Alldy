package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietDonHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, limit);

        Page<DonHangModel> pageModel = donHangResponsitory.getAllByTrangThai(trangThai, pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());
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
            }
             else if (trangThai == 6) {
                subject = "Chờ xàc nhận trả hàng!";
                title = "Đơn hàng đang chờ xác nhận trả hàng";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đang chờ xác nhận trả hàng. Chúng tôi sẽ xử lý yêu cầu của bạn sỡm nhất có thể.";
            } else if (trangThai == 7) {
                subject = "Kiểm tra hoàn hàng!";
                title = "Đơn hàng đang được kiểm tra hoàn hàng";
                model.setNgayKiemTraTraHang(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đang được kiểm tra hoàn hàng. Chúng tôi sẽ thông báo cho bạn khi quá trình kiểm tra hoàn tất.";
            } else if (trangThai == 8) {
                subject = "Hoàn tiền!";
                title = "Đơn hàng đã được hoàn tiền";
                 model.setNgayHoanThanhTraHang(new Date());
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được hoàn tiền. Số tiền hoàn lại sẽ sớm có trong tài khoản của bạn.";
            }
            else if (trangThai == 9) {
                subject = "Từ chối Hoàn tiền!";
                title = "Tư Chối hoàn tiền";
                messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã không được hoàn tiền.";
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
            model.setTrangThai(9);
            model.setNgayHuyTraHang(new Date());

            String subject = "Từ chối hoàn đơn hàng!";
            String messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã hủy. Cảm ơn bạn đã ghé qua cửa hàng";

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
                context.setVariable("lyDoTraHang", lyDoTraHang);
            }

            donHangResponsitory.saveAndFlush(model);
        });
    }

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

    @Override
    public void traDonHangUser(String maDonHang, String lyDoTraHang) throws MessagingException {
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setNgayTraHang(new Date());
        model.setLyDoTraHang(lyDoTraHang);
        model.setTrangThai(6);
        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        ctdhModel.forEach(c -> {
            int soLuongInDonHang = c.getSoLuong();
            ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
            sanPhamInDonHang.setSoLuong(soLuongInDonHang + sanPhamInDonHang.getSoLuong());
            chiTietSanPhamRepository.save(sanPhamInDonHang);
        });
        donHangResponsitory.save(model);
    }

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

//        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
//        ctdhModel.forEach(c -> {
//            int soLuongInDonHang = c.getSoLuong();
//            ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
//            sanPhamInDonHang.setSoLuong(soLuongInDonHang+sanPhamInDonHang.getSoLuong());
//            chiTietSanPhamRepository.save(sanPhamInDonHang);
//        });


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
}

