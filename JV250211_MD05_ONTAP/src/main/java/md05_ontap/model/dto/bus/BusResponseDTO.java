package md05_ontap.model.dto.bus;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusResponseDTO {
    private int busId;
    private String busName;
    private String registrationNumber;
    private int totalSeats;
    private String imageBus;
    private String status;
}
