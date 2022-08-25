package ir.ronad.courierManager.service.data;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TplOrderLogData {
    private TplOrderEntity entity;
    private TplOrderStatus lastStatus;
    private String description;
}
