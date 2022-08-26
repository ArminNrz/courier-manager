package ir.ronad.courierManager.dto.commonEvent.event.entity.tplorder;

import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import lombok.Data;

@Data
public class TplOrderDetail {
    private String id;
    private TplOrderStatus status;
}
