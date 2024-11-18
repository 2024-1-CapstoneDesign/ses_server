package capstone.ses.dto.auth.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleInfoResponse(
        String id,
        String name,
        String email,
        String picture
) {
    public static GoogleInfoResponse of(String id, String name, String email, String picture) {
        return new GoogleInfoResponse(id, name, email, picture);
    }
}
