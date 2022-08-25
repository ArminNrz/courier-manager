package ir.ronad.courierManager.domain.extraInfo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class LinkExpressExtraInfo extends BaseExtraInfo {
    private String actualReceiverName;
    private String rejectReason;
    private String rejectReasonDescription;
    private String message;
}
