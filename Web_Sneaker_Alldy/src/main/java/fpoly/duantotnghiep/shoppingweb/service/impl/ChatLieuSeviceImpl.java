package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.ChatLieuModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ChatLieuSeviceImpl implements IChatLieuService {
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
