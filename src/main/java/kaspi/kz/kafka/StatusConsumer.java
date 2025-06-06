package kaspi.kz.kafka;

import kaspi.kz.dto.RepairOrderUpdateDto;
import kaspi.kz.service.RepairOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StatusConsumer {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RepairOrderService repairOrderService;

    @Autowired
    public StatusConsumer(RepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    @KafkaListener(topics = "repair-order-status", groupId = "sto")
    public void consumeStatus(RepairOrderUpdateDto repairOrderUpdateDto) {
        try {
            repairOrderService.update(repairOrderUpdateDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
