package codesquad.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String memberId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public MemberLoginDto(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberLoginDto that = (MemberLoginDto) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, password);
    }
}
