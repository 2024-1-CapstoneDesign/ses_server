package capstone.ses.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Result<T> {

    private ResultCode result;
    private T data;
    private String message;
    private String code;

    @Builder
    public Result(ResultCode result, T data, String message, String code) {
        this.result = result;
        this.data = data;
        this.message = message;
        this.code = code;
    }
}
