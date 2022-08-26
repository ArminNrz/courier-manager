package ir.ronad.courierManager.service.data;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.NotificationType;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.BaseExtraInfo;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DeliveryResponse implements Serializable {
    private TplOrderEntity tplOrderEntity;
    private String tplTrackingCode;
    private TplOrderStatus status;
    private BaseExtraInfo extraInfo;
    private NotificationType notificationType;
    private TplOrderStatus lastTplOrderStatus;
}
