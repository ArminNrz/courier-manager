package ir.ronad.courierManager.service.higlevel.delivery.handler;

import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.entity.tplOrderScheduler.TplOrderSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static ir.ronad.courierManager.domain.enumartion.NotificationType.SCHEDULER;
import static ir.ronad.courierManager.domain.enumartion.NotificationType.WEBHOOK;
import static ir.ronad.courierManager.domain.enumartion.TplOrderStatus.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceSchedulerHandler {

    private final TplOrderSchedulerService schedulerService;

    private static final List<TplOrderStatus> REMOVABLE_STATUS_LIST = List.of(CANCELLED, DELIVERED, REJECTED_BY_3PL, NOT_DELIVERED);

    public void saveScheduler(DeliveryResponse deliveryResponse) {
        if (deliveryResponse.getNotificationType() == SCHEDULER) {
            schedulerService.save(deliveryResponse.getTplOrderEntity());
        }
    }

    public void deleteScheduler(DeliveryResponse deliveryResponse) {
        if (deliveryResponse.getNotificationType() == WEBHOOK) {
            return;
        }

        if (REMOVABLE_STATUS_LIST.contains(deliveryResponse.getStatus())) {
            schedulerService.delete(deliveryResponse.getTplOrderEntity());
        }
    }
}
