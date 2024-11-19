package capstone.ses.controller;

import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public Result login(@RequestParam(value="accessToken") String accessToken) {
        try {
            return new Result<>(ResultCode.SUCCESS, authService.loginByAccessToken(accessToken));
        } catch (Exception e) {
            return new Result<>(ResultCode.FAIL, e.getMessage(), "401");
        }
    }
}
