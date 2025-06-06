package kaspi.kz.service.impl;

import kaspi.kz.dto.RepairOrderCreateDto;
import kaspi.kz.dto.RepairOrderDto;
import kaspi.kz.dto.RepairOrderUpdateDto;
import kaspi.kz.dto.StatusHistoryDto;
import kaspi.kz.kafka.StatusProducer;
import kaspi.kz.model.RepairOrder;
import kaspi.kz.model.StatusHistory;
import kaspi.kz.model.enums.RepairOrderStatus;
import kaspi.kz.repository.RepairOrderRepository;
import kaspi.kz.service.RepairOrderService;
import kaspi.kz.service.StatusHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RepairOrderServiceImp implements RepairOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RepairOrderRepository repairOrderRepository;
    private final StatusHistoryService statusHistoryService;
    private final StatusProducer statusProducer;

    private static final Map<RepairOrderStatus, RepairOrderStatus> allowedTransitions = Map.of(
            RepairOrderStatus.SUBMITTED, RepairOrderStatus.IN_REVIEW,
            RepairOrderStatus.IN_REVIEW, RepairOrderStatus.IN_PROGRESS,
            RepairOrderStatus.IN_PROGRESS, RepairOrderStatus.FINISHED
    );

    @Autowired
    public RepairOrderServiceImp(RepairOrderRepository repairOrderRepository, StatusHistoryService statusHistoryService, StatusProducer statusProducer) {
        this.repairOrderRepository = repairOrderRepository;
        this.statusHistoryService = statusHistoryService;
        this.statusProducer = statusProducer;
    }

    @Override
    public void create(RepairOrderCreateDto repairOrderCreateDto) {
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setUsername(repairOrderCreateDto.clientName());
        repairOrder.setDescription(repairOrderCreateDto.description());

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(RepairOrderStatus.SUBMITTED);
        statusHistory.setCreatedAt(new Date());
        statusHistory.setReason("Repair order created");
        statusHistory.setChangedBy(repairOrderCreateDto.createdBy());
        statusHistory.setRepairOrder(repairOrder);

        repairOrder.setStatus(statusHistory.getStatus());
        repairOrder.addHistory(statusHistory);

        repairOrderRepository.save(repairOrder);
    }

    @Override
    public void send(RepairOrderUpdateDto repairOrderUpdateDto) {
        statusProducer.produceStatus(repairOrderUpdateDto);
    }

    @Override
    public void update(RepairOrderUpdateDto repairOrderUpdateDto) {
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderUpdateDto.id())
                .orElseThrow(() -> new IllegalArgumentException("RepairOrder with id " + repairOrderUpdateDto.id() + " not found"));


        if (!allowedTransitions.containsKey(repairOrder.getStatus())) {
            throw new IllegalStateException("No transition found for status " + repairOrder.getStatus());
        }

        RepairOrderStatus expectedNextStatus = allowedTransitions.get(repairOrder.getStatus());

        if(!repairOrderUpdateDto.status().equals(expectedNextStatus)) {
            throw new IllegalStateException("Invalid status " + repairOrderUpdateDto.status());
        }

        repairOrder.setStatus(repairOrderUpdateDto.status());

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(repairOrder.getStatus());
        statusHistory.setChangedBy(repairOrderUpdateDto.updatedBy());
        statusHistory.setReason(repairOrderUpdateDto.reason());
        statusHistory.setCreatedAt(new Date());
        statusHistory.setRepairOrder(repairOrder);
        repairOrder.addHistory(statusHistory);

        statusHistoryService.save(statusHistory);
        repairOrderRepository.save(repairOrder);

        if(repairOrderUpdateDto.status().equals(RepairOrderStatus.FINISHED)) {
            //you can use websocket or firebase for notification users
            logger.info("Order finished!");
        }
    }

    @Override
    public List<RepairOrderDto> getAllRepairOrdersByUsername(String username) {
        return repairOrderRepository.findAllByUsername(username).stream().map(this::toDto).toList();
    }

    @Override
    public List<RepairOrderDto> getAllRepairOrdersByStatus(RepairOrderStatus repairOrderStatus) {
        return repairOrderRepository.findAllByStatus(repairOrderStatus).stream().map(this::toDto).toList();
    }

    private RepairOrderDto toDto(RepairOrder repairOrder) {
        List<StatusHistoryDto> historyDtoList = repairOrder.getHistoryList()
                .stream()
                .map(h -> new StatusHistoryDto(
                        h.getStatus(),
                        h.getReason(),
                        h.getChangedBy(),
                        h.getCreatedAt()
                ))
                .toList();

        return new RepairOrderDto(
                repairOrder.getId(),
                repairOrder.getUsername(),
                repairOrder.getDescription(),
                repairOrder.getStatus(),
                historyDtoList
        );
    }
}
