package ir.ronad.courierManager.service.data;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class SendNotificationData {
    private String tplOrderId;
    private String orderId;
    private TplOrderStatus lastStatus;
    private TplOrderStatus newStatus;
    private List<TplOrderEntity> otherTplOrderEntities;
    private String tplOrderUUID;
}
