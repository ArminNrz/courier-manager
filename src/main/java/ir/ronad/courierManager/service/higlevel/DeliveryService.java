package ir.ronad.courierManager.service.higlevel;

import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;

public interface DeliveryService {
    TplOrderLimitDTO createOrder(String tplOrderId);
    void getOrder(String tplOrderId);
    void getOrderPrice(String tplOrderId);
}
