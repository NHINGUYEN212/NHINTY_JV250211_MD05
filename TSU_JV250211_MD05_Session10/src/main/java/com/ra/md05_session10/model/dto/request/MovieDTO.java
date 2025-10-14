package com.ra.md05_session10.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieDTO {
    @NotBlank(message = "title can not blank")
    private String title;
    @NotNull(message = "duration can not null")
    private int duration;
    @NotBlank(message = "genre can not blank")
    private String genre;
    @NotNull(message = "release date can not null")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;

    private MultipartFile poster;
}
