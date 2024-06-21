package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;

import java.util.List;

public interface IGioHangService {
//    List<GioHangDtoReponse> findAll();
//    GioHangDtoReponse finById(String s);
//    public boolean existsById(String s);
//    public void deleteById(String s);
//    public Map<String, Integer> getProductInCart();
     List<GioHangDtoReponse> laySpTrongGio();
    public void addOrUpdateToCart(String idCTSP,Integer sl);

    public void updateSoLuong(String key,Integer value);
    public void removeProductInCart(String idCTSP);

    Boolean checkSanPhamTrongGio(String idCTSP);

    Integer getSoLuong(String idCTSP);
    public void removeAllProdcutInCart();

    Boolean checkSoLuong();
}
