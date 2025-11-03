package md05_ontap.service.impl;

import lombok.RequiredArgsConstructor;
import md05_ontap.exception.HttpNotFound;
import md05_ontap.model.dto.busRoute.BusRouteRequestDTO;
import md05_ontap.model.dto.busRoute.BusRouteResponseDTO;
import md05_ontap.model.entity.Bus;
import md05_ontap.model.entity.BusRoute;
import md05_ontap.repository.BusRepository;
import md05_ontap.repository.BusRouteRepository;
import md05_ontap.service.BusRouteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor // Sử dụng Constructor Injection
public class BusRouteServiceImpl implements BusRouteService {

    // Tiêm các Repository cần thiết
    private final BusRouteRepository busRouteRepository;
    private final BusRepository busRepository; // Cần để tìm Bus khi tạo/cập nhật

    @Override
    public Page<BusRouteResponseDTO> findAll(Pageable pageable) {
        Page<BusRoute> busRoutePage = busRouteRepository.findAllByOrderByStartPointAsc(pageable);
        return busRoutePage.map(this::convertToDTO);
    }

    @Override
    public BusRouteResponseDTO save(BusRouteRequestDTO requestDTO) {
        // 1. Tìm Bus (xe buýt) mà chuyến đi này thuộc về
        Bus bus = busRepository.findById(requestDTO.getBusId())
                .orElseThrow(() -> new HttpNotFound("Không tìm thấy Bus với ID: " + requestDTO.getBusId()));

        // 2. Chuyển DTO -> Entity
        BusRoute busRoute = BusRoute.builder()
                .startPoint(requestDTO.getStartPoint())
                .endPoint(requestDTO.getEndPoint())
                .tripInformation(requestDTO.getTripInformation())
                .driverName(requestDTO.getDriverName())
                .bus(bus) // Gán đối tượng Bus đã tìm
                .status(true) // Yêu cầu: Mặc định là 1 (true) khi thêm mới
                .build();

        // 3. Lưu vào DB
        BusRoute busRouteNew = busRouteRepository.save(busRoute);
        return convertToDTO(busRouteNew);
    }

    @Override
    public BusRouteResponseDTO update(BusRouteRequestDTO requestDTO, int busRouteId) {
        // 1. Tìm chuyến đi (BusRoute) cần cập nhật
        BusRoute busRouteToUpdate = busRouteRepository.findById(busRouteId)
                .orElseThrow(() -> new HttpNotFound("Không tìm thấy BusRoute với ID: " + busRouteId));

        // 2. Kiểm tra xem người dùng có đổi Bus hay không
        Bus bus = busRouteToUpdate.getBus(); // Giữ Bus cũ
        if (bus.getBusId() != requestDTO.getBusId()) {
            // Nếu BusId thay đổi, tìm Bus mới
            bus = busRepository.findById(requestDTO.getBusId())
                    .orElseThrow(() -> new HttpNotFound("Không tìm thấy Bus với ID: " + requestDTO.getBusId()));
        }

        // 3. Cập nhật các trường trên đối tượng đã lấy
        busRouteToUpdate.setStartPoint(requestDTO.getStartPoint());
        busRouteToUpdate.setEndPoint(requestDTO.getEndPoint());
        busRouteToUpdate.setTripInformation(requestDTO.getTripInformation());
        busRouteToUpdate.setDriverName(requestDTO.getDriverName());
        busRouteToUpdate.setBus(bus); // Gán Bus (mới hoặc cũ)

        // 4. Logic cập nhật status (vì DTO dùng 'Boolean' wrapper)
        if (requestDTO.getStatus() != null) {
            busRouteToUpdate.setStatus(requestDTO.getStatus());
        }

        // 5. Lưu vào DB
        BusRoute busRouteUpdated = busRouteRepository.save(busRouteToUpdate);
        return convertToDTO(busRouteUpdated);
    }

    @Override
    public void delete(int busRouteId) {
        // Yêu cầu: Xóa mềm
        BusRoute busRoute = busRouteRepository.findById(busRouteId)
                .orElseThrow(() -> new HttpNotFound("Không tìm thấy BusRoute với ID: " + busRouteId));

        busRoute.setStatus(false); // Cập nhật trạng thái thành 0
        busRouteRepository.save(busRoute);
    }

    /**
     * Helper Method: Chuyển đổi Entity -> ResponseDTO
     */
    private BusRouteResponseDTO convertToDTO(BusRoute busRoute) {
        return BusRouteResponseDTO.builder()
                .busRouteId(busRoute.getBusRouteId())
                .startPoint(busRoute.getStartPoint())
                .endPoint(busRoute.getEndPoint())
                .tripInformation(busRoute.getTripInformation())
                .driverName(busRoute.getDriverName())
                .status(busRoute.getStatus() ? "Hoạt động" : "Không hoạt động")
                .busId(busRoute.getBus().getBusId())
                .busName(busRoute.getBus().getBusName())
                .registrationNumber(busRoute.getBus().getRegistrationNumber())
                .build();
    }

    @Override
    public Page<BusRouteResponseDTO> search(String startPoint, String endPoint, Integer id, Pageable pageable) {

        // Ưu tiên 1: Tìm theo ID (logic này của bạn đã chuẩn)
        if (id != null) {
            return busRouteRepository.findById(id)
                    .map(busRoute -> new PageImpl<>(
                            Collections.singletonList(convertToDTO(busRoute)), pageable, 1))
                    .orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }

        // SỬA LỖI LOGIC: Kiểm tra 3 trường hợp
        boolean hasStart = (startPoint != null && !startPoint.isBlank());
        boolean hasEnd = (endPoint != null && !endPoint.isBlank());

        Page<BusRoute> busRoutesPage;

        if (hasStart && hasEnd) {
            // Case 1: Tìm theo CẢ HAI (Dùng OR)
            busRoutesPage = busRouteRepository.findByStartPointContainingOrEndPointContaining(startPoint, endPoint, pageable);
        } else if (hasStart) {
            // Case 2: Chỉ tìm theo startPoint
            busRoutesPage = busRouteRepository.findByStartPointContaining(startPoint, pageable);
        } else if (hasEnd) {
            // Case 3: Chỉ tìm theo endPoint
            busRoutesPage = busRouteRepository.findByEndPointContaining(endPoint, pageable);
        } else {
            // Case 4: Không có tiêu chí tìm kiếm
            busRoutesPage = Page.empty(pageable);
        }

        return busRoutesPage.map(this::convertToDTO);
    }

    @Override
    public List<BusRouteResponseDTO> search1(String startPoint, String endPoint, Integer id) {
        // Ưu tiên 1: Tìm theo ID (nếu có)
        if (id != null) {
            return busRouteRepository.findById(id)
                    .map(busRoute -> Collections.singletonList(convertToDTO(busRoute)))
                    .orElse(Collections.emptyList());
        }

        // Ưu tiên 2: Tìm chính xác theo CẢ HAI điểm
        // CHỈ TÌM KHI CẢ HAI ĐỀU ĐƯỢC CUNG CẤP (không phải null hay rỗng)
        if (startPoint != null && !startPoint.isBlank() && endPoint != null && !endPoint.isBlank()) {

            // GỌI HÀM MỚI
            return busRouteRepository.findByStartPointAndEndPoint(startPoint, endPoint).stream()
                    .map(this::convertToDTO)
                    .toList();
        }

        // Nếu không có tiêu chí tìm kiếm hợp lệ, trả về danh sách rỗng
        return Collections.emptyList();
    }

}