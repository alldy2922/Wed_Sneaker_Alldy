package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.*;
import fpoly.duantotnghiep.shoppingweb.repository.GioHangUserRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IGioHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
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
    @Autowired
    private GioHangUserRepository gioHangUserRepository;
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
    //find sanPham by id in login
    @Override
    public List<GioHangDtoReponse> findCartById(KhachHangModel user, String productId) {
        ChiTietSanPhamModel chiTietSanPham = chiTietSanPhamRepository.findById(productId).orElse(null);
        if (chiTietSanPham == null) {
            return Collections.emptyList(); // Return an empty list if the product is not found
        }

        GioHangModel gioHang = gioHangUserRepository.findByKhachHangAndChiTietSanPham(user, chiTietSanPham);
        if (gioHang == null) {
            return Collections.emptyList(); // Return an empty list if the cart is not found
        }

        GioHangDtoReponse cartContent = new GioHangDtoReponse(gioHang.getChiTietSanPham(), gioHang.getSoLuong());
        return Collections.singletonList(cartContent); // Return a list with a single item
    }

    @Override
    public  List<GioHangDtoReponse> findCartByIdNoLogin(String productId) {
 return null;
    }

    @Override
    public void updateProductQuantityInDatabase(KhachHangModel user, String productId, Integer newQuantity) {
        ChiTietSanPhamModel chiTietSanPham = chiTietSanPhamRepository.findById(productId).orElse(null);
        if (chiTietSanPham == null) {
            return;
        }

        GioHangModel gioHang = gioHangUserRepository.findByKhachHangAndChiTietSanPham(user, chiTietSanPham);
        if (gioHang != null) {
            gioHang.setSoLuong(newQuantity);
            gioHangUserRepository.save(gioHang);
        }
    }

    public void removeProductInCart(String idCTSP){
        Map<String,Integer> productInCart = cart.getProductInCart();
        productInCart.remove(idCTSP);
    }
    @Override
    public void removeProductFromCart(KhachHangModel user, String productId) {
        ChiTietSanPhamModel chiTietSanPham = chiTietSanPhamRepository.findById(productId).orElse(null);
        if (chiTietSanPham == null) {
            return;
        }

        GioHangModel gioHang = gioHangUserRepository.findByKhachHangAndChiTietSanPham(user, chiTietSanPham);
        if (gioHang != null) {
            gioHangUserRepository.delete(gioHang);
        }
    }
    @Override
    @Transactional
    public void removeAllProductFromCart(KhachHangModel user) {
        gioHangUserRepository.deleteAllByKhachHang(user);
    }

    @Override
    public Boolean checkSoLuongLogin(KhachHangModel khachHang) {
        Boolean rs = true;
        List<GioHangDtoReponse> giohang = this.getCartFromDatabase(khachHang);

        for (var item: giohang) {
            int sl = item.getSoLuong();
            if(item.getSoLuongSanPham() < sl){
                rs = false;
            }
        }

        return rs;
    }

    @Override
    public void addToHoaDon(String idCTSP, Integer sl) {
        Map<String,Integer> sanPhamTrongGio  = cart.getProductInCart();
        sanPhamTrongGio.put(idCTSP,sl);
        System.out.println(sanPhamTrongGio.toString());
        cart.setProductInCart(sanPhamTrongGio);
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
    public void syncCartFromSessionToDatabase(Cart sessionCart, KhachHangModel user) {
        Map<String, Integer> sessionProducts = sessionCart.getProductInCart();
        for (Map.Entry<String, Integer> entry : sessionProducts.entrySet()) {
            String productId = entry.getKey();
            Integer quantity = entry.getValue();

            ChiTietSanPhamModel chiTietSanPham = chiTietSanPhamRepository.findById(productId).orElse(null);
            if (chiTietSanPham == null) {
                // Handle the case where the product is not found
                continue;
            }

            GioHangModel gioHang = gioHangUserRepository.findByKhachHangAndChiTietSanPham(user, chiTietSanPham);
            if (gioHang != null) {
                // If the product already exists in the cart, update the quantity
                gioHang.setSoLuong(gioHang.getSoLuong() + quantity);
            } else {
                // If the product doesn't exist in the cart, create a new entry
                gioHang = new GioHangModel();
                gioHang.setKhachHang(user);
                gioHang.setChiTietSanPham(chiTietSanPham);
                gioHang.setSoLuong(quantity);
            }
            // Save or update the product in the database
            gioHangUserRepository.save(gioHang);
        }
    }

    @Override
    public List<GioHangDtoReponse> getCartFromDatabase(KhachHangModel user) {
        List<GioHangModel> gioHangs = gioHangUserRepository.findByKhachHang(user);
        List<GioHangDtoReponse> cartContents = gioHangs.stream()
                .map(gioHang -> new GioHangDtoReponse(gioHang.getChiTietSanPham(), gioHang.getSoLuong()))
                .collect(Collectors.toList());
        return cartContents;
    }
    @Override
    public void addProductToCart(Cart sessionCart, String productId, Integer quantity) {
        Map<String, Integer> products = sessionCart.getProductInCart();
        products.merge(productId, quantity, Integer::sum);
        sessionCart.setProductInCart(products);
    }

    @Override
    public void addProductToCart(KhachHangModel user, String productId, Integer quantity) {
        ChiTietSanPhamModel chiTietSanPham = chiTietSanPhamRepository.findById(productId).orElse(null);
        if (chiTietSanPham == null) {
            return;
        }

        GioHangModel gioHang = gioHangUserRepository.findByKhachHangAndChiTietSanPham(user, chiTietSanPham);
        if (gioHang != null) {
            gioHang.setSoLuong(gioHang.getSoLuong() + quantity);
        } else {
            gioHang = new GioHangModel();
            gioHang.setKhachHang(user);
            gioHang.setChiTietSanPham(chiTietSanPham);
            gioHang.setSoLuong(quantity);
        }

        if (gioHang != null) { // Ensure gioHang is not null before calling methods on it
            gioHangUserRepository.save(gioHang);
        }
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
    @Override
    public Cart getSessionCart() {
        return this.cart;
    }
}
