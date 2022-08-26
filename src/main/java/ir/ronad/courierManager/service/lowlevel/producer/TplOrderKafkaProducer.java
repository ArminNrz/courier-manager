package ir.ronad.courierManager.service.lowlevel.producer;

import ir.ronad.courierManager.config.kafka.BaseKafkaProducer;
import ir.ronad.courierManager.dto.commonEvent.event.Topics;
import ir.ronad.courierManager.dto.commonEvent.event.entity.TplOrderEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

@Component
public class TplOrderKafkaProducer extends BaseKafkaProducer<TplOrderEvent> {

    public TplOrderKafkaProducer(KafkaProperties kafkaProperties) {
        super(Topics.TPL_ORDER_PRE, kafkaProperties);
    }
}
