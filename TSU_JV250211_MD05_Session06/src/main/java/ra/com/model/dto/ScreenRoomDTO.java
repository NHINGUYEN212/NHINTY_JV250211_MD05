package ra.com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ScreenRoomDTO {
    private String name;
    private Integer capacity;
    private String type;
    private Long cinemaId;
}
