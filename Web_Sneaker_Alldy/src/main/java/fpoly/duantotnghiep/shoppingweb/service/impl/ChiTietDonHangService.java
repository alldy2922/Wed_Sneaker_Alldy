package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietDonHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChiTietDonHangService implements IChiTietDonHangService {
    @Autowired
    private IChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public List<ChiTietDonHangDtoResponse> getByDonHang(String maDonHang) {
        DonHangModel donHangModel = new DonHangModel();
        donHangModel.setMa(maDonHang);
        return chiTietDonHangRepository.findAllByDonHang(donHangModel).stream().map(d -> new ChiTietDonHangDtoResponse(d)).collect(Collectors.toList());
    }

    @Override
    public ChiTietDonHangDtoResponse findById(String id) {
        return new ChiTietDonHangDtoResponse(chiTietDonHangRepository.findById(id).orElse(new ChiTietDonHangModel()));
    }
    @Override
    public void save(ChiTietDonHangDTORequest chiTietDonHang) {
        chiTietDonHangRepository.save(chiTietDonHang.mapModel());
    }
}
