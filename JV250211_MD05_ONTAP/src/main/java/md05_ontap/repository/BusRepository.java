package md05_ontap.repository;

import md05_ontap.model.dto.bus.BusResponseDTO;
import md05_ontap.model.entity.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Integer> {
    // validate tên xe buýt không được trùng
    boolean existsByBusName(String busName);
    boolean existsByRegistrationNumber(String registrationNumber);
    // validate khi cập nhật (trùng tên nhưng khác ID)
    boolean existsByBusNameAndBusIdNot(String busName, int busId);
    boolean existsByRegistrationNumberAndBusIdNot(String registrationNumber, int busId);

//    Tim kiem theo id co san chi can dung trong service khong can khai bao
//    Tim kiem theo ten
    Page<Bus> findByBusNameContainingIgnoreCase(String busName, Pageable pageable);

    Page<Bus> findByStatus(Boolean status, Pageable pageable);

}
