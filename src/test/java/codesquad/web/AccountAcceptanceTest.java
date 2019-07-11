package codesquad.web;

import codesquad.domain.AccountRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.HtmlFormDataBuilder;
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

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("email", email)
                .addParameter("password", "1234abcd")
                .addParameter("name", "bell")
                .addParameter("phoneNumber", "010-0000-0000")
                .build();

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
        assertThat(request.getHeaders().getLocation().getPath()).startsWith("/join");
    }

    @Test
    public void createInvalidEmail() {
        String email = "bellroute@";

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("email", email)
                .addParameter("password", "1234abcd")
                .addParameter("name", "bell")
                .addParameter("phoneNumber", "010-0000-0000")
                .build();

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }

    @Test
    public void createInvalidPassword() {
        String email = "bellroute@gmail.com";

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("email", "bellroute@gmail.com")
                .addParameter("password", "111111")
                .addParameter("name", "bell")
                .addParameter("phoneNumber", "010-0000-0000")
                .build();

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }

    @Test
    public void createBlankData() {
        String email = "bellroute@gmail.com";

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("email", "bellroute@gmail.com")
                .addParameter("password", "111111")
                .addParameter("name", "")
                .addParameter("phoneNumber", "")
                .build();

        ResponseEntity<String> response = template().postForEntity("/join/sign-up", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(accountRepository.findByEmail(email).isPresent()).isTrue();
    }
}
