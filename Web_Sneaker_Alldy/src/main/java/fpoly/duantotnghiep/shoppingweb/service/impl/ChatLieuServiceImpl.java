package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChatLieuDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChatLieuDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.ChatLieuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChatLieuRepository;
<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ChatLieuSeviceImpl.java
import fpoly.duantotnghiep.shoppingweb.service.security.IChatLieuSevice;
=======
import fpoly.duantotnghiep.shoppingweb.service.IChatLieuService;
>>>>>>> 1a545ceac75526f8d35bf8ab7db4358db035cfc5:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ChatLieuServiceImpl.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
<<<<<<< HEAD:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ChatLieuSeviceImpl.java
public class ChatLieuSeviceImpl implements IChatLieuSevice {
=======
public class ChatLieuServiceImpl implements IChatLieuService {
>>>>>>> 1a545ceac75526f8d35bf8ab7db4358db035cfc5:Web_Sneaker_Alldy/src/main/java/fpoly/duantotnghiep/shoppingweb/service/impl/ChatLieuServiceImpl.java
    @Autowired
    private IChatLieuRepository chatLieuRepository;

    public List<ChatLieuDTOResponse> findAll() {
        return chatLieuRepository.findAll().stream()
                .map(m -> new ChatLieuDTOResponse(m))
                .collect(Collectors.toList());
    }

    public ChatLieuDTOResponse save(ChatLieuDTORequest chatLieuDTORequest) {
        ChatLieuModel model = chatLieuRepository.save(chatLieuDTORequest.mapToModel());
        return new ChatLieuDTOResponse(model);
    }

    public ChatLieuDTOResponse findById(String s) {
        ChatLieuModel model = chatLieuRepository.findById(s).get();
        return new ChatLieuDTOResponse(model);
    }

    public boolean existsById(String s) {
        return chatLieuRepository.existsById(s);
    }

    public void deleteById(String s) {
        chatLieuRepository.deleteById(s);
    }

}
