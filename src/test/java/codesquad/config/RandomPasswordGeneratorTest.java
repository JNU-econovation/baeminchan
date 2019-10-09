package codesquad.config;

import codesquad.sequrity.RandomPasswordGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomPasswordGeneratorTest {

    @Test
    void generateRandomPassword() {
        String password = RandomPasswordGenerator.generatePassword();

        assertThat(password.length()).isEqualTo(8);
    }
}
