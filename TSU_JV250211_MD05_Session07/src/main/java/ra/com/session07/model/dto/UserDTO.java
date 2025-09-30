package ra.com.session07.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.com.session07.model.constant.Role;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private Role role;
}
