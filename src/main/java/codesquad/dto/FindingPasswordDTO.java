package codesquad.dto;

import codesquad.utils.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindingPasswordDTO {

    @NotBlank
    String email;

    @NotBlank
    String name;

    @Pattern(regexp = RegexUtil.REGEX_PHONE_NUMBER)
    String phoneNumber;
}
