package ir.ronad.courierManager.service.higlevel.delivery.handler;

import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.entity.tplOrder.TplOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceCommonHandler {

    private final TplOrderService tplOrderService;
    private final DeliveryServiceNotificationHandler notificationHandler;
    private final DeliveryServiceValidationHandler validationHandler;

    public void checkAndUpdateByDeliveryResponse(DeliveryResponse deliveryResponse) {
        if (validationHandler.isTplOrderStatusChange(deliveryResponse)) {
            this.updateByDeliveryResponse(deliveryResponse);
        }
    }

    public DeliveryResponse updateByDeliveryResponse(DeliveryResponse deliveryResponse) {
        deliveryResponse.setLastTplOrderStatus(deliveryResponse.getTplOrderEntity().getStatus());
        tplOrderService.updateStatus(deliveryResponse);
        tplOrderService.updateByDeliveryResponse(deliveryResponse);
        notificationHandler.sendToBus(deliveryResponse);
        return deliveryResponse;
    }
}
