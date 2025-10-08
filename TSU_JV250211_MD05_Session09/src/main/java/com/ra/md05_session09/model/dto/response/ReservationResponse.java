package com.ra.md05_session09.model.dto.response;

import com.ra.md05_session09.model.constant.Status;
import com.ra.md05_session09.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReservationResponse {
    private Long id;
    private String customerName;
    private List<String> roomNames;
    private Status status;
}
