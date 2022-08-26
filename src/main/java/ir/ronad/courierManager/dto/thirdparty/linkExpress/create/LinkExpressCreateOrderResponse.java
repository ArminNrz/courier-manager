package ir.ronad.courierManager.dto.thirdparty.linkExpress.create;

import ir.ronad.courierManager.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LinkExpressCreateOrderResponse extends BaseDTO {
    private String message;
    private String code;
    private String tracking_code;
}
