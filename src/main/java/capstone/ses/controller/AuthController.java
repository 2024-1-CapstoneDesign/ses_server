package capstone.ses.controller;

import capstone.ses.auth.jwt.JwtTokenProvider;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login")
    public Result login(@RequestParam(value="accessToken") String accessToken) {
        try {
            return new Result<>(ResultCode.SUCCESS, authService.loginByAccessToken(accessToken));
        } catch (Exception e) {
            return new Result<>(ResultCode.FAIL, e.getMessage(), "401");
        }
    }

    @GetMapping("/test/token")
    public Result issueTestToken() {
        try {
            return new Result<>(ResultCode.SUCCESS, authService.issueTestToken());
        } catch (Exception e) {
            return new Result(ResultCode.FAIL, "400");
        }
    }

//    @GetMapping("/test/member")
//    public Result getMemberEmail(@RequestHeader(value = "Authorization") String accessToken) {
//        try {
//            System.out.println("accessToken: " + accessToken);
//            return new Result(ResultCode.SUCCESS, jwtTokenProvider.getEmailFromAccessToken(accessToken));
//        } catch (Exception e) {
//            return new Result(ResultCode.FAIL, e.getMessage(), "500");
//        }
//    }
}
