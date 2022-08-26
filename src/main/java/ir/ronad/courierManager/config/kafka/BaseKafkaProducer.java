package ir.ronad.courierManager.config.kafka;

import ir.ronad.courierManager.dto.commonEvent.event.BaseEvent;
import ir.ronad.courierManager.dto.commonEvent.event.entity.BaseEntityEvent;
import ir.ronad.courierManager.dto.commonEvent.event.entity.TplOrderEvent;
import ir.ronad.courierManager.dto.commonEvent.event.inventory.InventoryEvent;
import ir.ronad.courierManager.dto.commonEvent.event.inventory.WarehouseInventoryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class BaseKafkaProducer<E extends BaseEvent> {

    private final KafkaTemplate<String, E> kafkaTemplate;

    public BaseKafkaProducer(String topic, KafkaProperties kafkaProperties) {
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
        kafkaTemplate.setDefaultTopic(topic);
    }

    public void produceEvent(E event) {
        log.debug("Sending event: {}", getEventIdentifier(event));
        Long id = null;
        if (event instanceof BaseEntityEvent) {
            id = ((BaseEntityEvent) event).getId();
        } else if (event instanceof InventoryEvent) {
            id = ((InventoryEvent) event).getVariantId();
        } else if (event instanceof WarehouseInventoryEvent) {
            id = ((WarehouseInventoryEvent) event).getVariantId();
        }
        ListenableFuture<SendResult<String, E>> listenableFuture = kafkaTemplate.sendDefault(id == null ? null : id.toString(), event);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.debug("Event sent successfully: {}", getEventIdentifier(event));
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("Failed sending event: {}", getEventIdentifier(event));
                log.error("Failed sending event", e);
            }
        });
    }

    private String getEventIdentifier(BaseEvent event) {
        if (event instanceof TplOrderEvent) {
            TplOrderEvent tplOrderEvent = (TplOrderEvent) event;
            return "tplOrderId: " + tplOrderEvent.getTplOrderId() +
                    ", orderId: " + tplOrderEvent.getOrderId() +
                    ", tplOrderUUID: " + tplOrderEvent.getTplOrderUUID();
        }
        else if (event instanceof BaseEntityEvent) {
            BaseEntityEvent entityEvent = (BaseEntityEvent) event;
            return entityEvent.getEntity() + ", " + entityEvent.getId() + ", " + entityEvent.getType().name();
        } else if (event instanceof InventoryEvent) {
            InventoryEvent inventoryEvent = (InventoryEvent) event;
            return  "variantId:"+inventoryEvent.getVariantId() + ", partnerId:" +inventoryEvent.getPartnerId() + ", available:"
                    + inventoryEvent.getAvailableAmount() + ", " + inventoryEvent.getAvailableDiff()+ ", orderable: "
                    + inventoryEvent.getOrderableAmount() + ", " + inventoryEvent.getOrderableDiff()+
                    ", isValid :" +inventoryEvent.getIsValid();
        }else if (event instanceof WarehouseInventoryEvent) {
            WarehouseInventoryEvent warehouseInventoryEvent = (WarehouseInventoryEvent) event;
            return  "variantId:"+warehouseInventoryEvent.getVariantId() + ", warehouseId:" +warehouseInventoryEvent.getWarehouseId() + ", available:"
                    + warehouseInventoryEvent.getAvailableAmount() + ", " + warehouseInventoryEvent.getAvailableDiff()+ ", orderable: "
                    + warehouseInventoryEvent.getOrderableAmount() + ", " + warehouseInventoryEvent.getOrderableDiff() +
                    " , isValid :" +warehouseInventoryEvent.getIsValid();
        } else {
            return event.getClass().getSimpleName() + "(" + event.getTime() + ")";
        }
    }
}
