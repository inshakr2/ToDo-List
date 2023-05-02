package com.rest.ToDoList.config;

import com.rest.ToDoList.domain.Account;
import com.rest.ToDoList.domain.AccountRole;
import com.rest.ToDoList.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {

        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Account user = Account.join(appProperties.getUserUsername(), appProperties.getUserPassword(),
                                         Set.of(AccountRole.USER));
                accountService.saveAccount(user);

                Account admin = Account.join(appProperties.getAdminUsername(), appProperties.getAdminPassword(),
                        Set.of(AccountRole.ADMIN, AccountRole.USER));
                accountService.saveAccount(admin);

            }
        };
    }

}
