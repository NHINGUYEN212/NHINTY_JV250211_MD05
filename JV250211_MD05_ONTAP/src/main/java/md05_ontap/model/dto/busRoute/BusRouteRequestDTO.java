package md05_ontap.model.dto.busRoute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BusRouteRequestDTO {
    // Yêu cầu: Validate dữ liệu đầu vào theo bảng mô tả
    @NotBlank(message = "Điểm đầu không được để trống")
    private String startPoint;

    @NotBlank(message = "Điểm cuối không được để trống")
    private String endPoint;

    @NotBlank(message = "Thông tin chuyến đi không được để trống")
    private String tripInformation;

    @NotBlank(message = "Tên lái xe không được để trống")
    private String driverName;

    @NotNull(message = "Mã xe buýt (bus_id) không được để trống")
    private Integer busId;
    private Boolean status;
}
