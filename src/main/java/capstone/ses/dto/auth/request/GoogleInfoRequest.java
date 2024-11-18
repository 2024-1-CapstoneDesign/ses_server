package capstone.ses.dto.auth.request;

public record GoogleInfoRequest(
        String accessToken
) {
    public static GoogleInfoRequest of(String accessToken) {
        return new GoogleInfoRequest(accessToken);
    }
}
