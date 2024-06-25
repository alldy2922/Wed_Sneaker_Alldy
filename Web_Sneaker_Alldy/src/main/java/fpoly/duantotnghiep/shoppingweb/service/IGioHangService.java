package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.model.Cart;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;

import java.util.List;
import java.util.Optional;

public interface IGioHangService {

    List<GioHangDtoReponse> laySpTrongGio();

    List<GioHangDtoReponse> getCartFromDatabase(KhachHangModel user);

     void removeAllProductFromCart(KhachHangModel user);

     Boolean checkSoLuongLogin(KhachHangModel khachHang);

    void addToHoaDon(String idCTSP,Integer sl);

    List<GioHangDtoReponse> findCartById(KhachHangModel user, String productId);

    List<GioHangDtoReponse>  findCartByIdNoLogin(String productId);

    void syncCartFromSessionToDatabase(Cart sessionCart, KhachHangModel user);

     void removeProductFromCart(KhachHangModel user, String productId);

     void updateProductQuantityInDatabase(KhachHangModel user, String productId, Integer newQuantity);

    void addOrUpdateToCart(String idCTSP, Integer sl);

    void addProductToCart(Cart sessionCart, String productId, Integer quantity);

    void addProductToCart(KhachHangModel user, String productId, Integer quantity);

    void updateSoLuong(String key, Integer value);

    void removeProductInCart(String idCTSP);

    Boolean checkSanPhamTrongGio(String idCTSP);

    Integer getSoLuong(String idCTSP);

    void removeAllProdcutInCart();

    Boolean checkSoLuong();

    Cart getSessionCart();
}
