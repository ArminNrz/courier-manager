package ir.ronad.courierManager.service.higlevel;

import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;

public interface DeliveryService {
    TplOrderLimitDTO createOrder(String tplOrderId);
    TplOrderLimitDTO getOrder(String tplOrderId);
    void getOrderPrice(String tplOrderId);
}
