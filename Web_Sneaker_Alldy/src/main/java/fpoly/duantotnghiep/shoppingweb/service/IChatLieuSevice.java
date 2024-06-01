package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChatLieuDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChatLieuDTORequest;

import java.util.List;

public interface IChatLieuSevice {
    List<ChatLieuDTOResponse> findAll();

    ChatLieuDTOResponse save(ChatLieuDTORequest chatLieuDTORequest);

    ChatLieuDTOResponse findById(String s);

    boolean existsById(String s);

    void deleteById(String s);
}
