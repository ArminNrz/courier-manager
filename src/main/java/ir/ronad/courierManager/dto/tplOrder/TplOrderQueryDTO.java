package ir.ronad.courierManager.dto.tplOrder;

import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TplOrderQueryDTO extends BaseDTO {
    private String id;
    private Long orderId;
    private List<Long> orderIds;
    private TplOrderStatus status;
    private List<TplOrderStatus> statuses;
    private String tplTrackingCode;
    private TplCode tplCode;
    private List<TplCode> tplCodes;
    private LocalDateTime created;
    private LocalDateTime updated;
}
