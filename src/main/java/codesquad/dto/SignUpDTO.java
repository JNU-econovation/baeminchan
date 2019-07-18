package codesquad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignUpDTO {

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
    private String password;

    private String passwordForCheck;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "비밀번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @Size(min = 2, max = 15, message = "이름은 2~15자 이내로 작성해 주세요.")
    @Column(nullable = false, name = "name")
    private String name;

    public boolean isCheckingPassWordMatch() {
        return password.equals(passwordForCheck);
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
