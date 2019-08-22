package codesquad.dto;

import codesquad.utils.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignUpDTO {

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = RegexUtil.REGEX_PASSWORD)
    private String password;

    private String passwordForCheck;

    @Pattern(regexp = RegexUtil.REGEX_PHONE_NUMBER)
    private String phoneNumber;

    @Size(min = 2, max = 15)
    private String name;

    public boolean isCheckingPassWordMatch() {
        return password.equals(passwordForCheck);
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
