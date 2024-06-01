package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("${admin.domain}/voucher")
@CrossOrigin(origins = "*")
public class VoucherRestController {
    @Autowired
    VoucherServiceImpl service;

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestPart("voucher") VoucherRequest voucherRequest, BindingResult result,
                                  @RequestPart(value = "idKhach", required = false) List<String> idKhachHang) {

        if (idKhachHang != null) {
            List<KhachHangModel> khachHang = service.findByUserNameIn(idKhachHang);
            voucherRequest.setKhachHang(khachHang);
        }

        voucherRequest.setMa(codeVoucher());
        validateNhap(result, voucherRequest);
        if (result.hasErrors()) {
            return ValidateUtil.getErrors(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Oke", "Thêm thành công", service.addVoucher(voucherRequest))
        );
    }

    @PutMapping("/cap-nhat-trang-thai/{id}")
    public ResponseEntity<?> capNhatTrangThai(@RequestBody Integer trangThai,
                                              @PathVariable String id) {
//        VoucherModel vc = service.findById1(id);
//        if (vc.getNgayBatDau().after(new Date())) {
//            result.rejectValue("trangThai", "", "Không thể kích hoạt voucher khi đơn hàng chưa đến hạn");
//        } else if(vc.getNgayKetThuc().after(new Date())){
//            result.rejectValue("trangThai", "", "Không thể kích hoạt voucher khi đơn hàng chưa đến hạn");
//        }
        service.updateTrangThai(trangThai, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/xoa-voucher/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") String id) {
        boolean exitst = service.exitst(id);
        if (exitst) {
            service.deleteVoucher(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Oke", "Xóa thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bài", "")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody VoucherRequest voucherRequest,
                                                 BindingResult result,
                                                 @PathVariable("id") String id) {

        boolean exitst = service.exitst(id);
        validateNhap(result, voucherRequest);
        if (exitst) {
            if (voucherRequest.getDoiTuongSuDung() == 0) {
                voucherRequest.setMa(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Oke", "Sửa thành công", service.addVoucher(voucherRequest))
                );
            } else {
                return ResponseEntity.status(HttpStatus.FOUND).body(
                        new ResponseObject("Found", "Không the update", "")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("Found", "Không tìm thấy", "")
        );
    }

    @PutMapping("/id")
    public ResponseEntity<?> updateTrangThai(@RequestBody Integer trangThai, @PathVariable String id) {
        service.updateTrangThai(trangThai, id);
        return ResponseEntity.ok().build();
    }

    private static String codeVoucher() {
        final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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

    private void validateNhap(BindingResult result, VoucherRequest voucherRequest) {


        if (voucherRequest.getDoiTuongSuDung() == 0) {
            if (voucherRequest.getSoLuong() == 0) {
                result.rejectValue("soLuong", "erSoLuong", "Vui lòng nhập dữ liệu");
            }
        } else if (voucherRequest.getDoiTuongSuDung() == 1) {
            if (voucherRequest.getKhachHang().size() == 0) {
                result.rejectValue("khachHang", "erKhachHang", "Vui lòng chọn khách hàng");
            }
        } else {
            result.rejectValue("doiTuongSuDung", "erDoiTuongSuDung", "Vui lòng chọn đối tượng sử dụng");

        }
        if (voucherRequest.getLoaiMucGiam() == null) {
            result.rejectValue("loaiMucGiam", "erDoiTuongSuDung", "Vui lòng chọn loại mức giảm");

        }
//        Mức giảm
        if (voucherRequest.getMucGiam() == null) {
            result.rejectValue("mucGiam", "erMucGiam", "Vui lòng nhập dữ liệu");

        } else {
//        Validate loại mức giảm Tien
            if (voucherRequest.getLoaiMucGiam().equals("TIEN")) {
                if (voucherRequest.getMucGiam() != null && voucherRequest.getGiaTriDonHang() != null) {
                    if (voucherRequest.getGiaTriDonHang() <= voucherRequest.getMucGiam()) {
                        result.rejectValue("mucGiam", "erMucGiam", "Mức giảm vướt quá giá trị đơn hàng");
                    }
                }

                if (voucherRequest.getMucGiam() < 1000) {
                    result.rejectValue("mucGiam", "erMucGiam", "Mức giảm phải lớn hơn 10.0000 đ");
                }
            }
////        Validate loại mức giảm %
            if (voucherRequest.getLoaiMucGiam().equals("PHAN TRAM")) {
                if (voucherRequest.getMucGiamToiDa() == null) {
                    result.rejectValue("mucGiamToiDa", "erMucGiamToiDa", "Vui lòng nhập dữ liệu");
                } else if (voucherRequest.getMucGiamToiDa() <= 10000) {
                    result.rejectValue("mucGiamToiDa", "erMucGiamToiDa", "Mức giảm tối đa phải lớn hơn 10.000 đ");
                }

                if (voucherRequest.getMucGiam() < 1 || voucherRequest.getMucGiam() >= 99) {
                    result.rejectValue("mucGiam", "erMucGiam", "Mức giảm phải trong khoảng 1-99");
                }
            }
        }
        if (voucherRequest.getNgayBatDau() == null) {
            result.rejectValue("ngayBatDau", "", "Vui lòng nhập dữ liệu");
        }
        if (voucherRequest.getNgayKetThuc() == null) {
            result.rejectValue("ngayKetThuc", "", "Vui lòng nhập dữ liệu");

        }
        if (voucherRequest.getNgayBatDau() != null && voucherRequest.getNgayKetThuc() != null) {
            if (voucherRequest.getNgayBatDau().after(voucherRequest.getNgayKetThuc())) {
                result.rejectValue("ngayBatDau", "", "Ngày bắt đầu và kết thúc không hợp lệ");
                result.rejectValue("ngayKetThuc", "", "Ngày bắt đầu và kết thúc không hợp lệ");
//
            } else {
                if (voucherRequest.getNgayKetThuc().before(new Date())) {
                    result.rejectValue("ngayKetThuc", "", "Ngày kết thúc không hợp lệ, phải lớn hơn ngày hiện tại");
                }
            }
        }
    }
}

