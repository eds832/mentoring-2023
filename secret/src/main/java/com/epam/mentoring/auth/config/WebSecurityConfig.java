package com.epam.mentoring.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.epam.mentoring.auth.handler.CustomAuthenticationFailureHandler;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) throws Exception {

        return http.authorizeHttpRequests(
                requests -> requests
                    .antMatchers(HttpMethod.GET, "/about", "/", "/login", "/login?error", "/blocked", "/css/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/info", "/secret").hasAuthority("STANDARD")
                    .antMatchers(HttpMethod.GET, "/admin").hasAuthority("ADMIN")
                    .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin.loginPage("/login")
                .failureHandler(customAuthenticationFailureHandler).permitAll())
            .logout(formLogout -> formLogout.deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logoutSuccess")
                .permitAll())
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();

        return new BCryptPasswordEncoder();
    }

}