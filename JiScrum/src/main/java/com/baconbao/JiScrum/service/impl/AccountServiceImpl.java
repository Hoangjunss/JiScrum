package com.baconbao.JiScrum.service.impl;

import com.baconbao.JiScrum.dto.account.AccountCreateDTO;
import com.baconbao.JiScrum.dto.account.AccountDTO;
import com.baconbao.JiScrum.dto.account.AuthenticationDTO;
import com.baconbao.JiScrum.dto.account.FormLoginDTO;
import com.baconbao.JiScrum.mapper.AccountMapper;
import com.baconbao.JiScrum.model.Account;
import com.baconbao.JiScrum.repository.AccountRepository;
import com.baconbao.JiScrum.service.AccountService;
import com.baconbao.JiScrum.utils.IdGenerator;
import com.baconbao.JiScrum.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private final AccountRepository accountRepository;

    @Override
    public AuthenticationDTO signIn(FormLoginDTO formLoginDTO) {
        try {
            String name = formLoginDTO.getUsername().trim().toLowerCase();

            Account account = accountRepository.findByUsername(name)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found"));

            log.info("Account: {}", account);
            log.info("formLoginDTO: {}", formLoginDTO);

            if (!passwordEncoder.matches(formLoginDTO.getPassword(), account.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }

            try {
                String jwtToken = jwtTokenUtil.generateToken((UserDetails) account);
                String refreshToken = jwtTokenUtil.generateRefreshToken((UserDetails) account);
                return AuthenticationDTO.builder()
                        .token(jwtToken)
                        .refreshToken(refreshToken)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO create(AccountCreateDTO accountCreateDTO) {

        if (usernameExists(accountCreateDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Account account = AccountMapper.toEntity(accountCreateDTO);
        account.setId(IdGenerator.getGenerationId());
        account.setPassword(passwordEncoder.encode(accountCreateDTO.getPassword()));

        return AccountMapper.toDTO(accountRepository.save(account));
    }

    @Override
    public Account getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Unauthorized");
        }

        Account account = (Account) authentication.getPrincipal();

        log.info("User principal: {}", account);
        return account;
    }

    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
}
