package codesquad.domain;

import codesquad.domain.member.MemberRequestDto;
import org.junit.Before;
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

public class MemberRequestValidationTest {
    private static Validator validator;
    private static Logger logger = LoggerFactory.getLogger(MemberRequestValidationTest.class);
    private MemberRequestDto memberRequestDto;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setUp_each() {
        memberRequestDto = new MemberRequestDto();
        memberRequestDto.setEmailId("chicken");
        memberRequestDto.setEmailDomain("gmail.com");
        memberRequestDto.setPassword("q12345678");
        memberRequestDto.setPassword2("q12345678");
        memberRequestDto.setName("burrito");
        memberRequestDto.setCell1("010");
        memberRequestDto.setCell2("1234");
        memberRequestDto.setCell3("5678");
    }

    @Test
    public void emailId_blank() {
        memberRequestDto.setEmailId("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void emailDomain_blank() {
        memberRequestDto.setEmailDomain("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password_blank() {
        memberRequestDto.setPassword("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password2_blank() {
        memberRequestDto.setPassword2("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void name_blank() {
        memberRequestDto.setName("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void cell_blank() {
        memberRequestDto.setCell1("");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password_password2_mismatch() {
        memberRequestDto.setPassword2("asd123456");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password_pattern_mismatch_nonAlphabet() {
        memberRequestDto.setPassword("12345678");
        memberRequestDto.setPassword2("12345678");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }

    @Test
    public void password_pattern_mismatch_less_than_8_characters() {
        memberRequestDto.setPassword("123a");
        memberRequestDto.setPassword2("123a");

        Set<ConstraintViolation<MemberRequestDto>> constraintViolations = validator.validate(memberRequestDto);
        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);

        for (ConstraintViolation<MemberRequestDto> constraintViolation : constraintViolations) {
            logger.debug(constraintViolation.getMessage());
        }
    }
}
