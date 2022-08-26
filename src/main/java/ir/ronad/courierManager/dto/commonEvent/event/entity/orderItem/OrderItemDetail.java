package ir.ronad.courierManager.dto.commonEvent.event.entity.orderItem;

import ir.ronad.courierManager.dto.commonEvent.status.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDetail {
    private Long id;
    private Long skuId;
    private Double amount;
    private OrderItemStatus status;
    }
