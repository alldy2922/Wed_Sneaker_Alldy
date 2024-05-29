package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.Cart;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IGioHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SessionScope
public class GioHangServiceImpl implements IGioHangService {
    @Autowired
    IGioHangRepository repository;
    @Autowired
    private IKhachHangRepository khachHangRepository;
    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;

    private final Cart cart = new Cart();


    // update
    public void addOrUpdateToCart(String idCTSP,Integer sl){
        Map<String,Integer> sanPhamTrongGio  = cart.getProductInCart();
        boolean chk = false;
        //Nếu sản phẩm đã có trong giỏ thì + dồn số lượng
        if(sanPhamTrongGio.containsKey(idCTSP)){//Kiểm tra sản phẩm có trong giỏ hàng chưa
            //Lấy số lượng hiện tại
            Integer soLuongHienCo = sanPhamTrongGio.get(idCTSP);
            //Cộng số lượng
            Integer soLuongMoi = soLuongHienCo + sl;
            //Cập nhật lại giỏ hàng
            sanPhamTrongGio.put(idCTSP,soLuongMoi);
        }else{
            sanPhamTrongGio.put(idCTSP,sl);
        }
        System.out.println(sanPhamTrongGio.toString());
        cart.setProductInCart(sanPhamTrongGio);
    }
    public void removeProductInCart(String idCTSP){
        Map<String,Integer> productInCart = cart.getProductInCart();
        productInCart.remove(idCTSP);
    }
    @Override
    public void removeAllProdcutInCart(){
        Map<String,Integer> productInCart = cart.getProductInCart();
        productInCart.clear();
    }

    public void updateSoLuong(String key,Integer value){
//        Map<String,Integer> product = cart.getProductInCart();
        cart.getProductInCart().put(key,value);
    }
    @Override
    public List<GioHangDtoReponse> laySpTrongGio(){
        return cart.getProductInCart().entrySet().stream().map(m -> new GioHangDtoReponse(repository.findById(m.getKey()).get(),m.getValue()))
                .collect(Collectors.toList());
    }
    @Override
    public Boolean checkSanPhamTrongGio(String idCTSP){
        return cart.getProductInCart().containsKey(idCTSP);
    }
    @Override
    public Integer getSoLuong(String idCTSP){
        return cart.getProductInCart().get(idCTSP);
    }
    @Override
    public Boolean checkSoLuong(){
        Boolean rs = true;
        List<GioHangDtoReponse> giohang = this.laySpTrongGio();

        for (var item: giohang) {
            int sl = item.getSoLuong();
            if(item.getSoLuongSanPham() < sl){
                rs = false;
            }
        }

        return rs;
    }

}
