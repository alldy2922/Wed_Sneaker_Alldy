package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.impl;

import fpoly.duantotnghiep.shoppingweb.dto.filter.VoucherDTOFiler;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.VoucherEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.VoucherService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository repository;
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @Autowired
    private VoucherEntityManager voucherEntityManager;


    public void deleteVoucherKhachHang(String username, String voucher) {
        VoucherModel voucherModel = repository.findById(voucher).get();
        List<KhachHangModel> lstKH = voucherModel.getKhachHang().stream().filter(k -> !k.getUsername().equals(username)).collect(Collectors.toList());
        voucherModel.setKhachHang(lstKH);
        repository.save(voucherModel);
    }

    @Override
    public List<VoucherReponse> voucherEligible() {
        return repository.findVoucherHienThi().stream()
                .map(c -> new VoucherReponse(c)).collect(Collectors.toList());
    }
////////////////////
    @Override
    public Page<VoucherReponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<VoucherModel> pageModel = repository.findAllVoucher(pageable);
        return pageModel.map(x -> new VoucherReponse(x));
    }

    @Override
    public Page<VoucherReponse> locVC(VoucherDTOFiler voucherDTOFiler, int pageNumber, int pageSize) {
        return voucherEntityManager.filterVoucherEntity(voucherDTOFiler, pageNumber, pageSize);
    }

    @Override
    public List<VoucherReponse> findAll() {
        return repository.findAll().stream().map(x -> new VoucherReponse(x)).collect(Collectors.toList());
    }

    @Override
    public Page<VoucherReponse> findByName(String keyword, Pageable pageable) {
        Page<VoucherModel> page = repository.findByMaLikeAndAndTrangThaiXoa(keyword, 0, pageable);
        return page.map(x -> new VoucherReponse(x));
    }

    @Override
    public VoucherReponse findById(String id) {
        VoucherModel getById = repository.findById(id).get();
        return new VoucherReponse(getById);
    }

    @Override
    public void updateTrangThai(int trangThai, String id) {
        VoucherModel vc = repository.findById(id).get();
        vc.setTrangThai(trangThai);
        repository.save(vc);
        if (vc.getDoiTuongSuDung() == 1 && vc.getKhachHang() != null) {
            if (vc.getTrangThai() == 1) {
                for (var mail : vc.getKhachHang()) {
                    Context context = new Context();
                    context.setVariable("voucher", vc);
                    new Thread(() -> {
                        try {
                            EmailUtil.sendEmailWithHtml(mail.getEmail(), "TẠM DỪNG KÍCH HOẠT VOUCHER", "email/voucherHuy", context);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
            }
            if (vc.getTrangThai() == 0) {
                for (var mail : vc.getKhachHang()) {
                    Context context = new Context();
                    context.setVariable("voucher", vc);
                    new Thread(() -> {
                        try {
                            EmailUtil.sendEmailWithHtml(mail.getEmail(), "DMV Store tặng bạn voucher giảm giá", "email/voucherTang", context);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
            }
        }
    }

    @Override
    public VoucherModel findById1(String id) {
        return repository.findById(id).get();
    }

    @Override
    public VoucherReponse addVoucher(VoucherRequest voucher) {
        if (voucher.getNgayBatDau().after(new Date())) {
            voucher.setTrangThai(1);
        }
        voucher.setTrangXoa(0);
        VoucherModel voucherModel = repository.save(voucher.maptoModel());
        if (voucher.getTrangThai() == 0) {
            if (voucherModel.getKhachHang() != null) {
                for (var mail : voucherModel.getKhachHang()) {
                    Context context = new Context();
                    context.setVariable("voucher", voucher);
                    new Thread(() -> {
                        try {
                            EmailUtil.sendEmailWithHtml(mail.getEmail(), "HYDRA SNEAKER tặng bạn voucher giảm giá", "email/voucherTang", context);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }
        return new VoucherReponse(voucherModel);
    }

    @Override
    public void deleteVoucher(String id) {
        VoucherModel vc = repository.findById(id).get();
        vc.setTrangThaiXoa(1);
        repository.save(vc);
    }

    @Override
    public boolean exitst(String id) {
        boolean exitst = repository.existsById(id);
        return exitst;
    }

    @Override
    public void deleteVouchers(List<String> ids) {
        for (String id : ids) {
            repository.deleteById(id);
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void mailThongBaoHetHan() {
        for (var vc : repository.findAll()) {
            if (vc.getDoiTuongSuDung() == 1 && vc.getTrangThaiXoa() == 0 && vc.getTrangThai() == 0) {
                String ngayBatDauStr = repository.findById(vc.getMa()).get().getNgayKetThuc().toString();  // Thay thế bằng ngày thực tế của bạn

                // Định dạng cho chuỗi ngày
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date ngayBatDau = null;
                Date ngayHienTai = new Date();
                try {
                    ngayBatDau = dateFormat.parse(ngayBatDauStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ngayBatDau);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                ngayBatDau = calendar.getTime();

                calendar.setTime(ngayHienTai);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                ngayHienTai = calendar.getTime();
                // Tính khoảng cách giữa hai ngày
                long khoangCach = (ngayHienTai.getTime() - ngayBatDau.getTime()) / (24 * 60 * 60 * 1000);
                System.out.println("Khoảng cách hết hạn: " + khoangCach);
                // Kiểm tra xem khoảng cách có bằng 1 ngày không
                if (khoangCach == -1) {
                    if (vc.getKhachHang() != null) {
                        for (var mail : vc.getKhachHang()) {
                            Context context = new Context();
                            context.setVariable("voucher", vc);
                            new Thread(() -> {
                                try {
                                    EmailUtil.sendEmailWithHtml(mail.getEmail(), "HYDRA SNEAKER Voucher của bạn sắp hết hạn", "email/voucherTangHetHan", context);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                    }
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void updateKichHoat() {
        if (repository.findAll().size() > 0) {
            for (var vc : repository.findAll()) {
                String ngayBatDauDate = vc.getNgayBatDau().toString();
                String ngayBatDauKT = vc.getNgayKetThuc().toString();
                // Định dạng của chuỗi
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    // Chuyển đổi chuỗi thành đối tượng Date
                    Date dateToCompareBD = sdf.parse(ngayBatDauDate);
                    Date dateToCompareKT = sdf.parse(ngayBatDauKT);
                    // Ngày hiện tại
                    Date currentDate = new Date();

                    // Cắt bớt giây để so sánh
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateToCompareBD);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    dateToCompareBD = calendar.getTime();

                    calendar.setTime(dateToCompareKT);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    dateToCompareKT = calendar.getTime();

                    calendar.setTime(currentDate);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    currentDate = calendar.getTime();

                    if (dateToCompareBD.equals(currentDate)) {
                        VoucherModel voucherUp = repository.findById(vc.getMa()).get();
                        voucherUp.setTrangThai(0);
                        repository.save(voucherUp);
                        if (voucherUp.getKhachHang() != null) {
                            for (var mail : voucherUp.getKhachHang()) {
                                Context context = new Context();
                                context.setVariable("voucher", voucherUp);
                                new Thread(() -> {
                                    try {
                                        EmailUtil.sendEmailWithHtml(mail.getEmail(), "HYDRA SNEAKER tặng bạn voucher giảm giá", "email/voucherTang", context);
                                    } catch (MessagingException e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            }
                        }
                        System.out.println("hếconf");
                    }
                    if (dateToCompareKT.equals(currentDate)) {
                        VoucherModel voucherUp = repository.findById(vc.getMa()).get();
                        voucherUp.setTrangThai(1);
                        repository.save(voucherUp);
                        System.out.println("còn");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public Integer upddateSoLuong(int soLuong, String ma) {
        VoucherModel vcUpdateSl = repository.findById(ma).get();
        vcUpdateSl.setSoLuong(soLuong);
        vcUpdateSl = repository.save(vcUpdateSl);
        return vcUpdateSl.getSoLuong();
    }

    @Override
    public List<KhachHangModel> findByUserNameIn(List<String> maKhachHang) {
        return khachHangRepository.findByUsernameIn(maKhachHang);
    }

}
