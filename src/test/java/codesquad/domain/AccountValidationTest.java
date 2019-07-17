package codesquad.domain;

import codesquad.dto.SignUpDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountValidationTest {
    private static final Logger log = LoggerFactory.getLogger(AccountValidationTest.class);
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void signUp_when_email_isEmpty() {
        SignUpDTO signUpDTO = new SignUpDTO("", "password12", "password12", "010-0000-0000", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(1);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void signUp_when_email_isWrong() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@", "password12", "password12", "010-0000-0000", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(1);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void signUp_when_password_isEmpty() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@gmail.com", "", "password12", "010-0000-0000", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(2);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void signUp_when_password_isWrong() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@gmail.com", "password", "password", "010-0000-0000", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(1);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }


    @Test
    public void signUp_when_name_isEmpty() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@gmail.com", "password12", "password12", "010-0000-0000", "");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(1);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void signUp_when_phoneNumber_isEmpty() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@gmail.com", "password12", "password12", "", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(2);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void signUp_when_phoneNumber_isWrong() {
        SignUpDTO signUpDTO = new SignUpDTO("bell@gmail.com", "password12", "password12", "010-00-000", "name");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(new Account(signUpDTO));
        assertThat(constraintViolations.size()).isEqualTo(1);

        for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

}
