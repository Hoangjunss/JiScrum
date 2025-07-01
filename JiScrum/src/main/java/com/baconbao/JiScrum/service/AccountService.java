package com.baconbao.JiScrum.service;

import com.baconbao.JiScrum.dto.account.AccountCreateDTO;
import com.baconbao.JiScrum.dto.account.AccountDTO;
import com.baconbao.JiScrum.dto.account.AuthenticationDTO;
import com.baconbao.JiScrum.dto.account.FormLoginDTO;
import com.baconbao.JiScrum.model.Account;

public interface AccountService {
    AuthenticationDTO signIn(FormLoginDTO formLoginDTO);

    AccountDTO create(AccountCreateDTO accountCreateDTO);

    Account getPrincipal();
}
