package ra.tsu_jv250211_md05_session04.model.dto;

import ra.tsu_jv250211_md05_session04.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {
    private String customerName;
    private String phoneNumber;
    private String address;

}
