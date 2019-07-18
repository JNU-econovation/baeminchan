package codesquad.domain;

import codesquad.dto.SignUpDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    public static final int USER = 0;
    public static final int ADMIN = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Size(min = 2, max = 15, message = "이름은 2~15자 이내로 작성해 주세요.")
    @Column(nullable = false, name = "name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "비밀번호 형식이 올바르지 않습니다.")
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false, name = "account_type")
    private int AccountType;

    public Account(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.AccountType = USER;
    }

    public Account(SignUpDTO signUpDTO) {
        this.email = signUpDTO.getEmail();
        this.password = signUpDTO.getPassword();
        this.name = signUpDTO.getName();
        this.phoneNumber = signUpDTO.getPhoneNumber();
        this.AccountType = USER;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
