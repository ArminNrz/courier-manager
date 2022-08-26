package ir.ronad.courierManager.dto.thirdparty.linkExpress.get;

import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LinkExpressGetOrderRequest extends BaseDTO {
    private String trackingCode;
}
