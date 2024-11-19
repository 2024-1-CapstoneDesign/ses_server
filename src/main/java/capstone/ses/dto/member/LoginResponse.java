package capstone.ses.dto.member;

import capstone.ses.dto.jwt.AuthTokens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private AuthTokens authTokens;
    private MemberResponse memberResponse;
}
