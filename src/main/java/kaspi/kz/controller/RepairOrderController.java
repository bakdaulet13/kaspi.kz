package kaspi.kz.controller;


import jakarta.validation.Valid;
import kaspi.kz.dto.RepairOrderCreateDto;
import kaspi.kz.dto.RepairOrderDto;
import kaspi.kz.dto.RepairOrderUpdateDto;
import kaspi.kz.model.enums.RepairOrderStatus;
import kaspi.kz.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class RepairOrderController {
    private final RepairOrderService repairOrderService;

    @Autowired
    public RepairOrderController(RepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRepairOrder(@RequestBody @Valid RepairOrderCreateDto repairOrderCreateDto) {
        repairOrderService.create(repairOrderCreateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRepairOrder(@RequestBody @Valid RepairOrderUpdateDto repairOrderUpdateDto) {
        repairOrderService.send(repairOrderUpdateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username")
    public ResponseEntity<List<RepairOrderDto>> getRepairOrder(@RequestParam("username") String username) {
        return ResponseEntity.ok(repairOrderService.getAllRepairOrdersByUsername(username));
    }

    @GetMapping("/status")
    public ResponseEntity<List<RepairOrderDto>> getRepairOrderByStatus(@RequestParam("status") RepairOrderStatus repairOrderStatus) {
        return ResponseEntity.ok(repairOrderService.getAllRepairOrdersByStatus(repairOrderStatus));
    }
}
