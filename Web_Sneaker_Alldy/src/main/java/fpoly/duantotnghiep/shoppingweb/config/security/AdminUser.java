package fpoly.duantotnghiep.shoppingweb.config.security;

<<<<<<< HEAD

import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
=======
>>>>>>> c147c504a5348021019527ed5e618ccbf3043942
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Data
public class AdminUser implements UserDetails {
    NhanVienModel nhanVienModel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(nhanVienModel.getVaiTro().getMa()));
    }

    @Override
    public String getPassword() {
        return nhanVienModel.getPassword();
    }

    @Override
    public String getUsername() {
        return nhanVienModel.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
