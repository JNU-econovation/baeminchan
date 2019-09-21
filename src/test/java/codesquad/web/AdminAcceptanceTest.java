package codesquad.web;

import codesquad.AcceptanceTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AdminAcceptanceTest extends AcceptanceTest {

    @Test
    public void request_adminPage() {
        ResponseEntity<String> adminResponse = basicAuthTemplate(adminAccount()).getForEntity("/admin", String.class);
        ResponseEntity<String> userResponse = basicAuthTemplate(defaultUser()).getForEntity("/admin", String.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, adminResponse.getStatusCode()),
                () -> assertEquals(HttpStatus.FORBIDDEN, userResponse.getStatusCode())
        );
    }
}
