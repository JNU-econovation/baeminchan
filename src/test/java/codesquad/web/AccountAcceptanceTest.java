package codesquad.web;

import codesquad.domain.AccountRepository;
import codesquad.dto.SignUpDTO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AccountAcceptanceTest.class);

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = template().getForEntity("/join", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createAccount() {
        String email = "bellroute@gmail.com";

        SignUpDTO signUpDTO = new SignUpDTO(email, "1234abcd", "1234abcd", "010-0000-0000", "bell");

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", signUpDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }

    @Test
    public void createInvalidEmail() {
        String email = "bellroute@";

        SignUpDTO signUpDTO = new SignUpDTO(email, "1234abcd", "1234abcd", "010-0000-0000", "bell");

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", signUpDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isFalse();
    }

    @Test
    public void createInvalidPassword() {
        String email = "bellroute@gmail.com";

        SignUpDTO signUpDTO = new SignUpDTO(email, "1234abcd", "abcd1234", "010-0000-0000", "bell");

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", signUpDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isFalse();
    }

    @Test
    public void createBlankData() {
        String email = "bellroute@gmail.com";

        SignUpDTO signUpDTO = new SignUpDTO(email, "1234abcd", "1234abcd", "010-0000-0000", "");

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", signUpDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }
}
