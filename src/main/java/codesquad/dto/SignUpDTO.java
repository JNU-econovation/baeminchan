package codesquad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class SignUpDTO {
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
    private String password;

    private String passwordForCheck;
    private String phoneNumber;
    private String name;

    public boolean isCheckingPassWordMatch() {
        return password.equals(passwordForCheck);
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
