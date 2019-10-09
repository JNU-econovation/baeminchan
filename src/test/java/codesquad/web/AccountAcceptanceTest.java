package codesquad.web;

import codesquad.AcceptanceTest;
import codesquad.domain.AccountRepository;
import codesquad.dto.FindingEmailDTO;
import codesquad.dto.FindingPasswordDTO;
import codesquad.dto.SignUpDTO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        String email = "bellrout@gmail.com";

        SignUpDTO signUpDTO = new SignUpDTO(email, "1234abcd", "1234abcd", "010-0000-0000", "bell");

        ResponseEntity<String> response = template().postForEntity("/member/sign-up", signUpDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }

    @Test
    public void find_id() {
        FindingEmailDTO findingEmailDTO = new FindingEmailDTO("user", "010-0000-1111");

        ResponseEntity<String> response = template().postForEntity("/member/find/request", findingEmailDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().contains("user@gmail.com")).isTrue();
    }

    @Test
    public void findIdFail_when_wrong_name() {
        FindingEmailDTO findingEmailDTO = new FindingEmailDTO("틀렸지롱", "010-0000-1111");

        ResponseEntity<String> response = template().postForEntity("/member/find/request", findingEmailDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().contains("user@gmail.com")).isFalse();
    }

    @Test
    public void findIdFail_when_wrong_phoneNumber() {
        FindingEmailDTO findingEmailDTO = new FindingEmailDTO("user", "010-0000-0000");

        ResponseEntity<String> response = template().postForEntity("/member/find/request", findingEmailDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().contains("user@gmail.com")).isFalse();
    }

    @Test
    public void find_password() {
        FindingPasswordDTO findingPasswordDTO = new FindingPasswordDTO("user@gmail.com", "user", "010-0000-1111");

        ResponseEntity<String> response = template().postForEntity("/member/find-pass/request", findingPasswordDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().contains("1111aaaa")).isTrue();
    }

    @Test
    public void findPasswordFail_when_wrong_id() {
        FindingPasswordDTO findingPasswordDTO = new FindingPasswordDTO("wrongwrong@gmail.com", "user", "010-0000-1111");

        ResponseEntity<String> response = template().postForEntity("/member/find-pass/request", findingPasswordDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().contains("1111aaaa")).isFalse();
    }

    @Test
    public void findPasswordFail_when_wrong_name() {
        FindingPasswordDTO findingPasswordDTO = new FindingPasswordDTO("user@gmail.com", "wrong", "010-0000-1111");

        ResponseEntity<String> response = template().postForEntity("/member/find-pass/request", findingPasswordDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().contains("임시비밀번호")).isFalse();
    }

    @Test
    public void findPasswordFail_when_wrong_phoneNumber() {FindingPasswordDTO findingPasswordDTO = new FindingPasswordDTO("user@gmail.com", "user", "010-0000-0000");

        ResponseEntity<String> response = template().postForEntity("/member/find-pass/request", findingPasswordDTO, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().contains("임시비밀번호")).isTrue();
    }
}
