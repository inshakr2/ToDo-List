package com.rest.ToDoList.service;

import com.rest.ToDoList.domain.Account;
import com.rest.ToDoList.domain.AccountRole;
import com.rest.ToDoList.repository.AccountRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {


    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {

        // given
        String name = "chany@kakao.com";
        String password = "123";
 
        Account joinAccount = Account.join(name, password, Set.of(AccountRole.ADMIN, AccountRole.USER));

        accountService.saveAccount(joinAccount);

        // when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        // then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();

    }

    @Test
    public void findByUsernameFail1() {
        String username = "foo@naver.com";

        try {
            accountService.loadUserByUsername(username);
            fail("supposed to be failed");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage()).containsSequence(username);
        }
    }

    @Test
    public void findByUsernameFail2() {
        String username = "foo@naver.com";

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                                        () -> accountService.loadUserByUsername(username));
        assertThat(exception).hasMessageContaining(username);

    }

}