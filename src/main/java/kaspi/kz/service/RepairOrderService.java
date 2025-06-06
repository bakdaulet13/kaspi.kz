package kaspi.kz.service;

import kaspi.kz.dto.RepairOrderCreateDto;
import kaspi.kz.dto.RepairOrderDto;
import kaspi.kz.dto.RepairOrderUpdateDto;
import kaspi.kz.model.enums.RepairOrderStatus;

import java.util.List;


public interface RepairOrderService {
    void create(RepairOrderCreateDto repairOrderCreateDto);
    void send(RepairOrderUpdateDto repairOrderUpdateDto);
    void update(RepairOrderUpdateDto repairOrderUpdateDto);
    List<RepairOrderDto> getAllRepairOrdersByUsername(String username);
    List<RepairOrderDto> getAllRepairOrdersByStatus(RepairOrderStatus repairOrderStatus);

}
