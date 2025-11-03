package md05_ontap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private int httpStatus;

    //Tạo một response thành công với mã HTTP tùy chỉnh.
    public static <T> ResponseWrapper<T> success(T data, String message, HttpStatus status) {
        return new ResponseWrapper<>(true, message, data, status.value());
    }
    //Tạo một response thành công với mã HTTP 200 (OK) mặc định
    public static <T> ResponseWrapper<T> success(T data, String message) {
        return new ResponseWrapper<>(true, message, data, HttpStatus.OK.value());
    }
    //Tạo một response lỗi
    public static <T> ResponseWrapper<T> error(String message, HttpStatus status) {
        return new ResponseWrapper<>(false, message, null, status.value());
    }
}
