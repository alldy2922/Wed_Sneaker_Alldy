package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class exportPdf {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("export/don-hang/{ma}")
    public void pdfDonHang(@PathVariable("ma")String ma,
                           HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        DonHangReponseUser donHangReponseUser = donHangService.findByMaUser(ma);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=DonHang_" + donHangReponseUser.getMa() + ".pdf";
        response.setHeader(headerKey, headerValue);


        PdfService exporter = new PdfService(donHangReponseUser);
        exporter.export(response);

    }
}
