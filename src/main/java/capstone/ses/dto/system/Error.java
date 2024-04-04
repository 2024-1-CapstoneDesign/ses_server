package capstone.ses.dto.system;

import lombok.Getter;

@Getter
public class Error {
    private String field;
    private String value;
    private String reason;

    public Error(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public Error(String field, String value) {
        this.field = field;
        this.value = value;
    }
}