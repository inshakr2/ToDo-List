package com.rest.ToDoList.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter(PROTECTED)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Account {

    @Id @GeneratedValue
    private Integer id;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    public static Account join(String email, String password, Set<AccountRole> roles) {

        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setRoles(roles);

        return account;
    }
}
