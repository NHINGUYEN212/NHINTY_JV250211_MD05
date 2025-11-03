package md05_ontap.repository;

import md05_ontap.model.entity.BusRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRouteRepository extends JpaRepository<BusRoute, Integer> {

    /**
     * Dùng cho chức năng "Xóa xe buýt" (BusService)
     * Kiểm tra xem một Bus (qua busId) đã có chuyến đi nào chưa.
     */
    boolean existsByBus_BusId(int busId);

    /**
     * Hiển thị tất cả danh sách chuyến đi được sắp xếp theo điểm bắt đầu.
     */
    Page<BusRoute> findAllByOrderByStartPointAsc(Pageable pageable);

    /**
     * Yêu cầu đề bài: Tìm kiếm chuyến đi theo điểm bắt đầu hoặc điểm kết thúc.
     * (Containing = tương đương LIKE %keyword%)
     */
    Page<BusRoute> findByStartPointContainingOrEndPointContaining(String startPoint, String endPoint, Pageable pageable);


//    Tim kiem chính xac bắt đầu và kết thúc
    List<BusRoute> findByStartPointAndEndPoint(String startPoint, String endPoint);

    // THÊM HÀM NÀY (Chỉ tìm startPoint)
    Page<BusRoute> findByStartPointContaining(String startPoint, Pageable pageable);

    // THÊM HÀM NÀY (Chỉ tìm endPoint)
    Page<BusRoute> findByEndPointContaining(String endPoint, Pageable pageable);
}
