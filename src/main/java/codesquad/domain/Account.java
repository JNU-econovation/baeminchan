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
    @Email
    @Column(nullable = false, name = "email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
    @Column(nullable = false, name = "password")
    private String password;

    @Size(min = 2, max = 15)
    @Column(nullable = false, name = "name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
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
