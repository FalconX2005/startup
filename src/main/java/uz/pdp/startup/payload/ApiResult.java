package uz.pdp.startup.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private T data;
    private boolean success;
    private String message;

    public ApiResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(true,null,result);
    }

    public static <T> ApiResult<T> success( String message) {
        return new ApiResult<>(true,message,null);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(false,message,null);
    }

}
