package com.rest.ToDoList.config;

import com.rest.ToDoList.common.BaseTestController;
import com.rest.ToDoList.domain.Account;
import com.rest.ToDoList.domain.AccountRole;
import com.rest.ToDoList.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

public class AuthServerConfigTest extends BaseTestController {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("인증 토큰 발급 테스트")
    public void getAuthToken() throws Exception{

        String username = "chany@email.com";
        String password = "chany";
        Account account = Account.join(username, password,
                Set.of(AccountRole.ADMIN, AccountRole.USER));
        accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "secret";

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId,clientSecret))
                    .param("username", username)
                    .param("password", password)
                    .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }

}