package codesquad.domain;

import codesquad.domain.member.MemberLoginDto;
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

public class MemberLoginValidationTest {
    private static Validator validator;
    private static Logger logger = LoggerFactory.getLogger(MemberLoginValidationTest.class);

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void memberId_blank() {
        MemberLoginDto memberLoginDto = new MemberLoginDto("", "q12345678");

        Set<ConstraintViolation<MemberLoginDto>> constraintViolations = validator.validate(memberLoginDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberLoginDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password_blank() {
        MemberLoginDto memberLoginDto = new MemberLoginDto("esp2ar0@gmail.com", "");

        Set<ConstraintViolation<MemberLoginDto>> constraintViolations = validator.validate(memberLoginDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberLoginDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }
}
