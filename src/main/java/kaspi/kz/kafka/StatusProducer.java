package kaspi.kz.kafka;

import kaspi.kz.dto.RepairOrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StatusProducer {
    private final KafkaTemplate<String, RepairOrderUpdateDto> kafkaTemplate;

    @Autowired
    public StatusProducer(KafkaTemplate<String, RepairOrderUpdateDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceStatus(RepairOrderUpdateDto repairOrderUpdateDto) {
        kafkaTemplate.send("repair-order-status", repairOrderUpdateDto);
    }
}
