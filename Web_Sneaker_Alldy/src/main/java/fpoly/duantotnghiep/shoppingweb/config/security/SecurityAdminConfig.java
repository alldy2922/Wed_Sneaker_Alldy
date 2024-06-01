package fpoly.duantotnghiep.shoppingweb.config.security;

import fpoly.duantotnghiep.shoppingweb.enumtype.Roles;
import fpoly.duantotnghiep.shoppingweb.service.security.UserAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityAdminConfig {
    private final UserAdminService userAdminService;

    public SecurityAdminConfig(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @Bean
//    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] adminPermitAll = {"/admin/AngularJs/**", "/admin/assets/**", "/admin/css/**", "/admin/images/**", "/admin/js/**",
                "/admin/quen-mat-khau", "/image/**", "/admin/dat-lai-mat-khau/**", "/admin/quen-mat-khau/xac-nhan",
                "/admin/AngularJS/CheckOut.js", "/admin/AngularJS/DonHang.js", "/admin/AngularJS/DanhSachYeuThichJS.js", "/admin/san-pham/filter"};
        http.securityMatcher("/admin/**")
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers(adminPermitAll).permitAll()
//                        .requestMatchers("/detail").hasAnyAuthority("STAFF","ADMIN")
//                        .requestMatchers("/add").hasAuthority("ADMIN")
                                .requestMatchers("/admin/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.STAFF.name())
                                .anyRequest().permitAll()
                )
                .userDetailsService(userAdminService)
                .formLogin(login -> login.loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/trang-chu", false)
                        .failureUrl("/admin/login/error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
                .logout(l -> l
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
