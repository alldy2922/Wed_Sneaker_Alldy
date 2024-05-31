package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.filter.VoucherDTOFiler;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("${admin.domain}/voucher")
public class VoucherController {
    @Autowired
    VoucherServiceImpl service;

    @GetMapping("")
    public String hienThi(Model model, @RequestParam(defaultValue = "1", name = "pageNumber", required = false) Integer pageNumber) {
        List<VoucherReponse> listVC;
        int totalPage = 0;
        Page<VoucherReponse> page0 = service.findAll(pageNumber - 1, 5);
        listVC = page0.getContent();
        totalPage = page0.getTotalPages();
        model.addAttribute("voucherDTOFiler", new VoucherDTOFiler());
        model.addAttribute("listVoucher0", listVC);
        model.addAttribute("totalPage0", totalPage);
        return "/admin/Voucher";
    }

    @PostMapping("/loc")
    public String loc(@ModelAttribute("voucherDTOFiler") VoucherDTOFiler voucherDTOFiler,
                      @RequestParam(defaultValue = "1", name = "pageNumber", required = false) Integer pageNumber,
                      Model model) throws ParseException {
        Page<VoucherReponse> page = service.locVC(voucherDTOFiler, pageNumber - 1, 8);
        List<VoucherReponse> listVC = page.getContent();
        int totalPage = page.getTotalPages();
        model.addAttribute("listVoucher0", listVC);
        model.addAttribute("totalPage0", totalPage);
        return "/admin/Voucher";
    }

    @GetMapping("/chi-tiet-voucher/{id}")
    public String chiTietVoucher() {
        return "/admin/fromVoucherU";
    }

    @GetMapping("/them-voucher")
    public String danhSa3() {
        return "/admin/fromVoucher";
    }
}
