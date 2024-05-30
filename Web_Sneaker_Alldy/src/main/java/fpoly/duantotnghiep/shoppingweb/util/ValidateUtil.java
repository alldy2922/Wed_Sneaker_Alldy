package fpoly.duantotnghiep.shoppingweb.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidateUtil {
    public static ResponseEntity<Map<String,String>> getErrors(BindingResult result){


        Map<String,String> errors = result.getFieldErrors().stream()
                                        .collect(Collectors.toMap(k -> k.getField(),v -> v.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }
}
