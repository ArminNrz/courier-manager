package ir.ronad.courierManager.service.higlevel.handler;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.mapper.DeliveryResponseMapper;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.data.SendNotificationData;
import ir.ronad.courierManager.service.entity.TplOrderService;
import ir.ronad.courierManager.service.lowlevel.producer.TplOrderNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceNotificationHandler {

    private final TplOrderService tplOrderService;
    private final DeliveryResponseMapper deliveryResponseMapper;
    private final TplOrderNotificationService notificationService;

    public void sendToBus(DeliveryResponse deliveryResponse) {
        log.trace("Enter: DeliveryServiceNotificationHandler.sendToBus({})", deliveryResponse);
        List<TplOrderEntity> otherTplOrders = tplOrderService.getByOrderId(deliveryResponse.getTplOrderEntity().getOrderId());
        SendNotificationData sendNotificationData = deliveryResponseMapper.toNotificationData(deliveryResponse, otherTplOrders);
        notificationService.sendToBus(sendNotificationData);
    }
}
