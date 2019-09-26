package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.NotFoundAccountException;
import codesquad.exception.UnAuthenticationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountServiceTest {
    private static final String EMAIL = "test@gmail.com";
    private static final String WRONG_EMAIL = "wrong@gmail.com";
    private static final String PASSWORD = "1234abcd";
    private static final String WRONG_PASSWORD = "abcd1234";
    private static final String PHONE_NUMBER = "010-0000-0000";
    private static final String NAME = "bell";

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account()
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setPhoneNumber(PHONE_NUMBER)
                .build();
    }

    @Test
    public void createAccount() {

        SignUpDTO signUpDTO = new SignUpDTO(EMAIL, PASSWORD, PASSWORD, PHONE_NUMBER, NAME);
        when(accountRepository.save(new Account(signUpDTO))).thenReturn(account);

        assertThat(accountRepository.save(new Account(signUpDTO)).getEmail()).isEqualTo(EMAIL);
        //fail
    }

    @Test(expected = RuntimeException.class)
    public void create_notMatchedPassword() {
        SignUpDTO signUpDTO = new SignUpDTO(EMAIL, PASSWORD, WRONG_PASSWORD, PHONE_NUMBER, NAME);
        when(accountRepository.save(new Account(signUpDTO))).thenReturn(account);

        accountService.create(signUpDTO);
    }

    @Test
    public void findAccount() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        assertThat(accountService.findByEmail(EMAIL).getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void login() {
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));
        when(passwordEncoder.matches(PASSWORD, accountRepository.findByEmail(EMAIL).orElseThrow(NotFoundAccountException::new).getPassword())).thenReturn(true);

        assertThat(accountService.login(session, new LoginDTO(EMAIL, PASSWORD))).isEqualTo(account);
    }

    @Test(expected = NotFoundAccountException.class)
    public void login_wrong_email() {
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        accountService.login(session, new LoginDTO(WRONG_EMAIL, PASSWORD));
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_wrong_password() {
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        accountService.login(session, new LoginDTO(EMAIL, WRONG_PASSWORD));
    }
}
