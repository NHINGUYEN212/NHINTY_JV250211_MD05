package md05_ontap.service;

import md05_ontap.model.dto.bus.BusCreateRequestDTO;
import md05_ontap.model.dto.bus.BusResponseDTO;
import md05_ontap.model.dto.bus.BusUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BusService {
    Page<BusResponseDTO> findAll(Pageable pageable);
    BusResponseDTO save(BusCreateRequestDTO createRequestDTO);
    void delete(int busId);
    BusResponseDTO update(BusUpdateRequestDTO updateRequestDTO, int busId);

//  Tim kiem
    Page<BusResponseDTO> search(Integer id, String busName,Boolean status, Pageable pageable);
}
