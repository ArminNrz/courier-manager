package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.event.entity.orderItem.OrderItemDetail;
import ir.ronad.courierManager.dto.commonEvent.status.OrderStatus;
import ir.ronad.courierManager.dto.commonEvent.type.OrderType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEvent extends BaseNotificationEntityEvent<OrderStatus> {

    public OrderEvent() {
        setEntity(Entity.ORDER);
    }

    private String trackingCode;
    private String postTrackingCode;
    private Long newWarehouseId;
    private Long lastWarehouseId;

    private List<OrderItemDetail> formerOrderItemDetails=new ArrayList<>();
    private Long variantId;
    private Double amount;
    private OrderType orderType;
}
