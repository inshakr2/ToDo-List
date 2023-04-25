package com.rest.ToDoList.service;

import com.rest.ToDoList.domain.Account;
import com.rest.ToDoList.domain.AccountRole;
import com.rest.ToDoList.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account saveAccount(Account account) {
        Account encodedAccount = account.encodePassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream().map(
                r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
    }
}
