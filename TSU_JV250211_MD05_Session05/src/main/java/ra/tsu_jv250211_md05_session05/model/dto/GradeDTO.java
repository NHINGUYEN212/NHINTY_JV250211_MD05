package ra.tsu_jv250211_md05_session05.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GradeDTO {
    private long studentId;
    private long courseId;
    private Double score;
}