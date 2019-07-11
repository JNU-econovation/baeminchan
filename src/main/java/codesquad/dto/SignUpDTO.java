package codesquad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpDTO {
    private String email;
    private String password;
    private String passwordForCheck;
    private String phoneNumber;
    private String name;

    public boolean isCheckingPassWordMatch() {
        return password.equals(passwordForCheck);
    }
}
