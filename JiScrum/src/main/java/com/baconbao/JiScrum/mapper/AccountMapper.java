package com.baconbao.JiScrum.mapper;

import com.baconbao.JiScrum.dto.account.AccountCreateDTO;
import com.baconbao.JiScrum.dto.account.AccountDTO;
import com.baconbao.JiScrum.model.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountMapper {

    public static Account toEntity(AccountCreateDTO accountCreateDTO){
        return Account.builder()
                .username(accountCreateDTO.getUsername())
                .password(accountCreateDTO.getPassword())
                .email(accountCreateDTO.getEmail())
                .createdAt(LocalDateTime.now())
                .status(true)
                .build();
    }

    public static AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .createdAt(account.getCreatedAt())
                .status(account.getStatus())
                .build();
    }
}
