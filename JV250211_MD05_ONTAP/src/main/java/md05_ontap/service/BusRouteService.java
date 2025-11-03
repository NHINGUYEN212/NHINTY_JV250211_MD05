package md05_ontap.service;

import md05_ontap.model.dto.busRoute.BusRouteRequestDTO;
import md05_ontap.model.dto.busRoute.BusRouteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusRouteService {
    Page<BusRouteResponseDTO> findAll(Pageable pageable);
    BusRouteResponseDTO save(BusRouteRequestDTO requestDTO);
    BusRouteResponseDTO update(BusRouteRequestDTO requestDTO, int busRouteId);
    void delete(int busRouteId);
//    Tim kiem tuong doi diem dau hoac diem cuoi
    Page<BusRouteResponseDTO> search(String startPoint, String endPoint, Integer id, Pageable pageable);
    //    Tim kiem tuong doi diem dau hoac diem cuoi
    List<BusRouteResponseDTO> search1(String startPoint, String endPoint, Integer id);
}