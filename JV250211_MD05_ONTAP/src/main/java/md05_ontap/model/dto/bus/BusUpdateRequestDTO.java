package md05_ontap.model.dto.bus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import md05_ontap.validate.FileNotBlank;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusUpdateRequestDTO {
    private String busName;
    private String registrationNumber;
    @Min(value = 1, message = "Tổng số ghế phải lớn hơn 0")
    private Integer totalSeats;
    private Boolean status;
    private MultipartFile imageBus;
}
