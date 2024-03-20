package com.jobnet.apigateway.configs.security;

import com.jobnet.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
            .switchIfEmpty(
                Mono.error(new UsernameNotFoundException("Username or password does not match."))
            )
            .map(CustomUserDetails::new)
            .map(userDetails -> {
                if (!userDetails.isAccountNonLocked())
                    throw new LockedException("Account is locked.");
                if (!userDetails.isEnabled())
                    throw new DisabledException("Account is disabled.");
                return userDetails;
            });
    }
}
