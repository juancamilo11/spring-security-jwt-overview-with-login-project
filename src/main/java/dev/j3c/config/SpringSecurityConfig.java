package dev.j3c.config;

import dev.j3c.config.jwt.JwtTokenHelper;
import dev.j3c.controllers.RestAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final RestAuthEntryPoint restAuthEntryPoint;

    @Autowired
    public SpringSecurityConfig(UserDetailsService userDetailsService, JwtTokenHelper jwtTokenHelper, RestAuthEntryPoint restAuthEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.restAuthEntryPoint = restAuthEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth); // Default value

        //In memory auth
//        auth.inMemoryAuthentication()
//                .withUser("juan.camilo")
//                .password(passwordEncoder().encode("12345")) // Password must be encoded
//                .authorities("USER","ADMIN");

        //Database auth
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //No login will be displayed --> All requests are allowed
//        http.authorizeRequests().anyRequest().permitAll();

        //It'll deny all requests from client
//        http.authorizeRequests().anyRequest().denyAll();

        //It'll only allow the requests from authenticated users
        http.authorizeRequests().anyRequest().authenticated();

        //Enable the H2 DB
        http.authorizeRequests(requests -> requests.antMatchers("/h2-console/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .httpBasic();

        //h2-console
        http.csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable();

        //It'll display the login form for authentication
        http.formLogin();
        http.httpBasic();

    }

}
