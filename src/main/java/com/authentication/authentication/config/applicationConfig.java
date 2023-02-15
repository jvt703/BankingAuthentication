package com.authentication.authentication.config;

import com.authentication.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class applicationConfig {

        private final UserRepository userRepository;
        @Bean
        public UserDetailsService userDetailsService(){
            return username -> userRepository.findByfirstName(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        }

}
