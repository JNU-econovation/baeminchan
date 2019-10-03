package codesquad.validation;

import codesquad.dto.ErrorResponse;
import codesquad.dto.SignUpDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import codesquad.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerAdviceTest extends AcceptanceTest {

    @Test
    public void createAccountFail() {
        String empty = "";
        SignUpDTO signUpFalse = new SignUpDTO(empty, "", "", "", "");

        ResponseEntity<ErrorResponse> response = template().postForEntity("/member/sign-up", signUpFalse, ErrorResponse.class);

        assertThat(response.toString()).contains("비밀번호 형식이 올바르지 않습니다.");
    }
}