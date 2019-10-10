package codesquad.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void match() {
        String password = "abcd1234";
        String encodedPassword = passwordEncoder.encode(password);

        assertThat(encodedPassword.equals(password)).isFalse();
        assertThat(passwordEncoder.matches(password, encodedPassword)).isTrue();
    }
}
