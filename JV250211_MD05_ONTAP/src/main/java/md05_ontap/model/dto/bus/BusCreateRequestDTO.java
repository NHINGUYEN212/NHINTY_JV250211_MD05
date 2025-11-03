package md05_ontap.model.dto.bus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import md05_ontap.validate.FileNotBlank;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusCreateRequestDTO {
    // Yêu cầu: Validate tên xe buýt không được trùng (sẽ xử lý ở Service)
    @NotBlank(message = "Tên xe buýt không được để trống")
    private String busName;

    @NotBlank(message = "Số đăng ký không được để trống")
    private String registrationNumber;

    @NotNull(message = "Tổng số ghế không được để trống")
    @Min(value = 1, message = "Tổng số ghế phải lớn hơn 0")
    private Integer totalSeats;
    @FileNotBlank(message = "File ảnh không được để trống")
    private MultipartFile imageBus;
}
