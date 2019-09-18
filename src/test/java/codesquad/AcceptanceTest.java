package codesquad;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
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
        createDefaultUser();

        return findByEmailId(DEFAULT_LOGIN_USER);
    }

    private void createDefaultUser() {
        Account account = new Account()
                .setEmail(DEFAULT_LOGIN_USER)
                .setName("bell")
                .setPassword("aaaa1111")
                .setPhoneNumber("010-0000-0000")
                .build();

        accountRepository.save(account);
    }

    protected Account findByEmailId(String accountId) {
        return accountRepository.findByEmail(accountId).get();
    }
}
