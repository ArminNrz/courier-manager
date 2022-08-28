package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.commonEvent.event.entity.tplorder.TplOrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class TplOrderEvent extends BaseStatusEntityEvent<TplOrderStatus> {

    public TplOrderEvent() {
        setEntity(Entity.TPL_ORDER);
    }

    private Long orderId;
    private String tplOrderId;
    private String tplOrderUUID;
    private List<TplOrderDetail> orderDetails;
}
