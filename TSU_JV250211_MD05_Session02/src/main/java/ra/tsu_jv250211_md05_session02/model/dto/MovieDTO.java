package ra.tsu_jv250211_md05_session02.model.dto;




//import com.example.session02.validator.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ra.tsu_jv250211_md05_session02.validator.UniqueTitle;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieDTO {
    @NotBlank
    @UniqueTitle
    private String title;

    @NotBlank
    private String director;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    private Double rating;
}
