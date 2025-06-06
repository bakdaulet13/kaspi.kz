package kaspi.kz.config;

import kaspi.kz.dto.RepairOrderUpdateDto;
import kaspi.kz.kafka.StatusConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {
    Logger logger = LoggerFactory.getLogger(StatusConsumer.class);

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, RepairOrderUpdateDto> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (r, e) -> {
                    logger.info("Sending record with key {} to DLT topic {}-dlt due to {}",
                            r.key(), r.topic(), e.getMessage());
                    return new org.apache.kafka.common.TopicPartition(r.topic() + "-dlt", r.partition());
                });

        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3);

        return new DefaultErrorHandler(recoverer, fixedBackOff);
    }
}
