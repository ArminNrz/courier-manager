package ir.ronad.courierManager.dto.tplOrder;

import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TplOrderLimitDTO extends BaseDTO {
    private String id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long orderId;
    private TplOrderStatus status;
    private TplCode tplCode;
    private String trackingCode;
    private String tplTrackingCode;
}
