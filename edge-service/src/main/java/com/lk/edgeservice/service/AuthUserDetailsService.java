package com.lk.edgeservice.service;

import com.lk.edgeservice.model.Account;
import com.lk.edgeservice.repository.AuthRepository;
import com.lk.edgeservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("ROLE_"+account.getRole());
        return authRepository.save(account);
    }


    public String generateToken(Account account){
        Optional<Account> accountFound  =   authRepository.findByEmail(account.getEmail());
        if (accountFound == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getEmail(),
                        account.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(accountFound.get());

        return token;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = authRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        List<GrantedAuthority>  authorities =   new ArrayList<>();
        GrantedAuthority authority  =   new SimpleGrantedAuthority(account.get().getRole());
        authorities.add(authority);

        return new User(account.get().getEmail(), account.get().getPassword(), authorities);
    }


}
