package fpoly.duantotnghiep.shoppingweb.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangReponseUser;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.util.RemoveAccent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor
public class PdfService {
    private DonHangReponseUser donHangReponseUser;

    public PdfService(DonHangReponseUser donHangReponseUser) {
        this.donHangReponseUser = donHangReponseUser;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.white);
        cell.setPadding(7);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("STT", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("San pham", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Size", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("So luong", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Don gia", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Don gia sau giam", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Thanh tien", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        NumberFormat numberFM = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        Integer i = 1;
        for (ChiTietDonHangDtoResponse d : donHangReponseUser.getChiTietDonHang()) {
            table.addCell(i+"");
            table.addCell(RemoveAccent.removeAccent(d.getSanPham()));
            table.addCell(d.getSize()+"");
            table.addCell(d.getSoLuong()+"");
            table.addCell((numberFM.format(d.getDonGia()))+" đ");
            table.addCell((numberFM.format(d.getDonGiaSauGiam()))+" đ");
            table.addCell((numberFM.format(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong()))))+" đ");
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        NumberFormat numberFM = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph title1 = new Paragraph("HOA DON THANH TOAN", new Font(Font.HELVETICA, 25, Font.BOLD));
        title1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title2 = new Paragraph("\n ALLDY SNEAKER", new Font(Font.HELVETICA, 20, Font.BOLD));
        title2.setAlignment(Element.ALIGN_CENTER);
        Paragraph title3 = new Paragraph("So dien thoai: 0388700177"+
                "\nEmail: alldystoresneaker@gmail.com" +
                "\nDia chi: So 136 Cầu Diễn, Ha Noi\n------------------------------------------",
                new Font(Font.HELVETICA, 13));
        title3.setAlignment(Element.ALIGN_CENTER);
        Image image1 = Image.getInstance("src/main/resources/static/admin/images/logo.jpg");
        image1.setAlignment(Element.ALIGN_CENTER);
        image1.scaleAbsolute(100, 60);

        Paragraph thongtinHD = new Paragraph("\n\nNhan Vien: " + (donHangReponseUser.getNhanVienDtoResponse() == null ? "Khong xac dinh" : donHangReponseUser.getNhanVienDtoResponse().getUsername())
                + " - " +( donHangReponseUser.getNhanVienDtoResponse() == null ? "" : RemoveAccent.removeAccent(donHangReponseUser.getNhanVienDtoResponse().getHoVaTen()))
                + "\nKhach Hang: " + RemoveAccent.removeAccent(donHangReponseUser.getTenNguoiNhan()) + "\nNgay mua: " + simpleDateFormat.format(donHangReponseUser.getNgayDatHang())
                + "\nHinh thuc thanh toan: " + (donHangReponseUser.getPhuongThucThanhToan()=="Thanh toán khi nhận hàng"?"Tien mat":"VN PAY")
                + "\n\n------------------------------------------------------------------------------------------------------------------------",
                new Font(Font.HELVETICA, 13));
        title3.setAlignment(Element.ALIGN_CENTER);

        Paragraph titleTable = new Paragraph("\nDanh Sach San Pham\n\n", new Font(Font.HELVETICA, 15, Font.BOLD));
        titleTable.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 3f, 1f, 1f, 1.5f,1.5f,1.5f});
        table.setSpacingBefore(0);

        writeTableHeader(table);
        writeTableData(table);

        Paragraph thongtinTT = new Paragraph("\n\n------------------------------------------------------------------------------------------------------------------------"
                + "\nTong tien: " + numberFM.format(donHangReponseUser.getTongTien())+"đ" ,new Font(Font.HELVETICA, 13));
        thongtinTT.setAlignment(Element.ALIGN_RIGHT);
        Paragraph boder = new Paragraph("\n------------------------------------------\n"
                + "ALDDY SNEAKER CAM ON VA HEN GAP LAI KHACH HANG");
        boder.setAlignment(Element.ALIGN_CENTER);

        document.add(title1);
        document.add(title2);
        document.add(image1);
        document.add(title3);
        document.add(thongtinHD);
        document.add(titleTable);
        document.add(table);
        document.add(thongtinTT);
//            document.add(tableTT);
        document.add(boder);
        document.close();

    }
}
