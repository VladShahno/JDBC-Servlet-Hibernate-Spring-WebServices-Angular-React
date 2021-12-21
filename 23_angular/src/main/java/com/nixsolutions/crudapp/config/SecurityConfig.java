package com.nixsolutions.crudapp.config;

import com.nixsolutions.crudapp.filter.JwtTokenFilter;
import com.nixsolutions.crudapp.jwt.JwtTokenProvider;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@ComponentScan("com.nixsolutions.crudapp")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    public SecurityConfig(@Lazy UserService userService,
            JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.applyPermitDefaultValues();
                    corsConfiguration.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE"));
                    return corsConfiguration;
                }).and().csrf().disable().authorizeRequests().antMatchers("/api/login")
                .permitAll().antMatchers("/api/registration").permitAll()
                .antMatchers("users/**").hasRole("ADMIN").anyRequest()
                .authenticated();
        http.addFilterBefore(new JwtTokenFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}