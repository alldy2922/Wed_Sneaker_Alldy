package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.filter.KhuyenMaiDTOFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.KhuyenMaiRepository;
import fpoly.duantotnghiep.shoppingweb.service.impl.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${admin.domain}/khuyen-mai")
public class KhuyenMaiController {
    @Autowired
    ISanPhamRepository repository;
    @Autowired
    SanPhamServiceImpl sanPhamService;
    @Autowired
    KhuyenMaiServiceImpl khuyenMaiService;
    @Autowired
    XuatXuServiceImpl xuatXuService;
    @Autowired
    DongSanPhamServiceImpl dongSanPhamService;
    @Autowired
    ThuongHieuService thuongHieuService;
    @Autowired
    MauSacServiceImpl mauSacService;
    @Autowired
    KhuyenMaiRepository kmTs;


    @GetMapping("")
    public String hienThi(Model model,
                          @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber) {

        Page<KhuyenMaiResponse> page = khuyenMaiService.findAll(pageNumber - 1, 8);

        List<KhuyenMaiResponse> list = page.getContent();

        model.addAttribute("khuyenMai", list);
        model.addAttribute("khuyenMaiDTOFilter", new KhuyenMaiDTOFilter());

        model.addAttribute("totalPage", page.getTotalPages());

        return "/admin/khuyenMai";
    }

