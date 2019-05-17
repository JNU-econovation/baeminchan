package codesquad.web;

import codesquad.domain.member.MemberLoginDto;
import codesquad.domain.member.MemberRequestDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiMemberAcceptanceTest extends AcceptanceTest {

    public MemberRequestDto defaultMemberRequestDto() {
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setEmailId("esp2ar0");
        memberRequestDto.setEmailDomain("gmail.com");
        memberRequestDto.setPassword("q12345678");
        memberRequestDto.setPassword2("q12345678");
        memberRequestDto.setName("창환");
        memberRequestDto.setCell1("010");
        memberRequestDto.setCell2("1234");
        memberRequestDto.setCell3("5678");
        return memberRequestDto;
    }

    public MemberLoginDto defaultMemberLoginDto() {
        MemberLoginDto memberLoginDto = new MemberLoginDto();
        memberLoginDto.setMemberId("esp2ar0@gmail.com");
        memberLoginDto.setPassword("q12345678");
        return memberLoginDto;
    }

    @Test
    public void create() {
        String location = createResource("/api/members", defaultMemberRequestDto());
        assertThat(location).isEqualTo("/");
    }

    @Test
    public void login() {
        createResource("/api/members", defaultMemberRequestDto());

        ResponseEntity<Void> response = template().postForEntity("/api/members/login", defaultMemberLoginDto(), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}
