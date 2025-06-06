package kaspi.kz.repository;

import kaspi.kz.model.RepairOrder;
import kaspi.kz.model.enums.RepairOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Long> {

    List<RepairOrder> findAllByUsername(String username);

    List<RepairOrder> findAllByStatus(RepairOrderStatus status);
}
