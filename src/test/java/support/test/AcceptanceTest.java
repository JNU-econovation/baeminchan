package support.test;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER = "bellroute@gmail.com";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AccountRepository accountRepository;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(Account loginUser) {
        return template.withBasicAuth(loginUser.getEmail(), loginUser.getPassword());
    }

    protected Account defaultUser() {
        return findByEmailId(DEFAULT_LOGIN_USER);
    }

    protected Account findByEmailId(String accountId) {
        return accountRepository.findByEmail(accountId).get();
    }
}
