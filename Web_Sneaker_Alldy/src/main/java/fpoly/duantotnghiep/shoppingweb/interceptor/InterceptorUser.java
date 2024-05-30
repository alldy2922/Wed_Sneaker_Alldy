package fpoly.duantotnghiep.shoppingweb.interceptor;

import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class InterceptorUser implements HandlerInterceptor {

    @Autowired
    private IThuongHieuService thuongHieuService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("branches", thuongHieuService.getThuongHieuBanChay());
    }
}
