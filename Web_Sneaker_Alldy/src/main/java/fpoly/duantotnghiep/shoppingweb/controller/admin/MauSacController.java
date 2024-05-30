package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import fpoly.duantotnghiep.shoppingweb.service.IMauSacService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("${admin.domain}/mau-sac")
public class MauSacController {

    @GetMapping("")
    public String show() {
        return "/admin/mauSac";
    }
}

