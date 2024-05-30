package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.Token;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class TokenServiceImpl {

    private Map<String, Token> tokens = new HashMap<>();

    public void saveToken(String username,Token token){
            tokens.put(username,token);
    }
    public Token getToken(String username){
        if(!tokens.containsKey(username)) return null;
        return tokens.get(username);
    }

    public void removeToken(){
        tokens.clear();
    }

}
