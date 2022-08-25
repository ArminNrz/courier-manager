package ir.ronad.courierManager.service.higlevel.manager.handler;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.service.data.DeliveryResponse;

public abstract class BaseCourierHandler {
    public abstract DeliveryResponse createOrder(TplOrderEntity tplOrderEntity);
}
