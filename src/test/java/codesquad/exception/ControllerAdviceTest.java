package codesquad.exception;

import codesquad.dto.ErrorResponse;
import codesquad.dto.SignUpDTO;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerAdviceTest extends AcceptanceTest {

    @Test
    public void createAccountFail() {
        String empty = "";
        SignUpDTO signUpFalse = new SignUpDTO(empty, "", "", "", "");

        ResponseEntity<ErrorResponse> response = template().postForEntity("/member/sign-up", signUpFalse, ErrorResponse.class);

        assertThat(response.toString()).isEqualTo("");
    }
}