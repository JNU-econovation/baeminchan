package support.test;

import codesquad.domain.member.MemberLoginDto;
import codesquad.domain.member.MemberRequestDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @Autowired
    private TestRestTemplate template;

    public TestRestTemplate template() {
        return template;
    }

    protected String createResource(String path, Object bodyPayload) {
        ResponseEntity<Void> response = template().postForEntity(path, bodyPayload, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return response.getHeaders().getLocation().getPath();
    }

    protected <T> T getResource(String location, Class<T> responseType) {
        return template().getForObject(location, responseType);
    }
}
