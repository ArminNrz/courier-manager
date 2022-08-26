package ir.ronad.courierManager.dto.thirdparty.linkExpress.get;

import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LinkExpressGetOrderResponse extends BaseDTO {
    private LinkExpressResultResponse result;
    private String code;
}
