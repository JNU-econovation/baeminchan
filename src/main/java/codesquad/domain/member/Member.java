package codesquad.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cell1;

    @Column(nullable = false)
    private String cell2;

    @Column(nullable = false)
    private String cell3;

    @Builder
    public Member(String memberId, String password, String name, String cell1, String cell2, String cell3) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
    }
}
