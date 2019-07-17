package codesquad.service;

import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import codesquad.dto.SignUpDTO;
import codesquad.exception.NotFoundException;
import codesquad.exception.UnAuthenticationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void createAccount() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        SignUpDTO signUpDTO = new SignUpDTO(EMAIL, PASSWORD, PASSWORD, PHONE_NUMBER, NAME);
        when(accountRepository.save(new Account(signUpDTO))).thenReturn(account);

        assertThat(accountService.create(signUpDTO).getEmail()).isEqualTo(EMAIL);
        //fail
    }

    @Test(expected = RuntimeException.class)
    public void create_notMatchedPassword() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        SignUpDTO signUpDTO = new SignUpDTO(EMAIL, PASSWORD, WRONG_PASSWORD, PHONE_NUMBER, NAME);
        when(accountRepository.save(new Account(signUpDTO))).thenReturn(account);

        accountService.create(signUpDTO);
    }

    @Test
    public void findAccount() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        assertThat(accountService.findByEmail(EMAIL).getEmail()).isEqualTo(EMAIL);
    }

    @Test
    public void login() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        assertThat(accountService.login(session, new LoginDTO(EMAIL, PASSWORD))).isEqualTo(account);
    }

    @Test(expected = NotFoundException.class)
    public void login_wrong_email() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        accountService.login(session, new LoginDTO(WRONG_EMAIL, PASSWORD));
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_wrong_password() {
        Account account = new Account(EMAIL, PASSWORD, NAME, PHONE_NUMBER);
        HttpSession session = new MockHttpSession();
        when(accountRepository.findByEmail(EMAIL)).thenReturn(Optional.of(account));

        accountService.login(session, new LoginDTO(EMAIL, WRONG_PASSWORD));
    }
}
