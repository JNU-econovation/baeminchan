package codesquad.web;

import codesquad.AcceptanceTest;
import codesquad.domain.Account;
import codesquad.domain.AccountRepository;
import codesquad.dto.LoginDTO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void login() {
        String password = "1111aaaa";
        LoginDTO request = new LoginDTO(DEFAULT_LOGIN_USER, password);

        ResponseEntity<Void> response = template().postForEntity("/member/sign-in", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(accountRepository.findByEmail(DEFAULT_LOGIN_USER).isPresent()).isTrue();
    }

    @Test
    public void login_wrong_email() {
        String email = "wrong_email@gmail.com";
        String password = "abcd1234";

        LoginDTO request = new LoginDTO(email, password);
        ResponseEntity<String> response = template().postForEntity("/member/sign-in", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(accountRepository.findByEmail(email).isPresent()).isFalse();
    }

    @Test
    public void login_wrong_password() {
        String password = "wrong_password";

        LoginDTO request = new LoginDTO(DEFAULT_LOGIN_USER, password);
        ResponseEntity<String> response = template().postForEntity("/member/sign-in", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(accountRepository.findByEmail(DEFAULT_LOGIN_USER).isPresent()).isTrue();
        assertThat(accountRepository.findByEmail(DEFAULT_LOGIN_USER).orElseThrow(RuntimeException::new).matchPassword(password));
    }
}
