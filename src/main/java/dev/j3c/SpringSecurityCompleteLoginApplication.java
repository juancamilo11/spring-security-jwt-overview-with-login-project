package dev.j3c;

import dev.j3c.JdbcAuthentication.entities.Authority;
import dev.j3c.JdbcAuthentication.entities.User;
import dev.j3c.JdbcAuthentication.repositories.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringSecurityCompleteLoginApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;

    @Autowired
    public SpringSecurityCompleteLoginApplication(PasswordEncoder passwordEncoder, UserDetailRepository userDetailRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailRepository = userDetailRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityCompleteLoginApplication.class, args);
    }

    @PostConstruct
    protected void init() {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority("USER", "User role"));
        authorityList.add(createAuthority("ADMIN", "Admin role"));

        User user = new User();
        user.setUsername("juan.camilo");
        user.setFirstName("Juan Camilo");
        user.setLastName("Cardona Calderón");

        user.setPassword(passwordEncoder.encode("1234567890"));
        user.setEmail("juan.cardona@gmail.com");
        user.setEnabled(true);

        this.userDetailRepository.save(user);

    }

    private Authority createAuthority(String roleCode, String roleDescription) {
        Authority authority = new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }

}
