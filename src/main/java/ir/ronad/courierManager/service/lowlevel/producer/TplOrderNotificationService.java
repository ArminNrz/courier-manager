package ir.ronad.courierManager.service.lowlevel.producer;

import ir.ronad.courierManager.dto.commonEvent.event.entity.EntityEventType;
import ir.ronad.courierManager.dto.commonEvent.event.entity.TplOrderEvent;
import ir.ronad.courierManager.dto.commonEvent.event.entity.tplorder.TplOrderDetail;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.service.data.SendNotificationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TplOrderNotificationService {

    private final TplOrderKafkaProducer kafkaProducer;
    private final TplOrderMapper tplOrderMapper;

    public void sendToBus(SendNotificationData notificationData) {
        log.trace("Enter: TplOrderNotificationService.sendToBus({})", notificationData);

        TplOrderEvent event = buildEventDTO(notificationData);

        try {
            log.debug("Try to send orderEvent: {}, to kafka queue with eventType: {}", event, event.getType());
            kafkaProducer.produceEvent(event);
            log.info("Send orderEvent: {}, with eventType: {} to kafka queue", event, event.getType());
        } catch (Exception e) {
            log.error("There is an error in sending orderEvent with id: {}", event.getOrderId());
            e.printStackTrace();
        }
    }

    private TplOrderEvent buildEventDTO(SendNotificationData notificationData) {
        log.trace("Enter: TplOrderNotificationService.buildEventDTO({})", notificationData);
        List<TplOrderDetail> eventDetails = notificationData.getOtherTplOrderEntities().stream()
                .map(tplOrderMapper::toEventDetails)
                .collect(Collectors.toList());

        TplOrderEvent event = tplOrderMapper.toEvent(notificationData);
        event.setType(EntityEventType.STATUS_UPDATE);
        event.setOrderDetails(eventDetails);
        event.setTime(ZonedDateTime.now());
        log.trace("Exit: TplOrderNotificationService.buildEventDTO : TplOrderEvent: {}", event);
        return event;
    }
}
