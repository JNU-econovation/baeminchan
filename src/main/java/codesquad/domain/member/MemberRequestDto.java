package codesquad.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "이메일을 입력해주세요")
    private String emailId;

    @NotBlank(message = "이메일을 입력해주세요")
    private String emailDomain;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$", message = "8~16자리의 영문, 숫자 조합이어야 합니다")
    private String password;

    private String password2;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "휴대전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{2,3}", message = "휴대전화번호 양식을 지켜주세요")
    private String cell1;

    @NotBlank(message = "휴대전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{3,4}", message = "휴대전화번호 양식을 지켜주세요")
    private String cell2;

    @NotBlank(message = "휴대전화번호를 입력해주세요")
    @Pattern(regexp = "[0-9]{4}", message = "휴대전화번호 양식을 지켜주세요")
    private String cell3;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다")
    private boolean isValidPassword() {
        if(password == null) {
            return false;
        }
        return password.equals(password2);
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
        isValidPassword();
    }

    public Member toEntity() {
        return Member.builder()
                .memberId(getMemberId())
                .password(password)
                .name(name)
                .cell1(cell1)
                .cell2(cell2)
                .cell3(cell3)
                .build();
    }

    private String getMemberId() {
        return emailId + '@' + emailDomain;
    }
}