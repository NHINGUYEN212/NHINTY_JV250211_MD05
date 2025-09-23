package ra.tsu_jv250211_md05_session05.model.dto;

import ra.tsu_jv250211_md05_session05.model.entity.Course;
import ra.tsu_jv250211_md05_session05.model.entity.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnrollmentDTO {
    private long studentId;
    private long courseId;

}
