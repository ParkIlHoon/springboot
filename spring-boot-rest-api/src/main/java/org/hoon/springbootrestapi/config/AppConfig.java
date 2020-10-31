package org.hoon.springbootrestapi.config;

import org.hoon.springbootrestapi.account.Account;
import org.hoon.springbootrestapi.account.AccountRepository;
import org.hoon.springbootrestapi.account.AccountRole;
import org.hoon.springbootrestapi.account.AccountService;
import org.hoon.springbootrestapi.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

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
            AccountRepository accountRepository;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Optional<Account> findAccount = this.accountRepository.findByEmail(appProperties.getAdminUserName());

                if (findAccount.isEmpty()) {
                    Account admin = Account.builder()
                            .email(appProperties.getAdminUserName())
                            .password(appProperties.getAdminPassword())
                            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                            .build();
                    accountService.saveAccount(admin);

                    Account user = Account.builder()
                            .email(appProperties.getUserUserName())
                            .password(appProperties.getUserPassword())
                            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                            .build();
                    accountService.saveAccount(user);
                }
            }
        };
    }
}
