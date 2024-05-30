package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;

import java.util.List;

public interface IChiTietDonHangService {
    List<ChiTietDonHangDtoResponse> getByDonHang(String maDonHang);

    ChiTietDonHangDtoResponse findById(String id);
    void save(ChiTietDonHangDTORequest chiTietDonHang);
}
