package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanXetDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanXetRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanXetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component

public class NhanXetServiceImpl implements INhanXetService{


    @Autowired
    private INhanXetRepository nhanXetRepository;

    @Override
    public Page<NhanXetDtoResponse> getNhanXetBySanPhamAndPheDuyet(Integer page, Integer limit, String maSP, Boolean pheDuyet){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMaAndPheDuyet(maSP, pheDuyet, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public Page<NhanXetDtoResponse> getNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate, Boolean pheDuyet){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMaAndRateAndPheDuyet(maSP, rate, pheDuyet, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public NhanXetDtoResponse add(NhanXetDtoRequest nhanXetDtoRequest){
        nhanXetDtoRequest.setPheDuyet(false);
        NhanXetModel nhanXetModel = nhanXetDtoRequest.mapToModel();
        nhanXetModel.setChinhSua(false);

        nhanXetModel.setThoiGian(new Date());
        nhanXetRepository.save(nhanXetModel);
        return new NhanXetDtoResponse();
//        return new NhanXetDtoResponse(nhanXetRepository.save(nhanXetModel));
//        model.setThoiGian(new Date());
    }
    @Override
    public void update(NhanXetDtoRequest nhanXetDtoRequest){
        nhanXetDtoRequest.setPheDuyet(false);
        NhanXetModel nhanXetModel = nhanXetDtoRequest.mapToModel();
        nhanXetModel.setChinhSua(true);
        nhanXetModel.setThoiGian(new Date());
        nhanXetRepository.save(nhanXetModel);

//        model.setThoiGian(new Date());
    }

    @Override
    public Float getAvgRatingBySanPhamAndPheDuyet(String maSP, Boolean pheDuyet){
        return nhanXetRepository.getAvgRatingBySanPhamAndPheDuyet(maSP,pheDuyet);
    }

    @Override
    public Page<NhanXetDtoResponse> getNhanXetBySanPham(Integer page, Integer limit, String maSP){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMa(maSP, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public Page<NhanXetDtoResponse> getAllNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMaAndRate(maSP, rate, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public Float getAvgRatingBySanPham(String maSP){
        return nhanXetRepository.getAvgRatingBySanPham(maSP);
    }

    @Override
    public Integer pheDuyetNhanXet(Boolean pheDuyet, String id){
        return nhanXetRepository.pheDuyetNhanXet(pheDuyet,id);
    }

    @Override
    public Boolean existsById(String id){
        return nhanXetRepository.existsById(id);
    }
    @Override
    public NhanXetDtoResponse getByid(String id){
        return new NhanXetDtoResponse(nhanXetRepository.findById(id).orElse(new NhanXetModel()));
    }
}
