package md05_ontap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md05_ontap.model.dto.ResponseWrapper;
import md05_ontap.model.dto.busRoute.BusRouteRequestDTO;
import md05_ontap.model.dto.busRoute.BusRouteResponseDTO;
import md05_ontap.service.BusRouteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bus-routes")
@RequiredArgsConstructor
public class BusRouteController {

    private final BusRouteService busRouteService;

    /**
     * 1. Lấy tất cả chuyến đi (Đã sắp xếp)
     */
    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<BusRouteResponseDTO> responseDTOS = busRouteService.findAll(pageable);
        return new ResponseEntity<>(
                ResponseWrapper.<Page<BusRouteResponseDTO>>builder()
                        .success(true)
                        .message("Get all bus routes success")
                        .data(responseDTOS)
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    /**
     * 2. Thêm mới chuyến đi
     * Dùng @ModelAttribute vì đây là chuẩn (mặc dù không có file upload,
     * dùng @RequestBody cũng được, nhưng @ModelAttribute nhất quán với BusController)
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper<BusRouteResponseDTO>> create(
            @Valid @ModelAttribute BusRouteRequestDTO requestDTO) {

        BusRouteResponseDTO responseDTO = busRouteService.save(requestDTO);
        return new ResponseEntity<>(
                ResponseWrapper.<BusRouteResponseDTO>builder()
                        .success(true)
                        .message("Create bus route success")
                        .data(responseDTO)
                        .httpStatus(HttpStatus.CREATED.value())
                        .build(),
                HttpStatus.CREATED
        );
    }

    /**
     * 3. Cập nhật chuyến đi
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<BusRouteResponseDTO>> update(
            @Valid @ModelAttribute BusRouteRequestDTO requestDTO,
            @PathVariable int id) {

        BusRouteResponseDTO responseDTO = busRouteService.update(requestDTO, id);
        return new ResponseEntity<>(
                ResponseWrapper.<BusRouteResponseDTO>builder()
                        .success(true)
                        .message("Update bus route success")
                        .data(responseDTO)
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    /**
     * 4. Xóa (xóa mềm) chuyến đi
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable int id) {
        busRouteService.delete(id);
        return new ResponseEntity<>(
                ResponseWrapper.<Void>builder()
                        .success(true)
                        .message("Delete bus route success")
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    /**
     * 5. Tìm kiếm chuyến đi
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String startPoint,
            @RequestParam(required = false) String endPoint,
            @RequestParam(required = false) Integer id,
            Pageable pageable) {

        Page<BusRouteResponseDTO> responseDTOS = busRouteService.search(startPoint, endPoint, id, pageable);
        return new ResponseEntity<>(
                ResponseWrapper.<Page<BusRouteResponseDTO>>builder()
                        .success(true)
                        .message("Search bus routes success")
                        .data(responseDTOS)
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

//    6. Tìm chính xác theo điạ điểm
@GetMapping("/search/exact")
public ResponseEntity<ResponseWrapper<List<BusRouteResponseDTO>>> searchExact(
        @RequestParam(required = false) String startPoint,
        @RequestParam(required = false) String endPoint,
        @RequestParam(required = false) Integer id) {

    // Gọi hàm 'search1' của bạn
    List<BusRouteResponseDTO> responseDTOS = busRouteService.search1(startPoint, endPoint, id);

    return new ResponseEntity<>(
            ResponseWrapper.<List<BusRouteResponseDTO>>builder()
                    .success(true)
                    .message("Search exact bus routes success (List)")
                    .data(responseDTOS)
                    .httpStatus(HttpStatus.OK.value())
                    .build(),
            HttpStatus.OK
    );
}

}
