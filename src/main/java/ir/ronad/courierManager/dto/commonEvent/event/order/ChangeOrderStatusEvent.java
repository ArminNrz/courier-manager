package ir.ronad.courierManager.dto.commonEvent.event.order;

import ir.ronad.courierManager.dto.commonEvent.status.OrderStatus;
import lombok.Data;

@Data
public class ChangeOrderStatusEvent {
    private Long partnerId;
    private Long orderId;
    private String postTrackingCode;
    private String trackingCode;
    private OrderStatus orderStatus;
}
