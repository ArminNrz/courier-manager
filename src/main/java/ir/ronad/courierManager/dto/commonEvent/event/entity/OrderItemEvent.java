package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.status.OrderItemStatus;
import ir.ronad.courierManager.dto.commonEvent.status.OrderStatus;
import ir.ronad.courierManager.dto.commonEvent.type.OrderType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemEvent extends BaseStatusEntityEvent<OrderItemStatus> {

    public OrderItemEvent() {
        setEntity(Entity.ORDER_ITEM);
    }

    private Long orderId;
    private OrderType orderType;
    private String orderTrackingCode;
    private Long variantId;

    private Double amount;

    private OrderStatus newOrderStatus;
    private OrderStatus lastOrderStatus;
}
