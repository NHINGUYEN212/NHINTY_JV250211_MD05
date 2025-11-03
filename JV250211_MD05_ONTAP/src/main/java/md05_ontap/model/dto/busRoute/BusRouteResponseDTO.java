package md05_ontap.model.dto.busRoute;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusRouteResponseDTO {

    private int busRouteId;
    private String startPoint;
    private String endPoint;
    private String tripInformation;
    private String driverName;
    private String status;

    private int busId;
    private String busName;
    private String registrationNumber;
}
