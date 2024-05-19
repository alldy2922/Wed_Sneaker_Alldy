package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;

import java.util.List;

public interface VourcherService {
   public List<VoucherReponse> findAll();
}
