package fpoly.duantotnghiep.shoppingweb.config.security;

import fpoly.duantotnghiep.shoppingweb.service.seucrity.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityCustomerConfig {
    private final CustomerService customerService;

    public SecurityCustomerConfig(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean("Filter-user")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] authen = {"/lich-su-mua-hang1","/danh-sach-yeu-thich/**","/doi-mat-khau/**","/thong-tin-ca-nhan/**"};
        http
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers(authen).authenticated()
                                .anyRequest().permitAll()
                )
                .userDetailsService(customerService)
                .formLogin(login -> login.loginPage("/dang-nhap")
                        .loginProcessingUrl("/dang-nhap")
//                        .defaultSuccessUrl("/trang-chu", false)
                        .failureUrl("/dang-nhap/error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                        .permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
                .logout(l -> l
                        .logoutUrl("/logout")
                                .logoutSuccessUrl("/trang-chu")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                ).exceptionHandling(han -> han.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                                        , new AntPathRequestMatcher("/danh-sach-yeu-thich/delete/**","DELETE"))

                                        .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                                        , new AntPathRequestMatcher("/danh-sach-yeu-thich/add","POST")));

        return http.build();
    }

//    @Bean("Encoder-user")
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
