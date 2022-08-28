package ir.ronad.courierManager.service.higlevel.delivery;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.service.data.DeliveryResponse;

public interface DeliveryService {
    TplOrderLimitDTO createOrder(String tplOrderId);
    TplOrderLimitDTO getOrder(String tplOrderId);
    DeliveryResponse getOrder(TplOrderEntity tplOrderEntity);
    void getOrderPrice(String tplOrderId);
}
