package md05_ontap.service.impl;

import lombok.RequiredArgsConstructor;
import md05_ontap.exception.HttpConflict;
import md05_ontap.exception.HttpNotFound;
import md05_ontap.model.dto.bus.BusCreateRequestDTO;
import md05_ontap.model.dto.bus.BusResponseDTO;
import md05_ontap.model.dto.bus.BusUpdateRequestDTO;
import md05_ontap.model.entity.Bus;
import md05_ontap.repository.BusRepository;
import md05_ontap.repository.BusRouteRepository;
import md05_ontap.service.BusService;
import md05_ontap.service.UploadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final UploadService uploadService;

    private final BusRouteRepository busRouteRepository;

    @Override
    public Page<BusResponseDTO> findAll(Pageable pageable) {
        Page<Bus> busPage = busRepository.findAll(pageable);
        return busPage.map(this::convertToDTO);
    }

    @Override
    public BusResponseDTO save(BusCreateRequestDTO createRequestDTO) {
        if (busRepository.existsByBusName(createRequestDTO.getBusName())) {
            throw new HttpConflict(createRequestDTO.getBusName() + " already exists");
        }
        if (busRepository.existsByRegistrationNumber(createRequestDTO.getRegistrationNumber())) {
            throw new HttpConflict(createRequestDTO.getRegistrationNumber() + " already exists");
        }

        String busImage = uploadService.uploadFile(createRequestDTO.getImageBus());

        Bus bus = Bus.builder()
                .busName(createRequestDTO.getBusName())
                .registrationNumber(createRequestDTO.getRegistrationNumber())
                .totalSeats(createRequestDTO.getTotalSeats())
                .imageBus(busImage)
                .status(true) // Luôn là 'true' khi tạo mới
                .build();

        Bus busNew = busRepository.save(bus);
        return convertToDTO(busNew);
    }

    @Override
    public void delete(int busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new HttpNotFound("Bus not found"));

        if (busRouteRepository.existsByBus_BusId(busId)) {
            throw new HttpConflict("Không thể xóa xe buýt " + busId + " vì đã có lịch trình.");
        }
        bus.setStatus(false); // Xóa mềm
        busRepository.save(bus);
    }

    @Override
    public BusResponseDTO update(BusUpdateRequestDTO updateRequestDTO, int busId) {

        Bus busToUpdate = busRepository.findById(busId)
                .orElseThrow(() -> new HttpNotFound("Bus not found"));

        // ----- LOGIC CẬP NHẬT TỪNG PHẦN (PATCH) -----

        // 1. Cập nhật busName (nếu được cung cấp)
        if (updateRequestDTO.getBusName() != null && !updateRequestDTO.getBusName().isBlank()) {
            // (Kiểm tra unique cho tên mới)
            if (busRepository.existsByBusNameAndBusIdNot(updateRequestDTO.getBusName(), busId)) {
                throw new HttpConflict(updateRequestDTO.getBusName() + " already exists");
            }
            busToUpdate.setBusName(updateRequestDTO.getBusName());
        }

        // 2. Cập nhật registrationNumber (nếu được cung cấp)
        if (updateRequestDTO.getRegistrationNumber() != null && !updateRequestDTO.getRegistrationNumber().isBlank()) {
            // (Kiểm tra unique cho số đăng ký mới)
            if (busRepository.existsByRegistrationNumberAndBusIdNot(updateRequestDTO.getRegistrationNumber(), busId)) {
                throw new HttpConflict(updateRequestDTO.getRegistrationNumber() + " already exists");
            }
            busToUpdate.setRegistrationNumber(updateRequestDTO.getRegistrationNumber());
        }

        // 3. Cập nhật totalSeats (nếu được cung cấp)
        if (updateRequestDTO.getTotalSeats() != null) {
            busToUpdate.setTotalSeats(updateRequestDTO.getTotalSeats());
        }

        // 4. Cập nhật status (nếu được cung cấp)
        if (updateRequestDTO.getStatus() != null) {
            busToUpdate.setStatus(updateRequestDTO.getStatus());
        }

        // 5. Cập nhật ảnh (nếu được cung cấp)
        if (updateRequestDTO.getImageBus() != null && !updateRequestDTO.getImageBus().isEmpty()) {
            String busImage = uploadService.uploadFile(updateRequestDTO.getImageBus());
            busToUpdate.setImageBus(busImage);
        }

        // (Nếu không gửi ảnh mới, 'imageBus' cũ sẽ được giữ nguyên)

        Bus busNew = busRepository.save(busToUpdate);
        return convertToDTO(busNew);
    }

    // TẠO HELPER METHOD
    private BusResponseDTO convertToDTO(Bus bus) {
        return BusResponseDTO.builder()
                .busId(bus.getBusId())
                .busName(bus.getBusName())
                .imageBus(bus.getImageBus())
                .totalSeats(bus.getTotalSeats())
                .registrationNumber(bus.getRegistrationNumber())
                .status(bus.getStatus() ? "Hoạt động" : "Không hoạt động")
                .build();
    }

//    Tim kiem
@Override
public Page<BusResponseDTO> search(Integer busId, String busName,Boolean status, Pageable pageable) {
    Page<Bus> busPage;
    // Tim theo id
    if (busId != null) {
        List<Bus> busList = busRepository.findById(busId)
                .map(List::of)
                .orElse(List.of());
        busPage = new PageImpl<>(busList, pageable, busList.size());

    } else if (busName != null && !busName.isBlank()) {
        // Tìm theo Tên
        busPage = busRepository.findByBusNameContainingIgnoreCase(busName, pageable);

    } else if (status!=null) {
        busPage = busRepository.findByStatus(status, pageable);
    } else {
        busPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
    }
    return busPage.map(this::convertToDTO);
}
}
