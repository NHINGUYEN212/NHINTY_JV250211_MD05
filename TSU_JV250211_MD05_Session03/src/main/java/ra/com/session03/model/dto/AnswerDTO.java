package ra.com.session03.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AnswerDTO {
    @NotBlank(message = "content can not blank")
    private String content;
    @NotNull
    private boolean correct;
}