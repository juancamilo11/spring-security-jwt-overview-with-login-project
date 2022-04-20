package dev.j3c.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth); // Default value
        auth.inMemoryAuthentication()
                .withUser("juan.camilo")
                .password(passwordEncoder().encode("12345")) // Password must be encoded
                .authorities("USER","ADMIN");
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

        //It'll display the login form for authentication
        http.formLogin();
        http.httpBasic();
    }

}
