package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NhanXetDtoRequest {
    private String id;
    private String chiTietDonHang;
    @NotNull(message = "Có thể cho chúng tôi mức độ hài lòng của bạn")
    private Float rating;
    @Size(max = 50, message = "Tiêu đề tối đa 50 ký tự")
    private String tieuDe;
    @Size(max = 200,message = "Nội dung tối đa 200 ký tự")
    private String noiDung;
    private Date thoiGian;
    private Boolean pheDuyet;

    public NhanXetModel mapToModel(){
        NhanXetModel model = new NhanXetModel();
        if(id!=null) model.setId(this.id);

        ChiTietDonHangModel chiTietDonHangModel = new ChiTietDonHangModel();
        chiTietDonHangModel.setId(chiTietDonHang);
        model.setChiTietDonHangModel(chiTietDonHangModel);

        model.setRating(this.rating);
        model.setTieuDe(this.tieuDe);
        model.setNoiDung(this.noiDung);
        model.setThoiGian(this.thoiGian);

        if(pheDuyet != null) model.setPheDuyet(pheDuyet);

        return model;
    }
}
