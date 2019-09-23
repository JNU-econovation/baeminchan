package codesquad;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AcceptanceTest.class);
    private static final String DEFAULT_LOGIN_USER = "user@gmail.com";
    private static final String ADMIN_LOGIN_USER = "admin@gmail.com";

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

    protected Account adminAccount() {
        createAdminAccount();

        return findByEmailId(ADMIN_LOGIN_USER);
    }

    private void createDefaultUser() {
        Account account = new Account()
                .setEmail(DEFAULT_LOGIN_USER)
                .setName("bell")
                .setPassword("aaaa1111")
                .setPhoneNumber("010-0000-0000")
                .build();

        log.info("defaultUser: {}", account.toString());
        accountRepository.deleteAll();
        accountRepository.save(account);
    }

    private void createAdminAccount() {
        Account account = new Account()
                .setEmail(ADMIN_LOGIN_USER)
                .setName("admin")
                .setPassword("1111aaaa")
                .setPhoneNumber("010-0000-0001")
                .changeToAdmin()
                .build();

        accountRepository.deleteAll();
        accountRepository.save(account);
    }

    protected Account findByEmailId(String accountId) {
        log.info("email: {}", accountId);
        log.info("accountDB1: {}", accountRepository.findAll());
        Account account = accountRepository.findByEmail(accountId).orElseThrow(RuntimeException::new);
        log.info("dbAccount: {}", account);
        return account;
    }
}
