package codesquad.dto;

import codesquad.utils.ExceptionMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = ExceptionMessages.NO_EMAIL)
    private String email;

    @NotBlank(message = ExceptionMessages.NO_PASSWORD)
    private String password;
}