    @GetMapping("/form-add")
    public String form(Model model) {
        getFormAdd(model, new KhuyenMaiRequest());
        return "/admin/formKhuyenMai";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                      BindingResult result,
                      Model model,
                      @RequestParam(value = "ids", required = false) List<String> sanPham) {

        validateNhap(result, khuyenMaiRequest);
        if (sanPham != null && khuyenMaiRequest.getMucGiam() != null) {
            List<SanPhamModel> sanPhamModel = repository.findByMaIn(sanPham);
            sanPhamModel.forEach(x -> {
                if (khuyenMaiRequest.getMucGiam().compareTo(x.getGiaBan()) > 0) {
                    result.rejectValue("mucGiam", "mucGiamErro", "Mức giảm không thể áp dụng cho sản phẩm " + x.getMa() + " - " + x.getTen());

                }
            });
            khuyenMaiRequest.setSanPham(sanPhamModel);
        }
        if (result.hasErrors()) {
            getFormAdd(model, khuyenMaiRequest);
            return "/admin/formKhuyenMai";
        }

        if (khuyenMaiRequest.getNgayBatDau().equals(new Date()) || khuyenMaiRequest.getNgayBatDau().before(new Date())) {
            khuyenMaiRequest.setTrangThai(0);
        } else if (khuyenMaiRequest.getNgayBatDau().after(new Date())) {
            khuyenMaiRequest.setTrangThai(1);
        }
        khuyenMaiRequest.setMa(code());


        khuyenMaiService.save(khuyenMaiRequest);

        return "redirect:/admin/khuyen-mai";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) {

        List<String> sanPhamOn = khuyenMaiService.findById(id).getSanPham().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        List<String> sanPhamKhuyenMai = sanPhamService.findByAllSanPhamWithKM().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        model.addAttribute("khuyenMai", khuyenMaiService.findById(id));
        model.addAttribute("id", khuyenMaiService.findById(id).getMa());
        model.addAttribute("ct", "aaa");
        model.addAttribute("tt", khuyenMaiService.findById(id).getTrangThai());
        int dis = 1;
        if (khuyenMaiService.findById(id).getNgayBatDau().after(new Date()) ||
                khuyenMaiService.findById(id).getNgayKetThuc().before(new Date())) {
            dis = 0;
        }
        model.addAttribute("dis", dis);

        model.addAttribute("sanPham", sanPhamService.findAll());

        model.addAttribute("action", "/admin/khuyen-mai/update/" + id);

        model.addAttribute("sanPhamOn", sanPhamOn);

        model.addAttribute("sanPhamKhuyenMai",
                listSanPhamKhuyenMaiById(sanPhamKhuyenMai, sanPhamOn));

        return "/admin/formKhuyenMai";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid
                         @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                         BindingResult result,
                         Model model,
                         @PathVariable String id,
                         @RequestParam(value = "ids", required = false) List<String> sanPham) {
        validateNhap(result, khuyenMaiRequest);
        if (sanPham != null && khuyenMaiRequest.getMucGiam() != null) {
            List<SanPhamModel> sanPhamModel = repository.findByMaIn(sanPham);
            sanPhamModel.forEach(x -> {
                if (khuyenMaiRequest.getMucGiam().compareTo(x.getGiaBan()) > 0) {
                    result.rejectValue("mucGiam", "mucGiamErro", "Mức giảm không thể áp dụng cho một số sản phẩm");

                }
            });
            khuyenMaiRequest.setSanPham(sanPhamModel);
        }
        if (khuyenMaiRequest.getNgayBatDau().equals(new Date()) || khuyenMaiRequest.getNgayBatDau().before(new Date())) {
            khuyenMaiRequest.setTrangThai(0);
        } else if (khuyenMaiRequest.getNgayBatDau().after(new Date())) {
            khuyenMaiRequest.setTrangThai(1);
        }
        KhuyenMaiResponse km = khuyenMaiService.findById(id);
        khuyenMaiRequest.setTrangThai(km.getTrangThai());
        if (result.hasErrors()) {
            getFormUpdate(model, khuyenMaiRequest, id);
            return "/admin/formKhuyenMai";
        }
        khuyenMaiRequest.setMa(id);

        khuyenMaiService.save(khuyenMaiRequest);

        return "redirect:/admin/khuyen-mai";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {

        khuyenMaiService.delete(id);

        return "redirect:/admin/khuyen-mai";
    }

    @PostMapping("/loc-khuyen-mai")
    public String loc(@ModelAttribute("khuyenMaiDTOFilter") KhuyenMaiDTOFilter khuyenMaiDTOFilter,
                      @RequestParam(defaultValue = "1", name = "pageNumber", required = false) Integer pageNumber,
                      Model model) {
        Page<KhuyenMaiResponse> page = khuyenMaiService.locKM(khuyenMaiDTOFilter, pageNumber - 1, 8);
        List<KhuyenMaiResponse> list = page.getContent();
        model.addAttribute("khuyenMai", list);
        model.addAttribute("totalPage", page.getTotalPages());
        return "/admin/khuyenMai";
    }

    @GetMapping("/kich-hoat/{id}")
    public String capKichHoat(
            @PathVariable("id") String id) {
        KhuyenMaiModel khuyenMaiModel = khuyenMaiService.findById1(id);
        khuyenMaiModel.setTrangThai(0);
        khuyenMaiService.capNhatTrangThai(khuyenMaiModel);
        return "redirect:/admin/khuyen-mai/" + id;
    }

    @GetMapping("/dung-kich-hoat/{id}")
    public String capDungKichHoat(
            @PathVariable("id") String id) {
        KhuyenMaiModel khuyenMaiModel = khuyenMaiService.findById1(id);
        khuyenMaiModel.setTrangThai(1);
        khuyenMaiService.capNhatTrangThai(khuyenMaiModel);
        return "redirect:/admin/khuyen-mai/" + id;
    }

    private static String code() {
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

    private static List<String> listSanPhamKhuyenMaiById(List<String> list1, List<String> list2) {
        List<String> list = new ArrayList<>();
        HashSet<String> hashSet2 = new HashSet<>(list2);
        for (String element : list1) {
            if (!hashSet2.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    private static BigDecimal giaGiam(String loai, SanPhamModel sanPham, KhuyenMaiResponse khuyenMai) {
        Date ngayHienTai = new Date();
        BigDecimal giaGiam = null;

        if (khuyenMai.getNgayBatDau().after(ngayHienTai)) {
            giaGiam = sanPham.getGiaNiemYet();
        } else {
            if (loai.equals("TIEN")) {
                giaGiam = (sanPham.getGiaNiemYet().subtract(khuyenMai.getMucGiam()));
            } else {
                giaGiam = sanPham.getGiaNiemYet().subtract(
                        sanPham.getGiaNiemYet().multiply(khuyenMai.getMucGiam().divide(new BigDecimal("100"))));
            }
        }
        return giaGiam;
    }

    private void updateGiaKhiGiam(KhuyenMaiResponse khuyenMai, List<SanPhamModel> sanPhamUpdate) {
        for (SanPhamModel sanPhamModel1 : sanPhamUpdate) {
            BigDecimal giaGiam = giaGiam(khuyenMai.getLoai(), sanPhamModel1, khuyenMai);
            sanPhamModel1.setGiaBan(giaGiam);
            sanPhamModel1.setMa(sanPhamModel1.getMa());
            sanPhamService.save1(sanPhamModel1);
        }
    }

    private void getFormAdd(Model model, KhuyenMaiRequest khuyenMaiRequest) {

        model.addAttribute("khuyenMai", khuyenMaiRequest);

        model.addAttribute("sanPham", sanPhamService.findAll());

        model.addAttribute("sanPhamKhuyenMai", sanPhamService.findByAllSanPhamWithKM().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList()));

        model.addAttribute("action", "/admin/khuyen-mai/add");

        model.addAttribute("sanPhamOn", new ArrayList<>());
    }

    private void getFormUpdate(Model model, KhuyenMaiRequest khuyenMaiRequest, String id) {

        model.addAttribute("khuyenMai", khuyenMaiRequest);

        List<String> sanPhamOn = khuyenMaiService.findById(id).getSanPham().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        List<String> sanPhamKhuyenMai = sanPhamService.findByAllSanPhamWithKM().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        model.addAttribute("id", khuyenMaiService.findById(id).getMa());
        model.addAttribute("ct", "aaa");
        model.addAttribute("tt", khuyenMaiService.findById(id).getTrangThai());
        int dis = 0;
        if (khuyenMaiService.findById(id).getNgayBatDau().after(new Date()) ||
                khuyenMaiService.findById(id).getNgayKetThuc().before(new Date())) {
            dis = 0;
        }
        model.addAttribute("dis", dis);
        System.out.println(dis + "dddd");

        model.addAttribute("sanPham", sanPhamService.findAll());

        model.addAttribute("action", "/admin/khuyen-mai/update/" + id);

        model.addAttribute("sanPhamOn", sanPhamOn);

        model.addAttribute("sanPhamKhuyenMai",
                listSanPhamKhuyenMaiById(sanPhamKhuyenMai, sanPhamOn));
    }

    private void getThuocTinhSanPham(Model model) {
        model.addAttribute("dongSP", dongSanPhamService.findAll());
        model.addAttribute("mauSac", mauSacService.findAll());
        model.addAttribute("xuatXu", xuatXuService.findAll());
        model.addAttribute("thuongHieu", thuongHieuService.findAll());
    }

    private static void validateNhap(BindingResult result, KhuyenMaiRequest khuyenMaiRequest) {
        if (khuyenMaiRequest.getLoai().equals("TIEN")) {
            if (khuyenMaiRequest.getMucGiam() != null) {
                if (khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("1000")) < 0) {
                    result.rejectValue("mucGiam", "mucGiamErro", "Mức giảm phải lớn hơn 1000");
                }
            }
        } else if (khuyenMaiRequest.getLoai().equals("PHAN TRAM")) {
            if (khuyenMaiRequest.getMucGiam() != null) {
                if (khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("1")) < 0 || khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("99")) > 0) {
                    result.rejectValue("mucGiam", "mucGiamErro", "Mức giảm phải trong khoảng 1-99");
                }
            }
        }
        if (khuyenMaiRequest.getNgayBatDau() != null && khuyenMaiRequest.getNgayKetThuc() != null) {

            if (khuyenMaiRequest.getNgayBatDau().after(khuyenMaiRequest.getNgayKetThuc())) {
                result.rejectValue("ngayBatDau", "", "Ngày bắt đầu lớn hơn ngày kết ");
            }
//            else if (khuyenMaiRequest.getNgayBatDau().before(new Date())) {
//                result.rejectValue("ngayBatDau", "", "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại ");
//            }
        }
    }
}
