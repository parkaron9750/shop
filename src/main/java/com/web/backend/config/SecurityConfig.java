package com.web.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /**
         * requestMatchers 는 member 하위 폴더에 있는 파일들은 다 접근이 가능하지만 그 외 파일들은 인증 권한이 있는 사용자만 열수 있다.
         */

        http.formLogin((form) -> form
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("email")
                        .failureUrl("/member/login/error")
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(("/css/**")).permitAll()
                .requestMatchers(("/")).permitAll()
                .requestMatchers(("/member/**")).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .exceptionHandling((exception) ->exception
                .authenticationEntryPoint(new CustomEntryPoint()));



        return  http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //암호화 해주는 객체생성
    }
}
