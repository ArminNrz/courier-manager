package ir.ronad.courierManager.service.higlevel.manager.handler.speed;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.higlevel.manager.handler.BaseCourierHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpeedCourierHandler extends BaseCourierHandler {
    @Override
    public DeliveryResponse createOrder(TplOrderEntity tplOrderEntity) {
        return null;
    }

    @Override
    public DeliveryResponse getOrder(TplOrderEntity tplOrderEntity) {
        return null;
    }
}
