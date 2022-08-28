package ir.ronad.courierManager.service.higlevel.delivery.handler;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.mapper.DeliveryResponseMapper;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.data.SendNotificationData;
import ir.ronad.courierManager.service.entity.tplOrder.TplOrderService;
import ir.ronad.courierManager.service.lowlevel.producer.TplOrderNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceNotificationHandler {

    private final TplOrderService tplOrderService;
    private final DeliveryResponseMapper deliveryResponseMapper;
    private final TplOrderNotificationService notificationService;

    public void sendToBus(DeliveryResponse deliveryResponse) {
        log.trace("Enter: DeliveryServiceNotificationHandler.sendToBus({})", deliveryResponse);
        long orderId = deliveryResponse.getTplOrderEntity().getOrderId();

        if (isTplOrderHasUUID(orderId, deliveryResponse.getTplOrderEntity())) return;
        List<TplOrderEntity> filteredTplOrderEntities = getFilteredTplOrderEntities(deliveryResponse, orderId);
        if (filteredTplOrderEntities == null) return;

        SendNotificationData sendNotificationData = deliveryResponseMapper.toNotificationData(deliveryResponse, filteredTplOrderEntities);
        notificationService.sendToBus(sendNotificationData);
    }

    private List<TplOrderEntity> getFilteredTplOrderEntities(DeliveryResponse deliveryResponse, long orderId) {
        List<TplOrderEntity> otherTplOrders = tplOrderService.getByOrderId(deliveryResponse.getTplOrderEntity().getOrderId());
        List<TplOrderEntity> filteredTplOrderEntities = otherTplOrders.stream()
                .filter(Objects::nonNull)
                .filter(tplOrderEntity -> tplOrderEntity.getTplOrderUUID() != null)
                .filter(tplOrderEntity -> tplOrderEntity.getOrderId() == orderId)
                .collect(Collectors.toList());

        if (filteredTplOrderEntities.isEmpty()) {
            log.warn("No tplOrder found with orderId: {}, So do not send event!", orderId);
            return null;
        }
        return filteredTplOrderEntities;
    }

    private boolean isTplOrderHasUUID(long orderId, TplOrderEntity tplOrderEntity) {
        if (tplOrderEntity.getTplOrderUUID() == null) {
            log.warn("tplOrderUUID for orderId: {}, is null, do not send event", orderId);
            return true;
        }
        return false;
    }
}
