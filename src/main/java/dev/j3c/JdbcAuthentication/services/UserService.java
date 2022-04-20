package dev.j3c.JdbcAuthentication.services;

import dev.j3c.JdbcAuthentication.repositories.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserDetailRepository userDetailRepository;

    @Autowired
    public UserService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDetailRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found!"));
    }
}
