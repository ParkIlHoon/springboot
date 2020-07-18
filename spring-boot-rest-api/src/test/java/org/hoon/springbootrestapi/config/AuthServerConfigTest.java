package org.hoon.springbootrestapi.config;

import org.hoon.springbootrestapi.account.Account;
import org.hoon.springbootrestapi.account.AccountRole;
import org.hoon.springbootrestapi.account.AccountService;
import org.hoon.springbootrestapi.common.AppProperties;
import org.hoon.springbootrestapi.common.BaseControllerTest;
import org.hoon.springbootrestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest
{
	@Autowired
	AccountService accountService;

	@Autowired
	AppProperties appProperties;

	@Test
	@TestDescription("인증 토큰을 발급 받는 테스트")
	public void getAuthToken() throws Exception
	{
		// 테스트 계정 생성
		String username = this.appProperties.getUserUserName();
		String password = this.appProperties.getUserPassword();
		Account account = Account.builder()
									.email(username)
									.password(password)
									.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
								.build();
		this.accountService.saveAccount(account);

		String clientId = this.appProperties.getClientId();
		String clientSecret = this.appProperties.getClientSecret();

		this.mockMvc.perform(post("/oauth/token")
								.with(httpBasic(clientId, clientSecret))
								.param("username", username)
								.param("password", password)
								.param("grant_type", "password"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("access_token").exists());
	}
}
