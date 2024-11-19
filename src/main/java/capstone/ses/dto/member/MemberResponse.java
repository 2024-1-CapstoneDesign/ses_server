package capstone.ses.dto.member;

import capstone.ses.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberResponse {
    private String name;
    private String email;
    private String profile;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profile(member.getPicture())
                .build();
    }
}
