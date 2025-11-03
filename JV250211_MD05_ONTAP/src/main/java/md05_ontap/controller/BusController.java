package md05_ontap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md05_ontap.model.dto.ResponseWrapper;
import md05_ontap.model.dto.bus.BusCreateRequestDTO;
import md05_ontap.model.dto.bus.BusResponseDTO;
import md05_ontap.model.dto.bus.BusUpdateRequestDTO;
import md05_ontap.service.BusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<BusResponseDTO> busResponseDTOS = busService.findAll(pageable);

        return new ResponseEntity<>(ResponseWrapper.builder()
                .success(true)
                .message("Get all bus success (paginated)")
                .data(busResponseDTOS)
                .httpStatus(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<BusResponseDTO>> create(@Valid @ModelAttribute BusCreateRequestDTO busRequestDTO) {
        BusResponseDTO busResponseDTO = busService.save(busRequestDTO);

        return new ResponseEntity<>(
                ResponseWrapper.<BusResponseDTO>builder()
                        .success(true)
                        .message("Create bus success")
                        .data(busResponseDTO)
                        .httpStatus(HttpStatus.CREATED.value())
                        .build(),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Integer id) { // (Sửa nhỏ: Dùng Integer)
        busService.delete(id);

        return new ResponseEntity<>(
                ResponseWrapper.<Void>builder()
                        .success(true)
                        .message("Delete bus success")
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<BusResponseDTO>> update(
            @Valid @ModelAttribute BusUpdateRequestDTO updateRequestDTO,
            @PathVariable int id) {

        BusResponseDTO busResponseDTO = busService.update(updateRequestDTO, id);

        return new ResponseEntity<>(
                ResponseWrapper.<BusResponseDTO>builder()
                        .success(true)
                        .message("Update bus success")
                        .data(busResponseDTO)
                        .httpStatus(HttpStatus.OK.value())
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) Integer busId,
            @RequestParam(required = false) String busName,
            @RequestParam(required = false) Boolean status,
            Pageable pageable) {

        Page<BusResponseDTO> busResponseDTOS = busService.search(busId, busName, status, pageable);

        return new ResponseEntity<>(ResponseWrapper.builder()
                .success(true)
                .message("Search bus success")
                .data(busResponseDTOS)
                .httpStatus(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}