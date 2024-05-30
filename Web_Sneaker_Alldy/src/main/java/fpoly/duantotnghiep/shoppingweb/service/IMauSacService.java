package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;

import java.util.List;

public interface IMauSacService {
   List<MauSacDTOResponse> findAll();
    public MauSacDTOResponse  save( MauSacDTORequest mauSacDTORequest);
    public MauSacDTOResponse findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);
}
